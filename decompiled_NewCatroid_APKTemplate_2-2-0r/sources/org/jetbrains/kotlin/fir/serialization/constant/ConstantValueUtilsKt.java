package org.jetbrains.kotlin.fir.serialization.constant;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.constant.AnnotationValue;
import org.jetbrains.kotlin.constant.ArrayValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.ErrorValue;
import org.jetbrains.kotlin.constant.KClassValue;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConstantValueUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\u001a\u0016\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001aB\u0010\u0004\u001a\u0016\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00010\u0005*\u0016\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00010\u00052\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tH\u0000\u001a\u001e\u0010\n\u001a\u0002H\u000b\"\n\b\u0000\u0010\u000b\u0018\u0001*\u00020\u0003*\u00020\fH\u0086\b¢\u0006\u0002\u0010\r\u001a \u0010\u000e\u001a\u0004\u0018\u0001H\u000b\"\n\b\u0000\u0010\u000b\u0018\u0001*\u00020\u0003*\u00020\u000fH\u0086\b¢\u0006\u0002\u0010\u0010\u001aJ\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\u0005*\u001e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u0012j\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u0014`\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0000\u001a:\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u0006\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\u0005*\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00130\u001a2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0000¨\u0006\u001b"}, d2 = {CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/constant/ConstantValue;", "argumentType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "addEmptyVarargValuesFor", "", "Lorg/jetbrains/kotlin/name/Name;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "coneType", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/constant/KClassValue$Value$LocalClass;", "(Lorg/jetbrains/kotlin/constant/KClassValue$Value$LocalClass;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "coneTypeSafe", "Lorg/jetbrains/kotlin/constant/AnnotationValue;", "(Lorg/jetbrains/kotlin/constant/AnnotationValue;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "convertToConstantValues", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "", "fir-serialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstantValueUtilsKt {
    public static final Map<Name, ConstantValue<?>> convertToConstantValues(Map<Name, ? extends FirExpression> map, FirSession session, ConstValueProvider constValueProvider) {
        ErrorValue.ErrorValueWithMessage errorValueWithMessageFindConstantValueFor;
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            FirExpression firExpression = (FirExpression) entry.getValue();
            if (constValueProvider == null || (errorValueWithMessageFindConstantValueFor = constValueProvider.findConstantValueFor(firExpression)) == null) {
                ConstantValue<?> constantValueFindConstantValueFor = constValueProvider != null ? constValueProvider.findConstantValueFor(firExpression) : null;
                if (!(constantValueFindConstantValueFor instanceof ConstantValue)) {
                    constantValueFindConstantValueFor = null;
                }
                if (constantValueFindConstantValueFor == null) {
                    Object objAccept = firExpression.accept(FirToConstantValueTransformer.INSTANCE, new FirToConstantValueTransformerData(session, constValueProvider));
                    if (!(objAccept instanceof ConstantValue)) {
                        objAccept = null;
                    }
                    constantValueFindConstantValueFor = (ConstantValue) objAccept;
                }
                if (constantValueFindConstantValueFor == null) {
                    ErrorValue.ErrorValueWithMessage errorValueWithMessage = ((Boolean) FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).getFlag(AnalysisFlags.getMetadataCompilation())).booleanValue() ? new ErrorValue.ErrorValueWithMessage("Constant conversion can be ignored in metadata compilation mode") : null;
                    if (errorValueWithMessage != null) {
                        errorValueWithMessageFindConstantValueFor = errorValueWithMessage;
                    } else {
                        throw new IllegalStateException(("Cannot convert expression " + UtilsKt.render(firExpression) + " to constant").toString());
                    }
                } else {
                    errorValueWithMessageFindConstantValueFor = constantValueFindConstantValueFor;
                }
            }
            linkedHashMap.put(key, errorValueWithMessageFindConstantValueFor);
        }
        return linkedHashMap;
    }

    public static final Map<Name, ConstantValue<Object>> addEmptyVarargValuesFor(Map<Name, ConstantValue<Object>> map, FirFunctionSymbol<?> firFunctionSymbol) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        if (firFunctionSymbol != null) {
            int i = 0;
            for (FirValueParameterSymbol firValueParameterSymbol : firFunctionSymbol.getValueParameterSymbols()) {
                int i2 = i + 1;
                if (!map.containsKey(firValueParameterSymbol.getName()) && firValueParameterSymbol.isVararg() && !DeclarationUtilsKt.itOrExpectHasDefaultParameterValue((FirFunction) firFunctionSymbol.getFir(), i)) {
                    map.put(firValueParameterSymbol.getName(), new ArrayValue(CollectionsKt.emptyList()));
                }
                i = i2;
            }
        }
        return map;
    }

    public static final Map<Name, ConstantValue<?>> convertToConstantValues(LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap, FirSession session, ConstValueProvider constValueProvider) {
        Intrinsics.checkNotNullParameter(linkedHashMap, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Set<Map.Entry<FirExpression, FirValueParameter>> setEntrySet = linkedHashMap.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Set<Map.Entry<FirExpression, FirValueParameter>> set = setEntrySet;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(set, 10)), 16));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Intrinsics.checkNotNull(entry);
            Pair pair = TuplesKt.to(((FirValueParameter) entry.getValue()).getName(), (FirExpression) entry.getKey());
            linkedHashMap2.put(pair.getFirst(), pair.getSecond());
        }
        return convertToConstantValues((Map<Name, ? extends FirExpression>) linkedHashMap2, session, constValueProvider);
    }

    public static final /* synthetic */ <T extends ConeKotlinType> T coneTypeSafe(AnnotationValue annotationValue) {
        Intrinsics.checkNotNullParameter(annotationValue, "<this>");
        KotlinTypeMarker type = annotationValue.getValue().getType();
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) type;
    }

    public static final /* synthetic */ <T extends ConeKotlinType> T coneType(KClassValue.Value.LocalClass localClass) {
        Intrinsics.checkNotNullParameter(localClass, "<this>");
        KotlinTypeMarker type = localClass.getType();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) type;
    }

    public static final ConstantValue<?> create(ConeKotlinType argumentType) {
        ConeKotlinType coneKotlinTypeArrayElementType;
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        if ((argumentType instanceof ConeErrorType) || !(argumentType instanceof ConeClassLikeType)) {
            return null;
        }
        int i = 0;
        while (!ConeBuiltinTypeUtilsKt.isPrimitiveArray(argumentType) && (coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(argumentType)) != null) {
            i++;
            argumentType = coneKotlinTypeArrayElementType;
        }
        ClassId classId = ConeTypeUtilsKt.getClassId(argumentType);
        if (classId == null) {
            return null;
        }
        return new KClassValue(classId, i);
    }
}
