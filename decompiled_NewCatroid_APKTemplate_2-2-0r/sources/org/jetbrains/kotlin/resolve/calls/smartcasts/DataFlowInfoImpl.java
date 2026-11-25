package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import javaslang.Tuple2;
import javaslang.collection.Iterator;
import javaslang.collection.LinkedHashSet;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.SetMultimap;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.javaslang.JavaslangAdaptersKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: DataFlowInfoImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0000\u0018\u0000 >2\u00020\u0001:\u0001>B\u0007\b\u0016¢\u0006\u0002\u0010\u0002BU\b\u0002\u0012\"\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007\u0012(\u0010\b\u001a$\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n`\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001H\u0016J4\u0010\u0012\u001a\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u00132\u0016\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u0013H\u0002J \u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J \u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J(\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J0\u0010 \u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fH\u0002J \u0010\"\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0005H\u0016J&\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010)\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u001fH\u0002J\u0010\u0010+\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0005H\u0016J&\u0010,\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u001f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020\n0'2\u0006\u0010%\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010-\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0001H\u0016JV\u0010.\u001a\u00020/2\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006012\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u00102\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0016\b\u0002\u00103\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0018\u0001042\b\b\u0002\u00105\u001a\u00020\u001fH\u0002J\b\u00106\u001a\u000207H\u0016J\u0014\u00108\u001a\u00020\u001f*\u00020\n2\u0006\u00109\u001a\u00020\u0019H\u0002J \u0010:\u001a\u00020\u001f*\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u0013H\u0002JL\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u0013*\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u00132\u001a\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u0013H\u0002JP\u0010<\u001a\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u0013*\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u00132\u001a\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\n\u0018\u00010\tj\n\u0012\u0004\u0012\u00020\n\u0018\u0001`\u0013H\u0002J\u0014\u0010=\u001a\u00020\n*\u00020\n2\u0006\u00109\u001a\u00020\u0019H\u0002R0\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR6\u0010\b\u001a$\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n`\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfoImpl;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "()V", "completeNullabilityInfo", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "completeTypeInfo", "Ljavaslang/collection/Set;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ImmutableMultimap;", "(Ljavaslang/collection/Map;Ljavaslang/collection/Map;)V", "getCompleteNullabilityInfo", "()Ljavaslang/collection/Map;", "getCompleteTypeInfo", "and", "other", "approximateDefinitelyNotNullableTypes", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableSet;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "assign", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "clearValueInfo", "value", "disequate", "equate", "identityEquals", "", "equateOrDisequate", "isEquate", "establishSubtyping", ModuleXmlParser.TYPE, "getCollectedNullability", ASTExpr.DEFAULT_MAP_KEY_NAME, "getCollectedTypes", "", "enrichWithNotNull", "getNullability", "stableOnly", "getStableNullability", "getStableTypes", "or", "putNullabilityAndTypeInfo", "", "map", "", "nullability", "newTypeInfoBuilder", "Lorg/jetbrains/kotlin/com/google/common/collect/SetMultimap;", "recordUnstable", "toString", "", "canBeDefinitelyNotNullOrNotNull", "settings", "containsNothing", "intersect", "intersectConsideringNothing", "makeReallyNotNullIfNeeded", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowInfoImpl implements DataFlowInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<DataFlowValue, Nullability> EMPTY_NULLABILITY_INFO;
    private static final Map<DataFlowValue, Set<KotlinType>> EMPTY_TYPE_INFO;
    private final Map<DataFlowValue, Nullability> completeNullabilityInfo;
    private final Map<DataFlowValue, Set<KotlinType>> completeTypeInfo;

    public /* synthetic */ DataFlowInfoImpl(Map map, Map map2, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, map2);
    }

    private DataFlowInfoImpl(Map<DataFlowValue, Nullability> map, Map<DataFlowValue, Set<KotlinType>> map2) {
        this.completeNullabilityInfo = map;
        this.completeTypeInfo = map2;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public Map<DataFlowValue, Nullability> getCompleteNullabilityInfo() {
        return this.completeNullabilityInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public Map<DataFlowValue, Set<KotlinType>> getCompleteTypeInfo() {
        return this.completeTypeInfo;
    }

    public DataFlowInfoImpl() {
        this(EMPTY_NULLABILITY_INFO, EMPTY_TYPE_INFO);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public Nullability getCollectedNullability(DataFlowValue key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getNullability(key, false);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public Nullability getStableNullability(DataFlowValue key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getNullability(key, true);
    }

    private final Nullability getNullability(DataFlowValue key, boolean stableOnly) {
        if (stableOnly && !key.getIsStable()) {
            return key.getImmanentNullability();
        }
        Nullability orElse = getCompleteNullabilityInfo().get(key).getOrElse(key.getImmanentNullability());
        Intrinsics.checkNotNull(orElse);
        return orElse;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void putNullabilityAndTypeInfo$default(DataFlowInfoImpl dataFlowInfoImpl, java.util.Map map, DataFlowValue dataFlowValue, Nullability nullability, LanguageVersionSettings languageVersionSettings, SetMultimap setMultimap, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            setMultimap = null;
        }
        SetMultimap setMultimap2 = setMultimap;
        if ((i & 32) != 0) {
            z = true;
        }
        dataFlowInfoImpl.putNullabilityAndTypeInfo(map, dataFlowValue, nullability, languageVersionSettings, setMultimap2, z);
    }

    private final void putNullabilityAndTypeInfo(java.util.Map<DataFlowValue, Nullability> map, DataFlowValue value, Nullability nullability, LanguageVersionSettings languageVersionSettings, SetMultimap<DataFlowValue, KotlinType> newTypeInfoBuilder, boolean recordUnstable) {
        DataFlowValue bound;
        if (value.getIsStable() || recordUnstable) {
            map.put(value, nullability);
        }
        IdentifierInfo identifierInfo = value.getIdentifierInfo();
        if (nullability.canBeNull() || !languageVersionSettings.supportsFeature(LanguageFeature.SafeCallBoundSmartCasts)) {
            return;
        }
        if (identifierInfo instanceof IdentifierInfo.Qualified) {
            IdentifierInfo.Qualified qualified = (IdentifierInfo.Qualified) identifierInfo;
            KotlinType receiverType = qualified.getReceiverType();
            if (!qualified.getSafe() || receiverType == null) {
                return;
            }
            putNullabilityAndTypeInfo(map, new DataFlowValue(qualified.getReceiverInfo(), receiverType, null, 4, null), nullability, languageVersionSettings, newTypeInfoBuilder, recordUnstable);
            return;
        }
        if (identifierInfo instanceof IdentifierInfo.SafeCast) {
            IdentifierInfo.SafeCast safeCast = (IdentifierInfo.SafeCast) identifierInfo;
            KotlinType targetType = safeCast.getTargetType();
            KotlinType subjectType = safeCast.getSubjectType();
            if (targetType == null || subjectType == null || !languageVersionSettings.supportsFeature(LanguageFeature.SafeCastCheckBoundSmartCasts)) {
                return;
            }
            DataFlowValue dataFlowValue = new DataFlowValue(safeCast.getSubjectInfo(), subjectType, null, 4, null);
            putNullabilityAndTypeInfo(map, dataFlowValue, nullability, languageVersionSettings, newTypeInfoBuilder, false);
            if (!dataFlowValue.getIsStable() || newTypeInfoBuilder == null) {
                return;
            }
            newTypeInfoBuilder.put(dataFlowValue, targetType);
            return;
        }
        if (!(identifierInfo instanceof IdentifierInfo.Variable) || (bound = ((IdentifierInfo.Variable) identifierInfo).getBound()) == null) {
            return;
        }
        putNullabilityAndTypeInfo(map, bound, nullability, languageVersionSettings, newTypeInfoBuilder, recordUnstable);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public java.util.Set<KotlinType> getCollectedTypes(DataFlowValue key, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return getCollectedTypes(key, true, languageVersionSettings);
    }

    private final java.util.Set<KotlinType> getCollectedTypes(DataFlowValue key, boolean enrichWithNotNull, LanguageVersionSettings languageVersionSettings) {
        Set<KotlinType> orElse = getCompleteTypeInfo().get(key).getOrElse(LinkedHashSet.empty());
        if (!enrichWithNotNull || getCollectedNullability(key).canBeNull()) {
            java.util.Set<KotlinType> javaSet = orElse.toJavaSet();
            Intrinsics.checkNotNullExpressionValue(javaSet, "toJavaSet(...)");
            return javaSet;
        }
        java.util.LinkedHashSet linkedHashSetNewLinkedHashSetWithExpectedSize = CollectionsKt.newLinkedHashSetWithExpectedSize(orElse.size() + 1);
        KotlinType type = key.getType();
        Intrinsics.checkNotNull(orElse);
        for (KotlinType kotlinType : orElse) {
            Intrinsics.checkNotNull(kotlinType);
            linkedHashSetNewLinkedHashSetWithExpectedSize.add(makeReallyNotNullIfNeeded(kotlinType, languageVersionSettings));
        }
        if (canBeDefinitelyNotNullOrNotNull(type, languageVersionSettings)) {
            linkedHashSetNewLinkedHashSetWithExpectedSize.add(makeReallyNotNullIfNeeded(type, languageVersionSettings));
        }
        return linkedHashSetNewLinkedHashSetWithExpectedSize;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public java.util.Set<KotlinType> getStableTypes(DataFlowValue key, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return getStableTypes(key, true, languageVersionSettings);
    }

    private final java.util.Set<KotlinType> getStableTypes(DataFlowValue key, boolean enrichWithNotNull, LanguageVersionSettings languageVersionSettings) {
        return !key.getIsStable() ? new java.util.LinkedHashSet() : getCollectedTypes(key, enrichWithNotNull, languageVersionSettings);
    }

    private final boolean canBeDefinitelyNotNullOrNotNull(KotlinType kotlinType, LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            return TypeUtils.isNullableType(kotlinType);
        }
        return kotlinType.getIsMarkedNullable();
    }

    private final KotlinType makeReallyNotNullIfNeeded(KotlinType kotlinType, LanguageVersionSettings languageVersionSettings) {
        if (languageVersionSettings.supportsFeature(LanguageFeature.NewInference)) {
            return SpecialTypesKt.makeDefinitelyNotNullOrNotNull$default(kotlinType.unwrap(), false, 1, null);
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        return kotlinTypeMakeNotNullable;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo clearValueInfo(DataFlowValue value, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        HashMap map = new HashMap();
        putNullabilityAndTypeInfo$default(this, map, value, value.getImmanentNullability(), languageVersionSettings, null, false, 48, null);
        return INSTANCE.create(this, map, EMPTY_TYPE_INFO, value);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo assign(DataFlowValue a, DataFlowValue b, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        java.util.Map mapMapOf = MapsKt.mapOf(TuplesKt.to(a, getStableNullability(b)));
        java.util.Set<KotlinType> stableTypes = getStableTypes(b, languageVersionSettings);
        if (!KotlinTypeKt.isError(b.getType()) && !Intrinsics.areEqual(a.getType(), b.getType())) {
            stableTypes = SetsKt.plus(stableTypes, b.getType());
        }
        return INSTANCE.create(this, mapMapOf, kotlin.collections.CollectionsKt.listOf(new Tuple2(a, stableTypes)), a);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo equate(DataFlowValue a, DataFlowValue b, boolean identityEquals, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return equateOrDisequate(a, b, languageVersionSettings, identityEquals, true);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo disequate(DataFlowValue a, DataFlowValue b, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return equateOrDisequate(a, b, languageVersionSettings, false, false);
    }

    private final DataFlowInfo equateOrDisequate(DataFlowValue a, DataFlowValue b, LanguageVersionSettings languageVersionSettings, boolean identityEquals, boolean isEquate) {
        Nullability nullabilityInvert;
        Nullability nullabilityInvert2;
        HashMap map = new HashMap();
        Companion companion = INSTANCE;
        SetMultimap setMultimapNewTypeInfoBuilder = companion.newTypeInfoBuilder();
        Nullability stableNullability = getStableNullability(a);
        Nullability stableNullability2 = getStableNullability(b);
        if (isEquate) {
            nullabilityInvert = stableNullability2;
        } else {
            nullabilityInvert = stableNullability2.invert();
            Intrinsics.checkNotNullExpressionValue(nullabilityInvert, "invert(...)");
        }
        Nullability nullabilityRefine = stableNullability.refine(nullabilityInvert);
        Intrinsics.checkNotNullExpressionValue(nullabilityRefine, "refine(...)");
        if (isEquate) {
            nullabilityInvert2 = stableNullability;
        } else {
            nullabilityInvert2 = stableNullability.invert();
            Intrinsics.checkNotNullExpressionValue(nullabilityInvert2, "invert(...)");
        }
        Nullability nullabilityRefine2 = stableNullability2.refine(nullabilityInvert2);
        Intrinsics.checkNotNullExpressionValue(nullabilityRefine2, "refine(...)");
        HashMap map2 = map;
        putNullabilityAndTypeInfo$default(this, map2, a, nullabilityRefine, languageVersionSettings, setMultimapNewTypeInfoBuilder, false, 32, null);
        putNullabilityAndTypeInfo$default(this, map2, b, nullabilityRefine2, languageVersionSettings, setMultimapNewTypeInfoBuilder, false, 32, null);
        boolean zIsEmpty = (getCollectedNullability(a) == nullabilityRefine && getCollectedNullability(b) == nullabilityRefine2) ? false : true;
        if (isEquate && (identityEquals || !stableNullability.canBeNonNull() || !stableNullability2.canBeNonNull())) {
            setMultimapNewTypeInfoBuilder.putAll(a, getStableTypes(b, false, languageVersionSettings));
            setMultimapNewTypeInfoBuilder.putAll(b, getStableTypes(a, false, languageVersionSettings));
            if (!Intrinsics.areEqual(a.getType(), b.getType())) {
                if (!TypeUtilsKt.isSubtypeOf(a.getType(), b.getType())) {
                    setMultimapNewTypeInfoBuilder.put(a, b.getType());
                }
                if (!TypeUtilsKt.isSubtypeOf(b.getType(), a.getType())) {
                    setMultimapNewTypeInfoBuilder.put(b, a.getType());
                }
            }
            zIsEmpty |= true ^ setMultimapNewTypeInfoBuilder.isEmpty();
        }
        return zIsEmpty ? companion.create(this, map2, setMultimapNewTypeInfoBuilder) : this;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo establishSubtyping(DataFlowValue value, KotlinType type, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (!Intrinsics.areEqual(value.getType(), type) && !getCollectedTypes(value, languageVersionSettings).contains(type)) {
            if (!FlexibleTypesKt.isFlexible(value.getType()) && TypeUtilsKt.isSubtypeOf(value.getType(), type)) {
                return this;
            }
            HashMap map = new HashMap();
            if (!languageVersionSettings.supportsFeature(LanguageFeature.NewInference) ? !type.getIsMarkedNullable() : !TypeUtils.isNullableType(type)) {
                putNullabilityAndTypeInfo$default(this, map, value, Nullability.NOT_NULL, languageVersionSettings, null, false, 48, null);
            }
            return Companion.create$default(INSTANCE, this, map, kotlin.collections.CollectionsKt.listOf(new Tuple2(value, kotlin.collections.CollectionsKt.listOf(type))), null, 8, null);
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo and(DataFlowInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (other == DataFlowInfo.INSTANCE.getEMPTY()) {
            return this;
        }
        if (this == DataFlowInfo.INSTANCE.getEMPTY()) {
            return other;
        }
        if (this == other) {
            return this;
        }
        boolean z = other instanceof DataFlowInfoImpl;
        HashMap map = new HashMap();
        Iterator<Tuple2<DataFlowValue, Nullability>> it = other.getCompleteNullabilityInfo().iterator();
        while (it.hasNext()) {
            Tuple2<DataFlowValue, Nullability> next = it.next();
            Intrinsics.checkNotNull(next);
            DataFlowValue dataFlowValue = (DataFlowValue) JavaslangAdaptersKt.component1(next);
            Nullability nullability = (Nullability) JavaslangAdaptersKt.component2(next);
            Intrinsics.checkNotNull(dataFlowValue);
            Nullability collectedNullability = getCollectedNullability(dataFlowValue);
            Nullability nullabilityAnd = collectedNullability.and(nullability);
            Intrinsics.checkNotNullExpressionValue(nullabilityAnd, "and(...)");
            if (nullabilityAnd != collectedNullability) {
                map.put(dataFlowValue, nullabilityAnd);
            }
        }
        return Companion.create$default(INSTANCE, this, map, other.getCompleteTypeInfo(), null, 8, null);
    }

    private final boolean containsNothing(Set<KotlinType> set) {
        if (set == null) {
            return false;
        }
        Set<KotlinType> set2 = set;
        if ((set2 instanceof Collection) && ((Collection) set2).isEmpty()) {
            return false;
        }
        java.util.Iterator<KotlinType> it = set2.iterator();
        while (it.hasNext()) {
            if (KotlinBuiltIns.isNothing(it.next())) {
                return true;
            }
        }
        return false;
    }

    private final Set<KotlinType> intersectConsideringNothing(Set<KotlinType> set, Set<KotlinType> set2) {
        if (containsNothing(set2)) {
            return set;
        }
        return containsNothing(set) ? set2 : intersect(set, set2);
    }

    private final Set<KotlinType> intersect(Set<KotlinType> set, Set<KotlinType> set2) {
        Set<KotlinType> setIntersect;
        if (set == null) {
            if (set2 != null) {
                return set2;
            }
            LinkedHashSet linkedHashSetEmpty = LinkedHashSet.empty();
            Intrinsics.checkNotNullExpressionValue(linkedHashSetEmpty, "empty(...)");
            return linkedHashSetEmpty;
        }
        if (set2 == null) {
            return set;
        }
        Set<KotlinType> setApproximateDefinitelyNotNullableTypes = approximateDefinitelyNotNullableTypes(set);
        Set<KotlinType> setApproximateDefinitelyNotNullableTypes2 = approximateDefinitelyNotNullableTypes(set2);
        if ((setApproximateDefinitelyNotNullableTypes == null && setApproximateDefinitelyNotNullableTypes2 == null) || (setApproximateDefinitelyNotNullableTypes != null && setApproximateDefinitelyNotNullableTypes2 != null)) {
            setIntersect = set.intersect(set2);
        } else {
            if (setApproximateDefinitelyNotNullableTypes != null) {
                set = setApproximateDefinitelyNotNullableTypes;
            }
            if (setApproximateDefinitelyNotNullableTypes2 != null) {
                set2 = setApproximateDefinitelyNotNullableTypes2;
            }
            setIntersect = set.intersect(set2);
        }
        Intrinsics.checkNotNull(setIntersect);
        return setIntersect;
    }

    private final Set<KotlinType> approximateDefinitelyNotNullableTypes(Set<KotlinType> set) {
        Set<KotlinType> set2 = set;
        if ((set2 instanceof Collection) && ((Collection) set2).isEmpty()) {
            return null;
        }
        for (KotlinType kotlinType : set2) {
            Intrinsics.checkNotNull(kotlinType);
            if (SpecialTypesKt.isDefinitelyNotNullType(kotlinType)) {
                final AnonymousClass2 anonymousClass2 = new Function1<KotlinType, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoImpl.approximateDefinitelyNotNullableTypes.2
                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinType invoke(KotlinType kotlinType2) {
                        return kotlinType2 instanceof DefinitelyNotNullType ? TypeUtilsKt.makeNotNullable(((DefinitelyNotNullType) kotlinType2).getOriginal()) : kotlinType2;
                    }
                };
                return set.map(new Function() { // from class: org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return DataFlowInfoImpl.approximateDefinitelyNotNullableTypes$lambda$5(anonymousClass2, obj);
                    }
                });
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinType approximateDefinitelyNotNullableTypes$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (KotlinType) tmp0.invoke(obj);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo
    public DataFlowInfo or(DataFlowInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (other != DataFlowInfo.INSTANCE.getEMPTY() && this != DataFlowInfo.INSTANCE.getEMPTY()) {
            if (this == other) {
                return this;
            }
            boolean z = other instanceof DataFlowInfoImpl;
            HashMap map = new HashMap();
            Iterator<Tuple2<DataFlowValue, Nullability>> it = other.getCompleteNullabilityInfo().iterator();
            while (it.hasNext()) {
                Tuple2<DataFlowValue, Nullability> next = it.next();
                Intrinsics.checkNotNull(next);
                DataFlowValue dataFlowValue = (DataFlowValue) JavaslangAdaptersKt.component1(next);
                Nullability nullability = (Nullability) JavaslangAdaptersKt.component2(next);
                Intrinsics.checkNotNull(dataFlowValue);
                map.put(dataFlowValue, getCollectedNullability(dataFlowValue).or(nullability));
            }
            Map<DataFlowValue, Set<KotlinType>> completeTypeInfo = getCompleteTypeInfo();
            Map<DataFlowValue, Set<KotlinType>> completeTypeInfo2 = other.getCompleteTypeInfo();
            SetMultimap setMultimapNewTypeInfoBuilder = INSTANCE.newTypeInfoBuilder();
            Iterator<DataFlowValue> it2 = completeTypeInfo.keySet().iterator();
            while (it2.hasNext()) {
                DataFlowValue next2 = it2.next();
                if (completeTypeInfo2.keySet().contains(next2)) {
                    Option<Set<KotlinType>> option = completeTypeInfo.get(next2);
                    Intrinsics.checkNotNullExpressionValue(option, "get(...)");
                    Set<KotlinType> set = (Set) JavaslangAdaptersKt.getOrNull(option);
                    Option<Set<KotlinType>> option2 = completeTypeInfo2.get(next2);
                    Intrinsics.checkNotNullExpressionValue(option2, "get(...)");
                    Set<KotlinType> setIntersectConsideringNothing = intersectConsideringNothing(set, (Set) JavaslangAdaptersKt.getOrNull(option2));
                    if (setIntersectConsideringNothing == null) {
                        setIntersectConsideringNothing = LinkedHashSet.empty();
                    }
                    setMultimapNewTypeInfoBuilder.putAll(next2, setIntersectConsideringNothing);
                }
            }
            return INSTANCE.create(null, map, setMultimapNewTypeInfoBuilder);
        }
        return DataFlowInfo.INSTANCE.getEMPTY();
    }

    public String toString() {
        return (getCompleteTypeInfo().isEmpty() && getCompleteNullabilityInfo().isEmpty()) ? "EMPTY" : "Non-trivial DataFlowInfo";
    }

    /* JADX INFO: compiled from: DataFlowInfoImpl.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0012H\u0002JT\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00102 \u0010\u0011\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\n0\u00130\u00140\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u0002J\u0014\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0012H\u0002R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\b\u001a$\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfoImpl$Companion;", "", "()V", "EMPTY_NULLABILITY_INFO", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "EMPTY_TYPE_INFO", "Ljavaslang/collection/Set;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ImmutableMultimap;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "parent", "updatedNullabilityInfo", "", "updatedTypeInfo", "Lorg/jetbrains/kotlin/com/google/common/collect/SetMultimap;", "", "Ljavaslang/Tuple2;", "valueToClearPreviousTypeInfo", "newTypeInfoBuilder", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final SetMultimap<DataFlowValue, KotlinType> newTypeInfoBuilder() {
            LinkedHashMultimap linkedHashMultimapCreate = LinkedHashMultimap.create();
            Intrinsics.checkNotNullExpressionValue(linkedHashMultimapCreate, "create(...)");
            return linkedHashMultimapCreate;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final DataFlowInfo create(DataFlowInfo parent, java.util.Map<DataFlowValue, ? extends Nullability> updatedNullabilityInfo, SetMultimap<DataFlowValue, KotlinType> updatedTypeInfo) {
            java.util.Set<java.util.Map.Entry<DataFlowValue, Collection<KotlinType>>> setEntrySet = updatedTypeInfo.asMap().entrySet();
            ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
            java.util.Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                arrayList.add(new Tuple2(entry.getKey(), entry.getValue()));
            }
            return create$default(this, parent, updatedNullabilityInfo, arrayList, null, 8, null);
        }

        static /* synthetic */ DataFlowInfo create$default(Companion companion, DataFlowInfo dataFlowInfo, java.util.Map map, Iterable iterable, DataFlowValue dataFlowValue, int i, Object obj) {
            if ((i & 8) != 0) {
                dataFlowValue = null;
            }
            return companion.create(dataFlowInfo, map, iterable, dataFlowValue);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final DataFlowInfo create(DataFlowInfo parent, java.util.Map<DataFlowValue, ? extends Nullability> updatedNullabilityInfo, Iterable<? extends Tuple2<DataFlowValue, ? extends Iterable<? extends KotlinType>>> updatedTypeInfo, DataFlowValue valueToClearPreviousTypeInfo) {
            Map<DataFlowValue, Nullability> mapPut;
            Map<DataFlowValue, Set<KotlinType>> mapPut2;
            if (updatedNullabilityInfo.isEmpty() && kotlin.collections.CollectionsKt.none(updatedTypeInfo) && valueToClearPreviousTypeInfo == null) {
                return parent == null ? DataFlowInfo.INSTANCE.getEMPTY() : parent;
            }
            java.util.Set<java.util.Map.Entry<DataFlowValue, ? extends Nullability>> setEntrySet = updatedNullabilityInfo.entrySet();
            if (parent == null || (mapPut = parent.getCompleteNullabilityInfo()) == null) {
                mapPut = DataFlowInfoImpl.EMPTY_NULLABILITY_INFO;
            }
            java.util.Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                DataFlowValue dataFlowValue = (DataFlowValue) entry.getKey();
                Nullability nullability = (Nullability) entry.getValue();
                if (dataFlowValue.getImmanentNullability() != nullability) {
                    mapPut = mapPut.put(dataFlowValue, nullability);
                    Intrinsics.checkNotNullExpressionValue(mapPut, "put(...)");
                } else {
                    mapPut = mapPut.remove(dataFlowValue);
                    Intrinsics.checkNotNullExpressionValue(mapPut, "remove(...)");
                }
            }
            if (parent == null || (mapPut2 = parent.getCompleteTypeInfo()) == null) {
                mapPut2 = DataFlowInfoImpl.EMPTY_TYPE_INFO;
            }
            if (valueToClearPreviousTypeInfo != null) {
                mapPut2 = mapPut2.remove(valueToClearPreviousTypeInfo);
                Intrinsics.checkNotNullExpressionValue(mapPut2, "remove(...)");
            }
            for (Tuple2<DataFlowValue, ? extends Iterable<? extends KotlinType>> tuple2 : updatedTypeInfo) {
                DataFlowValue dataFlowValue2 = (DataFlowValue) JavaslangAdaptersKt.component1(tuple2);
                for (KotlinType kotlinType : (Iterable) JavaslangAdaptersKt.component2(tuple2)) {
                    if (!Intrinsics.areEqual(dataFlowValue2.getType(), kotlinType) && !TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoImpl$Companion$create$3
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(UnwrappedType it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return Boolean.valueOf(it2.getConstructor() instanceof NewCapturedTypeConstructor);
                        }
                    })) {
                        mapPut2 = DataFlowInfoImplKt.put(mapPut2, dataFlowValue2, kotlinType);
                    }
                }
            }
            if (mapPut.isEmpty() && mapPut2.isEmpty()) {
                return DataFlowInfo.INSTANCE.getEMPTY();
            }
            return (mapPut == (parent != null ? parent.getCompleteNullabilityInfo() : null) && mapPut2 == parent.getCompleteTypeInfo()) ? parent : new DataFlowInfoImpl(mapPut, mapPut2, null);
        }
    }

    static {
        javaslang.collection.HashMap hashMapEmpty = javaslang.collection.HashMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "empty(...)");
        EMPTY_NULLABILITY_INFO = hashMapEmpty;
        javaslang.collection.HashMap hashMapEmpty2 = javaslang.collection.HashMap.empty();
        Intrinsics.checkNotNullExpressionValue(hashMapEmpty2, "empty(...)");
        EMPTY_TYPE_INFO = hashMapEmpty2;
    }
}
