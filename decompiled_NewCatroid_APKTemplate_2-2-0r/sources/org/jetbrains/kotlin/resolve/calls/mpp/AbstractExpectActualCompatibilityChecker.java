package org.jetbrains.kotlin.resolve.calls.mpp;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.ConstructorSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.EnumEntrySymbolMarker;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.SimpleFunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: AbstractExpectActualCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J%\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\tH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010\u001aJ%\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\tH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010\u001aJ4\u0010\u001e\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00052\b\u0010\u001f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u0005H\u0002J-\u0010\"\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010%J-\u0010&\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010%J-\u0010'\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010%J5\u0010(\u001a\u00020\b2\u000e\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+0*2\u000e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+0*H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010-J9\u0010.\u001a\u00020\b2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000*2\f\u00101\u001a\b\u0012\u0004\u0012\u0002000*2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u00102J%\u00103\u001a\u00020\b2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u00107J\u001e\u00108\u001a\u0004\u0018\u00010\u00052\b\u00109\u001a\u0004\u0018\u00010\u00052\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002J>\u0010;\u001a\u00020\b\"\u0004\b\u0000\u0010<\"\u0004\b\u0001\u0010=2\u0006\u0010>\u001a\u0002H<2\u0006\u0010?\u001a\u0002H<2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u0002H<\u0012\u0004\u0012\u0002H=0AH\u0082\b¢\u0006\u0002\u0010BJE\u0010C\u001a\u00020\b\"\u0004\b\u0000\u0010<\"\u0004\b\u0001\u0010=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H<0*2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002H<0*2\u0012\u0010@\u001a\u000e\u0012\u0004\u0012\u0002H<\u0012\u0004\u0012\u0002H=0AH\u0082\bJG\u0010D\u001a\u0006\u0012\u0002\b\u00030E2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\b\u0010I\u001a\u0004\u0018\u00010$2\b\u0010J\u001a\u0004\u0018\u00010\t2\b\u0010K\u001a\u0004\u0018\u00010\tH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010LJR\u0010D\u001a\b\u0012\u0004\u0012\u0002H<0E\"\b\b\u0000\u0010<*\u00020\u000f2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\b\u0010I\u001a\u0004\u0018\u00010$2\b\u0010J\u001a\u0004\u0018\u00010\t2\b\u0010K\u001a\u0004\u0018\u00010\t2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H<0\nJ5\u0010N\u001a\b\u0012\u0002\b\u0003\u0018\u00010O2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\b\u0010I\u001a\u0004\u0018\u00010$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010PJ?\u0010Q\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\b\u0010J\u001a\u0004\u0018\u00010\t2\b\u0010K\u001a\u0004\u0018\u00010\tH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010SJ3\u0010T\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010UJ4\u0010V\u001a\b\u0012\u0004\u0012\u0002H<0E\"\b\b\u0000\u0010<*\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010W\u001a\u00020X2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H<0\nJ3\u0010V\u001a\u0006\u0012\u0002\b\u00030E2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010W\u001a\u00020X2\b\u0010I\u001a\u0004\u0018\u00010$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010YJ5\u0010Z\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010W\u001a\u00020X2\b\u0010I\u001a\u0004\u0018\u00010$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010[J+\u0010\\\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\u0006\u0010]\u001a\u00020G2\u0006\u0010^\u001a\u00020GH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010_J+\u0010`\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010aJ7\u0010b\u001a\b\u0012\u0002\b\u0003\u0018\u00010R2\f\u0010/\u001a\b\u0012\u0004\u0012\u0002000*2\f\u00101\u001a\b\u0012\u0004\u0012\u0002000*H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010cJ}\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020\u000f2\f\u0010g\u001a\b\u0012\u0004\u0012\u00020\u000f0*2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010\u001c\u001a\u0004\u0018\u00010\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\t22\u0010h\u001a.\u0012(\u0012&\u0012\u0004\u0012\u00020\u000f\u0012\u001c\u0012\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030l\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0*0k0j\u0018\u00010iH\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010mJ4\u0010n\u001a\u00020e\"\b\b\u0000\u0010<*\u00020\u000f2\u0006\u0010F\u001a\u00020\u000f2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u000f0*2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002H<0\nJA\u0010p\u001a\u00020\b2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020r0*2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020r0*H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010tJ-\u0010u\u001a\b\u0012\u0004\u0012\u00020+0**\b\u0012\u0004\u0012\u00020r0*2\u0006\u0010#\u001a\u00020$H\u0002R\u0006\u0012\u0002\b\u00030\n¢\u0006\u0002\u0010vR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004b\u0006\u0012\u0002\b\u00030\n¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u000bR \u0010\f\u001a\u00020\b*\u00020\t8BX\u0082\u0004b\u0006\u0012\u0002\b\u00030\n¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR \u0010\r\u001a\u00020\u000e*\u00020\u000f8BX\u0082\u0004b\u0006\u0012\u0002\b\u00030\n¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006w"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/mpp/AbstractExpectActualCompatibilityChecker;", "", "()V", "compatibleModalityMap", "Ljava/util/EnumMap;", "Lorg/jetbrains/kotlin/descriptors/Modality;", "Ljava/util/EnumSet;", "isCtorless", "", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "isFinal", "name", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "getName", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "areCompatibleCallableVisibilities", "expectVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "expectModality", "actualVisibility", "areCompatibleClassKinds", "expectClass", "actualClass", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "areCompatibleClassVisibilities", "expectClassSymbol", "actualClassSymbol", "areCompatibleModalities", "actualModality", "expectContainingClassModality", "actualContainingClassModality", "areCompatibleSupertypes", "substitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Z", "areCompatibleSupertypesOneByOne", "areCompatibleSupertypesTransitive", "areCompatibleTypeLists", "expectedTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "actualTypes", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Ljava/util/List;Ljava/util/List;)Z", "areCompatibleTypeParameterUpperBounds", "expectTypeParameterSymbols", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "actualTypeParameterSymbols", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Z", "arePropertySettersWithCompatibleVisibilities", "expected", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "actual", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Z", "effectiveModality", "declarationModality", "containingClassModality", "equalBy", ExifInterface.GPS_DIRECTION_TRUE, "K", "first", "second", "selector", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Z", "equalsBy", "getCallablesCompatibility", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "expectDeclaration", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "actualDeclaration", "parentSubstitutor", "expectContainingClass", "actualContainingClass", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "context", "getCallablesStrongIncompatibility", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "getCallablesWeakIncompatibility", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "getClassScopesIncompatibility", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "getClassifiersCompatibility", "actualClassLikeSymbol", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "getClassifiersIncompatibility", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "getFunctionsIncompatibility", "expectFunction", "actualFunction", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "getPropertiesIncompatibility", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "getTypeParametersVarianceOrReifiedIncompatibility", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Ljava/util/List;Ljava/util/List;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "matchSingleExpectAgainstPotentialActuals", "", "expectMember", "actualMembers", "unfulfilled", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;Ljava/util/List;)V", "matchSingleExpectTopLevelDeclarationAgainstPotentialActuals", "actualDeclarations", "valueParametersCountCompatible", "expectValueParameters", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "actualValueParameters", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;Ljava/util/List;Ljava/util/List;)Z", "toTypeList", "(Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;)Ljava/util/List;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbstractExpectActualCompatibilityChecker {
    public static final AbstractExpectActualCompatibilityChecker INSTANCE = new AbstractExpectActualCompatibilityChecker();
    private static final EnumMap<Modality, EnumSet<Modality>> compatibleModalityMap = AddToStdlibKt.enumMapOf(TuplesKt.to(Modality.ABSTRACT, AddToStdlibKt.enumSetOf(Modality.ABSTRACT, new Modality[0])), TuplesKt.to(Modality.OPEN, AddToStdlibKt.enumSetOf(Modality.OPEN, new Modality[0])), TuplesKt.to(Modality.FINAL, AddToStdlibKt.enumSetOf(Modality.OPEN, Modality.FINAL)), TuplesKt.to(Modality.SEALED, AddToStdlibKt.enumSetOf(Modality.SEALED, new Modality[0])));

    /* JADX INFO: compiled from: AbstractExpectActualCompatibilityChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Modality.values().length];
            try {
                iArr[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AbstractExpectActualCompatibilityChecker() {
    }

    public final <T extends DeclarationSymbolMarker> ExpectActualCompatibility<T> getClassifiersCompatibility(RegularClassSymbolMarker expectClassSymbol, ClassLikeSymbolMarker actualClassLikeSymbol, ExpectActualMatchingContext<T> context) {
        Intrinsics.checkNotNullParameter(expectClassSymbol, "expectClassSymbol");
        Intrinsics.checkNotNullParameter(actualClassLikeSymbol, "actualClassLikeSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        ExpectActualCompatibility<T> expectActualCompatibility = (ExpectActualCompatibility<T>) INSTANCE.getClassifiersCompatibility(context, expectClassSymbol, actualClassLikeSymbol, null);
        Intrinsics.checkNotNull(expectActualCompatibility, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility<T of org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker.getClassifiersCompatibility>");
        return expectActualCompatibility;
    }

    public final <T extends DeclarationSymbolMarker> ExpectActualCompatibility<T> getCallablesCompatibility(CallableSymbolMarker expectDeclaration, CallableSymbolMarker actualDeclaration, TypeSubstitutorMarker parentSubstitutor, RegularClassSymbolMarker expectContainingClass, RegularClassSymbolMarker actualContainingClass, ExpectActualMatchingContext<T> context) {
        Intrinsics.checkNotNullParameter(expectDeclaration, "expectDeclaration");
        Intrinsics.checkNotNullParameter(actualDeclaration, "actualDeclaration");
        Intrinsics.checkNotNullParameter(context, "context");
        ExpectActualCompatibility<T> expectActualCompatibility = (ExpectActualCompatibility<T>) INSTANCE.getCallablesCompatibility((ExpectActualMatchingContext<?>) context, expectDeclaration, actualDeclaration, parentSubstitutor, expectContainingClass, actualContainingClass);
        Intrinsics.checkNotNull(expectActualCompatibility, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility<T of org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker.getCallablesCompatibility>");
        return expectActualCompatibility;
    }

    public final <T extends DeclarationSymbolMarker> void matchSingleExpectTopLevelDeclarationAgainstPotentialActuals(DeclarationSymbolMarker expectDeclaration, List<? extends DeclarationSymbolMarker> actualDeclarations, ExpectActualMatchingContext<T> context) {
        Intrinsics.checkNotNullParameter(expectDeclaration, "expectDeclaration");
        Intrinsics.checkNotNullParameter(actualDeclarations, "actualDeclarations");
        Intrinsics.checkNotNullParameter(context, "context");
        INSTANCE.matchSingleExpectAgainstPotentialActuals(context, expectDeclaration, actualDeclarations, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpectActualCompatibility<?> getClassifiersCompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, ClassLikeSymbolMarker classLikeSymbolMarker, TypeSubstitutorMarker typeSubstitutorMarker) {
        ExpectActualCompatibility.Incompatible.WeakIncompatible<?> classifiersIncompatibility = getClassifiersIncompatibility(expectActualMatchingContext, regularClassSymbolMarker, classLikeSymbolMarker, typeSubstitutorMarker);
        return classifiersIncompatibility != null ? classifiersIncompatibility : ExpectActualCompatibility.Compatible.INSTANCE;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getClassifiersIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, ClassLikeSymbolMarker classLikeSymbolMarker, TypeSubstitutorMarker typeSubstitutorMarker) {
        RegularClassSymbolMarker regularClassSymbolMarkerExpandToRegularClass;
        if (!Intrinsics.areEqual(getName(expectActualMatchingContext, regularClassSymbolMarker), getName(expectActualMatchingContext, classLikeSymbolMarker))) {
            throw new IllegalArgumentException(("This function should be invoked only for declarations with the same name: " + regularClassSymbolMarker + ", " + classLikeSymbolMarker).toString());
        }
        if (classLikeSymbolMarker instanceof RegularClassSymbolMarker) {
            regularClassSymbolMarkerExpandToRegularClass = (RegularClassSymbolMarker) classLikeSymbolMarker;
        } else {
            if (!(classLikeSymbolMarker instanceof TypeAliasSymbolMarker)) {
                throw new IllegalStateException(("Incorrect actual classifier for " + regularClassSymbolMarker + ": " + classLikeSymbolMarker).toString());
            }
            regularClassSymbolMarkerExpandToRegularClass = expectActualMatchingContext.expandToRegularClass((TypeAliasSymbolMarker) classLikeSymbolMarker);
            if (regularClassSymbolMarkerExpandToRegularClass == null) {
                return null;
            }
        }
        RegularClassSymbolMarker regularClassSymbolMarker2 = regularClassSymbolMarkerExpandToRegularClass;
        if (!areCompatibleClassKinds(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.ClassKind.INSTANCE;
        }
        Boolean[] boolArr = new Boolean[3];
        boolArr[0] = Boolean.valueOf(expectActualMatchingContext.isCompanion(regularClassSymbolMarker));
        boolArr[1] = Boolean.valueOf(expectActualMatchingContext.isInner(regularClassSymbolMarker));
        boolArr[2] = Boolean.valueOf(expectActualMatchingContext.isInline(regularClassSymbolMarker) || expectActualMatchingContext.isValue(regularClassSymbolMarker));
        List listListOf = CollectionsKt.listOf((Object[]) boolArr);
        Boolean[] boolArr2 = new Boolean[3];
        boolArr2[0] = Boolean.valueOf(expectActualMatchingContext.isCompanion(regularClassSymbolMarker2));
        boolArr2[1] = Boolean.valueOf(expectActualMatchingContext.isInner(regularClassSymbolMarker2));
        boolArr2[2] = Boolean.valueOf(expectActualMatchingContext.isInline(regularClassSymbolMarker2) || expectActualMatchingContext.isValue(regularClassSymbolMarker2));
        if (!Intrinsics.areEqual(listListOf, CollectionsKt.listOf((Object[]) boolArr2))) {
            return ExpectActualCompatibility.Incompatible.ClassModifiers.INSTANCE;
        }
        if (expectActualMatchingContext.isFun(regularClassSymbolMarker) && !expectActualMatchingContext.isFun(regularClassSymbolMarker2) && expectActualMatchingContext.isNotSamInterface(regularClassSymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.FunInterfaceModifier.INSTANCE;
        }
        RegularClassSymbolMarker regularClassSymbolMarker3 = regularClassSymbolMarker;
        List<TypeParameterSymbolMarker> typeParameters = expectActualMatchingContext.getTypeParameters(regularClassSymbolMarker3);
        RegularClassSymbolMarker regularClassSymbolMarker4 = regularClassSymbolMarker2;
        List<TypeParameterSymbolMarker> typeParameters2 = expectActualMatchingContext.getTypeParameters(regularClassSymbolMarker4);
        if (typeParameters.size() != typeParameters2.size()) {
            return ExpectActualCompatibility.Incompatible.ClassTypeParameterCount.INSTANCE;
        }
        if (!areCompatibleModalities$default(this, expectActualMatchingContext.getModality(regularClassSymbolMarker3), expectActualMatchingContext.getModality(regularClassSymbolMarker4), null, null, 12, null)) {
            return ExpectActualCompatibility.Incompatible.Modality.INSTANCE;
        }
        if (!areCompatibleClassVisibilities(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.Visibility.INSTANCE;
        }
        TypeSubstitutorMarker typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = expectActualMatchingContext.createExpectActualTypeParameterSubstitutor(typeParameters, typeParameters2, typeSubstitutorMarker);
        if (!areCompatibleTypeParameterUpperBounds(expectActualMatchingContext, typeParameters, typeParameters2, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor)) {
            return ExpectActualCompatibility.Incompatible.ClassTypeParameterUpperBounds.INSTANCE;
        }
        ExpectActualCompatibility.Incompatible.WeakIncompatible<?> typeParametersVarianceOrReifiedIncompatibility = getTypeParametersVarianceOrReifiedIncompatibility(expectActualMatchingContext, typeParameters, typeParameters2);
        if (typeParametersVarianceOrReifiedIncompatibility != null) {
            return typeParametersVarianceOrReifiedIncompatibility;
        }
        if (!areCompatibleSupertypes(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor)) {
            return ExpectActualCompatibility.Incompatible.Supertypes.INSTANCE;
        }
        ExpectActualCompatibility.Incompatible.WeakIncompatible<?> classScopesIncompatibility = getClassScopesIncompatibility(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor);
        if (classScopesIncompatibility != null) {
            return classScopesIncompatibility;
        }
        return null;
    }

    private final boolean areCompatibleSupertypes(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker) {
        boolean allowTransitiveSupertypesActualization = expectActualMatchingContext.getAllowTransitiveSupertypesActualization();
        if (!allowTransitiveSupertypesActualization) {
            return areCompatibleSupertypesOneByOne(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2, typeSubstitutorMarker);
        }
        if (allowTransitiveSupertypesActualization) {
            return areCompatibleSupertypesTransitive(expectActualMatchingContext, regularClassSymbolMarker, regularClassSymbolMarker2, typeSubstitutorMarker);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean areCompatibleSupertypesOneByOne(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker) {
        List<KotlinTypeMarker> superTypes = expectActualMatchingContext.getSuperTypes(regularClassSymbolMarker);
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypes) {
            if (!expectActualMatchingContext.isAnyConstructor(expectActualMatchingContext.typeConstructor((KotlinTypeMarker) obj))) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        List<KotlinTypeMarker> superTypes2 = expectActualMatchingContext.getSuperTypes(regularClassSymbolMarker2);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : superTypes2) {
            if (!expectActualMatchingContext.isAnyConstructor(expectActualMatchingContext.typeConstructor((KotlinTypeMarker) obj2))) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList3;
        ArrayList arrayList5 = arrayList2;
        if ((arrayList5 instanceof Collection) && arrayList5.isEmpty()) {
            return true;
        }
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = expectActualMatchingContext.safeSubstitute(typeSubstitutorMarker, (KotlinTypeMarker) it.next());
            ArrayList arrayList6 = arrayList4;
            if (!(arrayList6 instanceof Collection) || !arrayList6.isEmpty()) {
                Iterator it2 = arrayList6.iterator();
                while (it2.hasNext()) {
                    if (expectActualMatchingContext.areCompatibleExpectActualTypes(kotlinTypeMarkerSafeSubstitute, (KotlinTypeMarker) it2.next())) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    private final boolean areCompatibleSupertypesTransitive(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker) {
        List<KotlinTypeMarker> superTypes = expectActualMatchingContext.getSuperTypes(regularClassSymbolMarker);
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypes) {
            if (!expectActualMatchingContext.isAnyConstructor(expectActualMatchingContext.typeConstructor((KotlinTypeMarker) obj))) {
                arrayList.add(obj);
            }
        }
        KotlinTypeMarker defaultType = expectActualMatchingContext.getDefaultType(regularClassSymbolMarker2);
        ArrayList arrayList2 = arrayList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            return true;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            if (!expectActualMatchingContext.actualTypeIsSubtypeOfExpectType(expectActualMatchingContext.safeSubstitute(typeSubstitutorMarker, (KotlinTypeMarker) it.next()), defaultType)) {
                return false;
            }
        }
        return true;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getClassScopesIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker) {
        ArrayList arrayList = new ArrayList();
        List<DeclarationSymbolMarker> listCollectAllMembers = expectActualMatchingContext.collectAllMembers(regularClassSymbolMarker2, true);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listCollectAllMembers) {
            Name name2 = INSTANCE.getName(expectActualMatchingContext, (DeclarationSymbolMarker) obj);
            Object obj2 = linkedHashMap.get(name2);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(name2, obj2);
            }
            ((List) obj2).add(obj);
        }
        Iterator<DeclarationSymbolMarker> it = expectActualMatchingContext.collectAllMembers(regularClassSymbolMarker, false).iterator();
        while (true) {
            ArrayList arrayList2 = null;
            if (!it.hasNext()) {
                break;
            }
            DeclarationSymbolMarker next = it.next();
            boolean z = next instanceof CallableSymbolMarker;
            if (!z || !expectActualMatchingContext.shouldSkipMatching((CallableSymbolMarker) next, regularClassSymbolMarker)) {
                List list = (List) linkedHashMap.get(getName(expectActualMatchingContext, next));
                if (list != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj3 : list) {
                        DeclarationSymbolMarker declarationSymbolMarker = (DeclarationSymbolMarker) obj3;
                        if ((z && (declarationSymbolMarker instanceof CallableSymbolMarker)) || ((next instanceof RegularClassSymbolMarker) && (declarationSymbolMarker instanceof RegularClassSymbolMarker))) {
                            arrayList3.add(obj3);
                        }
                    }
                    arrayList2 = arrayList3;
                }
                matchSingleExpectAgainstPotentialActuals(expectActualMatchingContext, next, arrayList2 == null ? CollectionsKt.emptyList() : arrayList2, typeSubstitutorMarker, regularClassSymbolMarker, regularClassSymbolMarker2, arrayList);
            }
        }
        if (expectActualMatchingContext.getClassKind(regularClassSymbolMarker) == ClassKind.ENUM_CLASS) {
            if (!expectActualMatchingContext.collectEnumEntryNames(regularClassSymbolMarker2).containsAll(expectActualMatchingContext.collectEnumEntryNames(regularClassSymbolMarker))) {
                return ExpectActualCompatibility.Incompatible.EnumEntries.INSTANCE;
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new ExpectActualCompatibility.Incompatible.ClassScopes(arrayList);
    }

    private final void matchSingleExpectAgainstPotentialActuals(final ExpectActualMatchingContext<?> expectActualMatchingContext, final DeclarationSymbolMarker declarationSymbolMarker, final List<? extends DeclarationSymbolMarker> list, final TypeSubstitutorMarker typeSubstitutorMarker, final RegularClassSymbolMarker regularClassSymbolMarker, final RegularClassSymbolMarker regularClassSymbolMarker2, List<Pair<DeclarationSymbolMarker, Map<ExpectActualCompatibility.Incompatible<?>, List<DeclarationSymbolMarker>>>> list2) {
        Map mapKeysToMap = org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(list, new Function1<DeclarationSymbolMarker, ExpectActualCompatibility<? extends Object>>() { // from class: org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker$matchSingleExpectAgainstPotentialActuals$mapping$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ExpectActualCompatibility<Object> invoke(DeclarationSymbolMarker actualMember) {
                Intrinsics.checkNotNullParameter(actualMember, "actualMember");
                DeclarationSymbolMarker declarationSymbolMarker2 = declarationSymbolMarker;
                if (declarationSymbolMarker2 instanceof CallableSymbolMarker) {
                    return AbstractExpectActualCompatibilityChecker.INSTANCE.getCallablesCompatibility((ExpectActualMatchingContext<?>) expectActualMatchingContext, (CallableSymbolMarker) declarationSymbolMarker, (CallableSymbolMarker) actualMember, typeSubstitutorMarker, regularClassSymbolMarker, regularClassSymbolMarker2);
                }
                if (declarationSymbolMarker2 instanceof RegularClassSymbolMarker) {
                    TypeSubstitutorMarker typeSubstitutorMarker2 = typeSubstitutorMarker;
                    if (typeSubstitutorMarker2 == null || expectActualMatchingContext.getInnerClassesCapturesOuterTypeParameters()) {
                        typeSubstitutorMarker2 = null;
                    }
                    return AbstractExpectActualCompatibilityChecker.INSTANCE.getClassifiersCompatibility(expectActualMatchingContext, (RegularClassSymbolMarker) declarationSymbolMarker, (ClassLikeSymbolMarker) actualMember, typeSubstitutorMarker2);
                }
                throw new IllegalStateException(("Unsupported declaration: " + declarationSymbolMarker + " (" + list + ')').toString());
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : mapKeysToMap.entrySet()) {
            DeclarationSymbolMarker declarationSymbolMarker2 = (DeclarationSymbolMarker) entry.getKey();
            ExpectActualCompatibility expectActualCompatibility = (ExpectActualCompatibility) entry.getValue();
            if (Intrinsics.areEqual(expectActualCompatibility, ExpectActualCompatibility.Compatible.INSTANCE)) {
                expectActualMatchingContext.onMatchedMembers(declarationSymbolMarker, declarationSymbolMarker2);
                return;
            } else if (expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible) {
                Object obj = linkedHashMap.get(expectActualCompatibility);
                if (obj == null) {
                    obj = (List) new SmartList();
                    linkedHashMap.put(expectActualCompatibility, obj);
                }
                ((List) obj).add(declarationSymbolMarker2);
            }
        }
        if (list2 != null) {
            list2.add(TuplesKt.to(declarationSymbolMarker, linkedHashMap));
        }
        expectActualMatchingContext.onMismatchedMembersFromClassScope(declarationSymbolMarker, linkedHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ExpectActualCompatibility<?> getCallablesCompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, CallableSymbolMarker callableSymbolMarker, CallableSymbolMarker callableSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2) {
        boolean z = callableSymbolMarker instanceof ConstructorSymbolMarker;
        if ((!z || !(callableSymbolMarker2 instanceof ConstructorSymbolMarker)) && !Intrinsics.areEqual(expectActualMatchingContext.getCallableId(callableSymbolMarker).getCallableName(), expectActualMatchingContext.getCallableId(callableSymbolMarker2).getCallableName())) {
            throw new IllegalArgumentException(("This function should be invoked only for declarations with the same name: " + callableSymbolMarker + ", " + callableSymbolMarker2).toString());
        }
        if ((expectActualMatchingContext.getDispatchReceiverType(callableSymbolMarker) == null) != (expectActualMatchingContext.getDispatchReceiverType(callableSymbolMarker2) == null)) {
            throw new IllegalArgumentException(("This function should be invoked only for declarations in the same kind of container (both members or both top level): " + callableSymbolMarker + ", " + callableSymbolMarker2).toString());
        }
        if (expectActualMatchingContext.getEnumConstructorsAreAlwaysCompatible()) {
            if ((regularClassSymbolMarker != null ? expectActualMatchingContext.getClassKind(regularClassSymbolMarker) : null) == ClassKind.ENUM_CLASS) {
                if ((regularClassSymbolMarker2 != null ? expectActualMatchingContext.getClassKind(regularClassSymbolMarker2) : null) == ClassKind.ENUM_CLASS && z && (callableSymbolMarker2 instanceof ConstructorSymbolMarker)) {
                    return ExpectActualCompatibility.Compatible.INSTANCE;
                }
            }
        }
        ExpectActualCompatibility.Incompatible.StrongIncompatible<?> callablesStrongIncompatibility = getCallablesStrongIncompatibility(expectActualMatchingContext, callableSymbolMarker, callableSymbolMarker2, typeSubstitutorMarker);
        if (callablesStrongIncompatibility != null) {
            return callablesStrongIncompatibility;
        }
        ExpectActualCompatibility.Incompatible.WeakIncompatible<?> callablesWeakIncompatibility = getCallablesWeakIncompatibility(expectActualMatchingContext, callableSymbolMarker, callableSymbolMarker2, regularClassSymbolMarker, regularClassSymbolMarker2);
        if (callablesWeakIncompatibility != null) {
            return callablesWeakIncompatibility;
        }
        return ExpectActualCompatibility.Compatible.INSTANCE;
    }

    private final ExpectActualCompatibility.Incompatible.StrongIncompatible<?> getCallablesStrongIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, CallableSymbolMarker callableSymbolMarker, CallableSymbolMarker callableSymbolMarker2, TypeSubstitutorMarker typeSubstitutorMarker) {
        if ((callableSymbolMarker instanceof FunctionSymbolMarker) != (callableSymbolMarker2 instanceof FunctionSymbolMarker)) {
            return ExpectActualCompatibility.Incompatible.CallableKind.INSTANCE;
        }
        KotlinTypeMarker extensionReceiverType = expectActualMatchingContext.getExtensionReceiverType(callableSymbolMarker);
        KotlinTypeMarker extensionReceiverType2 = expectActualMatchingContext.getExtensionReceiverType(callableSymbolMarker2);
        if ((extensionReceiverType != null) != (extensionReceiverType2 != null)) {
            return ExpectActualCompatibility.Incompatible.ParameterShape.INSTANCE;
        }
        List<ValueParameterSymbolMarker> valueParameters = expectActualMatchingContext.getValueParameters(callableSymbolMarker);
        List<ValueParameterSymbolMarker> valueParameters2 = expectActualMatchingContext.getValueParameters(callableSymbolMarker2);
        if (!valueParametersCountCompatible(expectActualMatchingContext, callableSymbolMarker, callableSymbolMarker2, valueParameters, valueParameters2)) {
            return ExpectActualCompatibility.Incompatible.ParameterCount.INSTANCE;
        }
        List<TypeParameterSymbolMarker> typeParameters = expectActualMatchingContext.getTypeParameters(callableSymbolMarker);
        List<TypeParameterSymbolMarker> typeParameters2 = expectActualMatchingContext.getTypeParameters(callableSymbolMarker2);
        if (typeParameters.size() != typeParameters2.size()) {
            return ExpectActualCompatibility.Incompatible.FunctionTypeParameterCount.INSTANCE;
        }
        TypeSubstitutorMarker typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor = expectActualMatchingContext.createExpectActualTypeParameterSubstitutor(typeParameters, typeParameters2, typeSubstitutorMarker);
        if (areCompatibleTypeLists(expectActualMatchingContext, toTypeList(expectActualMatchingContext, valueParameters, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor), toTypeList(expectActualMatchingContext, valueParameters2, expectActualMatchingContext.createEmptySubstitutor()))) {
            if (expectActualMatchingContext.areCompatibleExpectActualTypes(extensionReceiverType != null ? expectActualMatchingContext.safeSubstitute(typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor, extensionReceiverType) : null, extensionReceiverType2)) {
                if (expectActualMatchingContext.getShouldCheckReturnTypesOfCallables() && !expectActualMatchingContext.areCompatibleExpectActualTypes(expectActualMatchingContext.safeSubstitute(typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor, expectActualMatchingContext.getReturnType(callableSymbolMarker)), expectActualMatchingContext.getReturnType(callableSymbolMarker2))) {
                    return ExpectActualCompatibility.Incompatible.ReturnType.INSTANCE;
                }
                if (areCompatibleTypeParameterUpperBounds(expectActualMatchingContext, typeParameters, typeParameters2, typeSubstitutorMarkerCreateExpectActualTypeParameterSubstitutor)) {
                    return null;
                }
                return ExpectActualCompatibility.Incompatible.FunctionTypeParameterUpperBounds.INSTANCE;
            }
        }
        return ExpectActualCompatibility.Incompatible.ParameterTypes.INSTANCE;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getCallablesWeakIncompatibility(final ExpectActualMatchingContext<?> expectActualMatchingContext, CallableSymbolMarker callableSymbolMarker, CallableSymbolMarker callableSymbolMarker2, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2) {
        List<TypeParameterSymbolMarker> typeParameters = expectActualMatchingContext.getTypeParameters(callableSymbolMarker);
        List<TypeParameterSymbolMarker> typeParameters2 = expectActualMatchingContext.getTypeParameters(callableSymbolMarker2);
        List<ValueParameterSymbolMarker> valueParameters = expectActualMatchingContext.getValueParameters(callableSymbolMarker);
        List<ValueParameterSymbolMarker> valueParameters2 = expectActualMatchingContext.getValueParameters(callableSymbolMarker2);
        if (expectActualMatchingContext.getHasStableParameterNames(callableSymbolMarker2)) {
            int size = valueParameters.size();
            for (int i = 0; i < size; i++) {
                ValueParameterSymbolMarker valueParameterSymbolMarker = valueParameters.get(i);
                AbstractExpectActualCompatibilityChecker abstractExpectActualCompatibilityChecker = INSTANCE;
                if (!Intrinsics.areEqual(abstractExpectActualCompatibilityChecker.getName(expectActualMatchingContext, valueParameterSymbolMarker), abstractExpectActualCompatibilityChecker.getName(expectActualMatchingContext, valueParameters2.get(i)))) {
                    return ExpectActualCompatibility.Incompatible.ParameterNames.INSTANCE;
                }
            }
        }
        int size2 = typeParameters.size();
        for (int i2 = 0; i2 < size2; i2++) {
            TypeParameterSymbolMarker typeParameterSymbolMarker = typeParameters.get(i2);
            AbstractExpectActualCompatibilityChecker abstractExpectActualCompatibilityChecker2 = INSTANCE;
            if (!Intrinsics.areEqual(abstractExpectActualCompatibilityChecker2.getName(expectActualMatchingContext, typeParameterSymbolMarker), abstractExpectActualCompatibilityChecker2.getName(expectActualMatchingContext, typeParameters2.get(i2)))) {
                return ExpectActualCompatibility.Incompatible.TypeParameterNames.INSTANCE;
            }
        }
        Modality modality = expectActualMatchingContext.getModality(callableSymbolMarker);
        if (!areCompatibleModalities(modality, expectActualMatchingContext.getModality(callableSymbolMarker2), regularClassSymbolMarker != null ? expectActualMatchingContext.getModality(regularClassSymbolMarker) : null, regularClassSymbolMarker2 != null ? expectActualMatchingContext.getModality(regularClassSymbolMarker2) : null)) {
            return ExpectActualCompatibility.Incompatible.Modality.INSTANCE;
        }
        if (!areCompatibleCallableVisibilities(expectActualMatchingContext.getVisibility(callableSymbolMarker), modality, expectActualMatchingContext.getVisibility(callableSymbolMarker2))) {
            return ExpectActualCompatibility.Incompatible.Visibility.INSTANCE;
        }
        ExpectActualCompatibility.Incompatible.WeakIncompatible<?> typeParametersVarianceOrReifiedIncompatibility = getTypeParametersVarianceOrReifiedIncompatibility(expectActualMatchingContext, typeParameters, typeParameters2);
        if (typeParametersVarianceOrReifiedIncompatibility != null) {
            return typeParametersVarianceOrReifiedIncompatibility;
        }
        if (expectActualMatchingContext.getShouldCheckAbsenceOfDefaultParamsInActual() && (callableSymbolMarker2 instanceof FunctionSymbolMarker) && (callableSymbolMarker instanceof FunctionSymbolMarker) && !expectActualMatchingContext.isAnnotationConstructor(callableSymbolMarker2)) {
            Iterator it = SequencesKt.flatMapIterable(SequencesKt.minus((Sequence) expectActualMatchingContext.allOverriddenDeclarationsRecursive((FunctionSymbolMarker) callableSymbolMarker2), (Iterable) SequencesKt.toSet(expectActualMatchingContext.allOverriddenDeclarationsRecursive((FunctionSymbolMarker) callableSymbolMarker))), new Function1<CallableSymbolMarker, List<? extends ValueParameterSymbolMarker>>() { // from class: org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker.getCallablesWeakIncompatibility.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<ValueParameterSymbolMarker> invoke(CallableSymbolMarker it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return expectActualMatchingContext.getValueParameters(it2);
                }
            }).iterator();
            while (it.hasNext()) {
                if (expectActualMatchingContext.getHasDefaultValue((ValueParameterSymbolMarker) it.next())) {
                    return ExpectActualCompatibility.Incompatible.ActualFunctionWithDefaultParameters.INSTANCE;
                }
            }
        }
        int size3 = valueParameters.size();
        for (int i3 = 0; i3 < size3; i3++) {
            if (expectActualMatchingContext.isVararg(valueParameters.get(i3)) != expectActualMatchingContext.isVararg(valueParameters2.get(i3))) {
                return ExpectActualCompatibility.Incompatible.ValueParameterVararg.INSTANCE;
            }
        }
        if ((callableSymbolMarker instanceof SimpleFunctionSymbolMarker) && expectActualMatchingContext.isInline(callableSymbolMarker)) {
            List<ValueParameterSymbolMarker> list = valueParameters;
            Iterable indices = CollectionsKt.getIndices(list);
            if (!(indices instanceof Collection) || !((Collection) indices).isEmpty()) {
                Iterator it2 = indices.iterator();
                while (it2.hasNext()) {
                    int iNextInt = ((IntIterator) it2).nextInt();
                    if (!expectActualMatchingContext.isNoinline(valueParameters.get(iNextInt)) && expectActualMatchingContext.isNoinline(valueParameters2.get(iNextInt))) {
                        return ExpectActualCompatibility.Incompatible.ValueParameterNoinline.INSTANCE;
                    }
                }
            }
            Iterable indices2 = CollectionsKt.getIndices(list);
            if (!(indices2 instanceof Collection) || !((Collection) indices2).isEmpty()) {
                Iterator it3 = indices2.iterator();
                while (it3.hasNext()) {
                    int iNextInt2 = ((IntIterator) it3).nextInt();
                    if (!expectActualMatchingContext.isCrossinline(valueParameters.get(iNextInt2)) && expectActualMatchingContext.isCrossinline(valueParameters2.get(iNextInt2))) {
                        return ExpectActualCompatibility.Incompatible.ValueParameterCrossinline.INSTANCE;
                    }
                }
            }
        }
        if ((callableSymbolMarker instanceof FunctionSymbolMarker) && (callableSymbolMarker2 instanceof FunctionSymbolMarker)) {
            ExpectActualCompatibility.Incompatible.WeakIncompatible<?> functionsIncompatibility = getFunctionsIncompatibility(expectActualMatchingContext, callableSymbolMarker, callableSymbolMarker2);
            if (functionsIncompatibility != null) {
                return functionsIncompatibility;
            }
        } else if ((callableSymbolMarker instanceof PropertySymbolMarker) && (callableSymbolMarker2 instanceof PropertySymbolMarker)) {
            ExpectActualCompatibility.Incompatible.WeakIncompatible<?> propertiesIncompatibility = getPropertiesIncompatibility(expectActualMatchingContext, (PropertySymbolMarker) callableSymbolMarker, (PropertySymbolMarker) callableSymbolMarker2);
            if (propertiesIncompatibility != null) {
                return propertiesIncompatibility;
            }
        } else if (!(callableSymbolMarker instanceof EnumEntrySymbolMarker) || !(callableSymbolMarker2 instanceof EnumEntrySymbolMarker)) {
            throw new IllegalStateException(("Unsupported declarations: " + callableSymbolMarker + ", " + callableSymbolMarker2).toString());
        }
        return null;
    }

    private final boolean valueParametersCountCompatible(ExpectActualMatchingContext<?> expectActualMatchingContext, CallableSymbolMarker callableSymbolMarker, CallableSymbolMarker callableSymbolMarker2, List<? extends ValueParameterSymbolMarker> list, List<? extends ValueParameterSymbolMarker> list2) {
        if (list.size() == list2.size()) {
            return true;
        }
        if (expectActualMatchingContext.isAnnotationConstructor(callableSymbolMarker) && expectActualMatchingContext.isAnnotationConstructor(callableSymbolMarker2) && list.isEmpty()) {
            List<? extends ValueParameterSymbolMarker> list3 = list2;
            if ((list3 instanceof Collection) && list3.isEmpty()) {
                return true;
            }
            Iterator<T> it = list3.iterator();
            while (it.hasNext()) {
                if (!expectActualMatchingContext.getHasDefaultValue((ValueParameterSymbolMarker) it.next())) {
                }
            }
            return true;
        }
        return false;
    }

    private final boolean areCompatibleTypeLists(ExpectActualMatchingContext<?> expectActualMatchingContext, List<? extends KotlinTypeMarker> list, List<? extends KotlinTypeMarker> list2) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!expectActualMatchingContext.areCompatibleExpectActualTypes(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private final boolean areCompatibleClassKinds(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2) {
        if (expectActualMatchingContext.getClassKind(regularClassSymbolMarker) == expectActualMatchingContext.getClassKind(regularClassSymbolMarker2)) {
            return true;
        }
        return expectActualMatchingContext.getClassKind(regularClassSymbolMarker) == ClassKind.CLASS && isFinal(expectActualMatchingContext, regularClassSymbolMarker) && isCtorless(expectActualMatchingContext, regularClassSymbolMarker) && expectActualMatchingContext.getClassKind(regularClassSymbolMarker2) == ClassKind.OBJECT;
    }

    static /* synthetic */ boolean areCompatibleModalities$default(AbstractExpectActualCompatibilityChecker abstractExpectActualCompatibilityChecker, Modality modality, Modality modality2, Modality modality3, Modality modality4, int i, Object obj) {
        if ((i & 4) != 0) {
            modality3 = null;
        }
        if ((i & 8) != 0) {
            modality4 = null;
        }
        return abstractExpectActualCompatibilityChecker.areCompatibleModalities(modality, modality2, modality3, modality4);
    }

    private final boolean areCompatibleModalities(Modality expectModality, Modality actualModality, Modality expectContainingClassModality, Modality actualContainingClassModality) {
        Modality modalityEffectiveModality = effectiveModality(expectModality, expectContainingClassModality);
        return ((EnumSet) MapsKt.getValue(compatibleModalityMap, modalityEffectiveModality)).contains(effectiveModality(actualModality, actualContainingClassModality));
    }

    private final Modality effectiveModality(Modality declarationModality, Modality containingClassModality) {
        return (containingClassModality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[containingClassModality.ordinal()]) == 1 ? Modality.FINAL : declarationModality;
    }

    private final boolean areCompatibleCallableVisibilities(Visibility expectVisibility, Modality expectModality, Visibility actualVisibility) {
        Integer numCompare = Visibilities.INSTANCE.compare(expectVisibility, actualVisibility);
        if (expectModality != Modality.FINAL) {
            if (numCompare != null && numCompare.intValue() == 0) {
                return true;
            }
        } else if (numCompare != null && numCompare.intValue() <= 0) {
            return true;
        }
        return false;
    }

    private final boolean areCompatibleClassVisibilities(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker, RegularClassSymbolMarker regularClassSymbolMarker2) {
        Visibility visibility = expectActualMatchingContext.getVisibility(regularClassSymbolMarker);
        Visibility visibility2 = expectActualMatchingContext.getVisibility(regularClassSymbolMarker2);
        if (Intrinsics.areEqual(visibility, visibility2)) {
            return true;
        }
        if (!expectActualMatchingContext.getAllowClassActualizationWithWiderVisibility()) {
            return false;
        }
        Integer numCompare = Visibilities.INSTANCE.compare(visibility2, visibility);
        return numCompare != null && numCompare.intValue() > 0;
    }

    private final boolean areCompatibleTypeParameterUpperBounds(ExpectActualMatchingContext<?> expectActualMatchingContext, List<? extends TypeParameterSymbolMarker> list, List<? extends TypeParameterSymbolMarker> list2, TypeSubstitutorMarker typeSubstitutorMarker) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            List<KotlinTypeMarker> bounds = expectActualMatchingContext.getBounds(list.get(i));
            List<KotlinTypeMarker> bounds2 = expectActualMatchingContext.getBounds(list2.get(i));
            if (bounds.size() == bounds2.size()) {
                List<KotlinTypeMarker> list3 = bounds;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it = list3.iterator();
                while (it.hasNext()) {
                    arrayList.add(expectActualMatchingContext.safeSubstitute(typeSubstitutorMarker, (KotlinTypeMarker) it.next()));
                }
                if (areCompatibleTypeLists(expectActualMatchingContext, arrayList, bounds2)) {
                }
            }
            return false;
        }
        return true;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getFunctionsIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, CallableSymbolMarker callableSymbolMarker, CallableSymbolMarker callableSymbolMarker2) {
        if (expectActualMatchingContext.isSuspend(callableSymbolMarker) != expectActualMatchingContext.isSuspend(callableSymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.FunctionModifiersDifferent.INSTANCE;
        }
        if ((!expectActualMatchingContext.isInfix(callableSymbolMarker) || expectActualMatchingContext.isInfix(callableSymbolMarker2)) && ((!expectActualMatchingContext.isInline(callableSymbolMarker) || expectActualMatchingContext.isInline(callableSymbolMarker2)) && (!expectActualMatchingContext.isOperator(callableSymbolMarker) || expectActualMatchingContext.isOperator(callableSymbolMarker2)))) {
            return null;
        }
        return ExpectActualCompatibility.Incompatible.FunctionModifiersNotSubset.INSTANCE;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getPropertiesIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, PropertySymbolMarker propertySymbolMarker, PropertySymbolMarker propertySymbolMarker2) {
        if (expectActualMatchingContext.isVar(propertySymbolMarker) != expectActualMatchingContext.isVar(propertySymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.PropertyKind.INSTANCE;
        }
        if (expectActualMatchingContext.isLateinit(propertySymbolMarker) != expectActualMatchingContext.isLateinit(propertySymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.PropertyLateinitModifier.INSTANCE;
        }
        if (expectActualMatchingContext.isConst(propertySymbolMarker) && !expectActualMatchingContext.isConst(propertySymbolMarker2)) {
            return ExpectActualCompatibility.Incompatible.PropertyConstModifier.INSTANCE;
        }
        if (arePropertySettersWithCompatibleVisibilities(expectActualMatchingContext, propertySymbolMarker, propertySymbolMarker2)) {
            return null;
        }
        return ExpectActualCompatibility.Incompatible.PropertySetterVisibility.INSTANCE;
    }

    private final boolean arePropertySettersWithCompatibleVisibilities(ExpectActualMatchingContext<?> expectActualMatchingContext, PropertySymbolMarker propertySymbolMarker, PropertySymbolMarker propertySymbolMarker2) {
        FunctionSymbolMarker setter;
        FunctionSymbolMarker setter2 = expectActualMatchingContext.getSetter(propertySymbolMarker);
        if (setter2 == null || (setter = expectActualMatchingContext.getSetter(propertySymbolMarker2)) == null) {
            return true;
        }
        FunctionSymbolMarker functionSymbolMarker = setter2;
        return areCompatibleCallableVisibilities(expectActualMatchingContext.getVisibility(functionSymbolMarker), expectActualMatchingContext.getModality(functionSymbolMarker), expectActualMatchingContext.getVisibility(setter));
    }

    private final List<KotlinTypeMarker> toTypeList(ExpectActualMatchingContext<?> expectActualMatchingContext, List<? extends ValueParameterSymbolMarker> list, TypeSubstitutorMarker typeSubstitutorMarker) {
        List<? extends ValueParameterSymbolMarker> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(expectActualMatchingContext.safeSubstitute(typeSubstitutorMarker, expectActualMatchingContext.getReturnType((ValueParameterSymbolMarker) it.next())));
        }
        return arrayList;
    }

    private final Name getName(ExpectActualMatchingContext<?> expectActualMatchingContext, DeclarationSymbolMarker declarationSymbolMarker) {
        if (declarationSymbolMarker instanceof ConstructorSymbolMarker) {
            return SpecialNames.INIT;
        }
        if (declarationSymbolMarker instanceof ValueParameterSymbolMarker) {
            return expectActualMatchingContext.getParameterName((ValueParameterSymbolMarker) declarationSymbolMarker);
        }
        if (declarationSymbolMarker instanceof CallableSymbolMarker) {
            return expectActualMatchingContext.getCallableId((CallableSymbolMarker) declarationSymbolMarker).getCallableName();
        }
        if (declarationSymbolMarker instanceof RegularClassSymbolMarker) {
            Name shortClassName = expectActualMatchingContext.getClassId((RegularClassSymbolMarker) declarationSymbolMarker).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            return shortClassName;
        }
        if (declarationSymbolMarker instanceof TypeAliasSymbolMarker) {
            Name shortClassName2 = expectActualMatchingContext.getClassId((TypeAliasSymbolMarker) declarationSymbolMarker).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
            return shortClassName2;
        }
        if (declarationSymbolMarker instanceof TypeParameterSymbolMarker) {
            return expectActualMatchingContext.getParameterName((TypeParameterSymbolMarker) declarationSymbolMarker);
        }
        throw new IllegalStateException(("Unsupported declaration: " + declarationSymbolMarker).toString());
    }

    private final boolean isCtorless(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker) {
        return expectActualMatchingContext.getMembersForExpectClass(regularClassSymbolMarker, SpecialNames.INIT).isEmpty();
    }

    private final boolean isFinal(ExpectActualMatchingContext<?> expectActualMatchingContext, RegularClassSymbolMarker regularClassSymbolMarker) {
        return expectActualMatchingContext.getModality(regularClassSymbolMarker) == Modality.FINAL;
    }

    private final ExpectActualCompatibility.Incompatible.WeakIncompatible<?> getTypeParametersVarianceOrReifiedIncompatibility(ExpectActualMatchingContext<?> expectActualMatchingContext, List<? extends TypeParameterSymbolMarker> list, List<? extends TypeParameterSymbolMarker> list2) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!Intrinsics.areEqual(expectActualMatchingContext.getVariance(list.get(i)), expectActualMatchingContext.getVariance(list2.get(i)))) {
                return ExpectActualCompatibility.Incompatible.TypeParameterVariance.INSTANCE;
            }
        }
        Iterable indices = CollectionsKt.getIndices(list);
        if ((indices instanceof Collection) && ((Collection) indices).isEmpty()) {
            return null;
        }
        Iterator it = indices.iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            if (!expectActualMatchingContext.isReified(list.get(iNextInt)) && expectActualMatchingContext.isReified(list2.get(iNextInt))) {
                return ExpectActualCompatibility.Incompatible.TypeParameterReified.INSTANCE;
            }
        }
        return null;
    }
}
