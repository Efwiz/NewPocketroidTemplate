package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KClass;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.util.ConeTypeRegistry;
import org.jetbrains.kotlin.util.ArrayMap;
import org.jetbrains.kotlin.util.AttributeArrayOwner;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: ConeAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0003:\u0001\u001bB\u0013\b\u0012\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002¢\u0006\u0002\u0010\u0005B\u0019\b\u0002\u0012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000J\u001d\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00020\u0012H\u0086\u0002J\u0015\u0010\u000f\u001a\u00020\u00102\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0086\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000J@\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00002-\u0010\u0015\u001a)\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00020\u0016¢\u0006\u0002\b\u0017H\u0082\bJ\u0015\u0010\u0018\u001a\u00020\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0086\u0002J\u0012\u0010\u0019\u001a\u00020\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000R(\u0010\t\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\n8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "Lorg/jetbrains/kotlin/util/AttributeArrayOwner;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "", "attribute", "(Lorg/jetbrains/kotlin/fir/types/ConeAttribute;)V", "attributes", "", "(Ljava/util/List;)V", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", ProductAction.ACTION_ADD, "other", "contains", "", "attributeKey", "Lkotlin/reflect/KClass;", "intersect", "perform", "op", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "plus", ProductAction.ACTION_REMOVE, SchemaSymbols.ATTVAL_UNION, "Companion", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAttributes extends AttributeArrayOwner<ConeAttribute<?>, ConeAttribute<?>> implements Iterable<ConeAttribute<?>>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final ConeAttributes Empty;
    private static final ConeAttributes WithExtensionFunctionType;
    private static final Map<ConeAttribute<?>, ConeAttributes> predefinedAttributes;

    public /* synthetic */ ConeAttributes(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this((List<? extends ConeAttribute<?>>) list);
    }

    public /* synthetic */ ConeAttributes(ConeAttribute coneAttribute, DefaultConstructorMarker defaultConstructorMarker) {
        this((ConeAttribute<?>) coneAttribute);
    }

    private ConeAttributes(List<? extends ConeAttribute<?>> list) {
        for (ConeAttribute<?> coneAttribute : list) {
            registerComponent((KClass) coneAttribute.getKey(), coneAttribute);
        }
    }

    /* JADX INFO: compiled from: ConeAttributes.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J)\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\r\"\u0010\b\u0000\u0010\u000e\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000e0\u0002H\u0086\bJ\u0018\u0010\u000f\u001a\u00020\u00052\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0011J \u0010\u0012\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00050\u0013*\u0006\u0012\u0002\b\u00030\u0002H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u001e\u0010\n\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u00050\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeAttributes$Companion;", "Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "()V", "Empty", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "getEmpty", "()Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "WithExtensionFunctionType", "getWithExtensionFunctionType", "predefinedAttributes", "", "attributeAccessor", "Lkotlin/properties/ReadOnlyProperty;", ExifInterface.GPS_DIRECTION_TRUE, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "attributes", "", "predefined", "Lkotlin/Pair;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ConeTypeRegistry<ConeAttribute<?>, ConeAttribute<?>> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final /* synthetic */ <T extends ConeAttribute<? extends T>> ReadOnlyProperty<ConeAttributes, T> attributeAccessor() {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            ReadOnlyProperty<ConeAttributes, T> readOnlyPropertyGenerateNullableAccessor = generateNullableAccessor(Reflection.getOrCreateKotlinClass(ConeAttribute.class));
            Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.fir.types.ConeAttributes, T of org.jetbrains.kotlin.fir.types.ConeAttributes.Companion.attributeAccessor?>");
            return readOnlyPropertyGenerateNullableAccessor;
        }

        public final ConeAttributes getEmpty() {
            return ConeAttributes.Empty;
        }

        public final ConeAttributes getWithExtensionFunctionType() {
            return ConeAttributes.WithExtensionFunctionType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Pair<ConeAttribute<?>, ConeAttributes> predefined(ConeAttribute<?> coneAttribute) {
            return TuplesKt.to(coneAttribute, new ConeAttributes(coneAttribute, (DefaultConstructorMarker) null));
        }

        public final ConeAttributes create(List<? extends ConeAttribute<?>> attributes) {
            Intrinsics.checkNotNullParameter(attributes, "attributes");
            if (attributes.isEmpty()) {
                return getEmpty();
            }
            return new ConeAttributes(attributes, (DefaultConstructorMarker) null);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        Empty = new ConeAttributes((List<? extends ConeAttribute<?>>) CollectionsKt.emptyList());
        WithExtensionFunctionType = new ConeAttributes((List<? extends ConeAttribute<?>>) CollectionsKt.listOf(CompilerConeAttributes.ExtensionFunctionType.INSTANCE));
        predefinedAttributes = MapsKt.mapOf(companion.predefined(CompilerConeAttributes.EnhancedNullability.INSTANCE));
    }

    private ConeAttributes(ConeAttribute<?> coneAttribute) {
        this((List<? extends ConeAttribute<?>>) CollectionsKt.listOf(coneAttribute));
    }

    public final boolean contains(ConeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        return contains(attribute.getKey());
    }

    public final boolean contains(KClass<? extends ConeAttribute<?>> attributeKey) {
        Intrinsics.checkNotNullParameter(attributeKey, "attributeKey");
        return getArrayMap().get(INSTANCE.getId(attributeKey)) != null;
    }

    public final ConeAttributes plus(ConeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        if (contains(attribute)) {
            return this;
        }
        if (isEmpty()) {
            ConeAttributes coneAttributes = predefinedAttributes.get(attribute);
            return coneAttributes == null ? new ConeAttributes(attribute) : coneAttributes;
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        CollectionsKt.addAll(listCreateListBuilder, getArrayMap());
        listCreateListBuilder.add(attribute);
        return new ConeAttributes((List<? extends ConeAttribute<?>>) CollectionsKt.build(listCreateListBuilder));
    }

    public final ConeAttributes remove(ConeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        if (isEmpty()) {
            return this;
        }
        ArrayMap<ConeAttribute<?>> arrayMap = getArrayMap();
        ArrayList arrayList = new ArrayList();
        for (ConeAttribute<?> coneAttribute : arrayMap) {
            if (!Intrinsics.areEqual(coneAttribute, attribute)) {
                arrayList.add(coneAttribute);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.size() == getArrayMap().getSize() ? this : INSTANCE.create(arrayList2);
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected TypeRegistry<ConeAttribute<?>, ConeAttribute<?>> getTypeRegistry() {
        return INSTANCE;
    }

    public final ConeAttributes union(ConeAttributes other) {
        ConeAttribute coneAttributeUnion;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            ConeAttribute<?> coneAttribute = getArrayMap().get(iIntValue);
            ConeAttribute<?> coneAttribute2 = other.getArrayMap().get(iIntValue);
            if (coneAttribute == null) {
                coneAttributeUnion = coneAttribute2 != null ? coneAttribute2.union(coneAttribute) : null;
            } else {
                coneAttributeUnion = coneAttribute.union(coneAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, coneAttributeUnion);
        }
        return INSTANCE.create(arrayList);
    }

    public final ConeAttributes intersect(ConeAttributes other) {
        ConeAttribute coneAttributeIntersect;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            ConeAttribute<?> coneAttribute = getArrayMap().get(iIntValue);
            ConeAttribute<?> coneAttribute2 = other.getArrayMap().get(iIntValue);
            if (coneAttribute == null) {
                coneAttributeIntersect = coneAttribute2 != null ? coneAttribute2.intersect(coneAttribute) : null;
            } else {
                coneAttributeIntersect = coneAttribute.intersect(coneAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, coneAttributeIntersect);
        }
        return INSTANCE.create(arrayList);
    }

    public final ConeAttributes add(ConeAttributes other) {
        ConeAttribute coneAttributeAdd;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            ConeAttribute<?> coneAttribute = getArrayMap().get(iIntValue);
            ConeAttribute<?> coneAttribute2 = other.getArrayMap().get(iIntValue);
            if (coneAttribute == null) {
                coneAttributeAdd = coneAttribute2 != null ? coneAttribute2.add(coneAttribute) : null;
            } else {
                coneAttributeAdd = coneAttribute.add(coneAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, coneAttributeAdd);
        }
        return INSTANCE.create(arrayList);
    }
}
