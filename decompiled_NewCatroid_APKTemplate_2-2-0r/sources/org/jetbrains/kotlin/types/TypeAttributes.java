package org.jetbrains.kotlin.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.util.ArrayMap;
import org.jetbrains.kotlin.util.AttributeArrayOwner;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: TypeAttributes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0003:\u0001\u0019B\u0013\b\u0012\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002¢\u0006\u0002\u0010\u0005B\u0019\b\u0002\u0012\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000J\u0015\u0010\u000f\u001a\u00020\u00102\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0086\u0002J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000J@\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00002-\u0010\u0013\u001a)\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0002\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00020\u0014¢\u0006\u0002\b\u0015H\u0082\bJ\u0015\u0010\u0016\u001a\u00020\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0086\u0002J\u0012\u0010\u0017\u001a\u00020\u00002\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0002J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0000R(\u0010\t\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\n8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAttributes;", "Lorg/jetbrains/kotlin/util/AttributeArrayOwner;", "Lorg/jetbrains/kotlin/types/TypeAttribute;", "", "attribute", "(Lorg/jetbrains/kotlin/types/TypeAttribute;)V", "attributes", "", "(Ljava/util/List;)V", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", ProductAction.ACTION_ADD, "other", "contains", "", "intersect", "perform", "op", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "plus", ProductAction.ACTION_REMOVE, SchemaSymbols.ATTVAL_UNION, "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeAttributes extends AttributeArrayOwner<TypeAttribute<?>, TypeAttribute<?>> implements Iterable<TypeAttribute<?>>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TypeAttributes Empty = new TypeAttributes((List<? extends TypeAttribute<?>>) CollectionsKt.emptyList());

    public /* synthetic */ TypeAttributes(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this((List<? extends TypeAttribute<?>>) list);
    }

    private TypeAttributes(List<? extends TypeAttribute<?>> list) {
        for (TypeAttribute<?> typeAttribute : list) {
            registerComponent((KClass) typeAttribute.getKey(), typeAttribute);
        }
    }

    /* JADX INFO: compiled from: TypeAttributes.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J)\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H\n0\t\"\u0010\b\u0000\u0010\n\u0018\u0001*\b\u0012\u0004\u0012\u0002H\n0\u0002H\u0086\bJ\u0018\u0010\u000b\u001a\u00020\u00052\u0010\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\rJ4\u0010\u000e\u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000f0\u00102\u0006\u0010\u0012\u001a\u00020\u00112\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000f0\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAttributes$Companion;", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "Lorg/jetbrains/kotlin/types/TypeAttribute;", "()V", "Empty", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getEmpty", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "attributeAccessor", "Lkotlin/properties/ReadOnlyProperty;", ExifInterface.GPS_DIRECTION_TRUE, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "attributes", "", "customComputeIfAbsent", "", "Ljava/util/concurrent/ConcurrentHashMap;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "compute", "Lkotlin/Function1;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends TypeRegistry<TypeAttribute<?>, TypeAttribute<?>> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final /* synthetic */ <T extends TypeAttribute<? extends T>> ReadOnlyProperty<TypeAttributes, T> attributeAccessor() {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            ReadOnlyProperty<TypeAttributes, T> readOnlyPropertyGenerateNullableAccessor = generateNullableAccessor(Reflection.getOrCreateKotlinClass(TypeAttribute.class));
            Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.types.TypeAttributes, T of org.jetbrains.kotlin.types.TypeAttributes.Companion.attributeAccessor?>");
            return readOnlyPropertyGenerateNullableAccessor;
        }

        @Override // org.jetbrains.kotlin.util.TypeRegistry
        public int customComputeIfAbsent(ConcurrentHashMap<String, Integer> concurrentHashMap, String key, Function1<? super String, Integer> compute) {
            int iIntValue;
            Intrinsics.checkNotNullParameter(concurrentHashMap, "<this>");
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(compute, "compute");
            Integer num = concurrentHashMap.get(key);
            if (num != null) {
                return num.intValue();
            }
            synchronized (concurrentHashMap) {
                Integer num2 = concurrentHashMap.get(key);
                if (num2 == null) {
                    Integer numInvoke = compute.invoke(key);
                    concurrentHashMap.putIfAbsent(key, Integer.valueOf(numInvoke.intValue()));
                    num2 = numInvoke;
                }
                Intrinsics.checkNotNull(num2);
                iIntValue = num2.intValue();
            }
            return iIntValue;
        }

        public final TypeAttributes getEmpty() {
            return TypeAttributes.Empty;
        }

        public final TypeAttributes create(List<? extends TypeAttribute<?>> attributes) {
            Intrinsics.checkNotNullParameter(attributes, "attributes");
            if (attributes.isEmpty()) {
                return getEmpty();
            }
            return new TypeAttributes(attributes, null);
        }
    }

    private TypeAttributes(TypeAttribute<?> typeAttribute) {
        this((List<? extends TypeAttribute<?>>) CollectionsKt.listOf(typeAttribute));
    }

    public final boolean contains(TypeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        return getArrayMap().get(INSTANCE.getId((KClass) attribute.getKey())) != null;
    }

    public final TypeAttributes plus(TypeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        if (contains(attribute)) {
            return this;
        }
        if (isEmpty()) {
            return new TypeAttributes(attribute);
        }
        return INSTANCE.create(CollectionsKt.plus((Collection<? extends TypeAttribute<?>>) CollectionsKt.toList(this), attribute));
    }

    public final TypeAttributes remove(TypeAttribute<?> attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        if (isEmpty()) {
            return this;
        }
        ArrayMap<TypeAttribute<?>> arrayMap = getArrayMap();
        ArrayList arrayList = new ArrayList();
        for (TypeAttribute<?> typeAttribute : arrayMap) {
            if (!Intrinsics.areEqual(typeAttribute, attribute)) {
                arrayList.add(typeAttribute);
            }
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.size() == getArrayMap().getSize() ? this : INSTANCE.create(arrayList2);
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected TypeRegistry<TypeAttribute<?>, TypeAttribute<?>> getTypeRegistry() {
        return INSTANCE;
    }

    public final TypeAttributes union(TypeAttributes other) {
        TypeAttribute typeAttributeUnion;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            TypeAttribute<?> typeAttribute = getArrayMap().get(iIntValue);
            TypeAttribute<?> typeAttribute2 = other.getArrayMap().get(iIntValue);
            if (typeAttribute == null) {
                typeAttributeUnion = typeAttribute2 != null ? typeAttribute2.union(typeAttribute) : null;
            } else {
                typeAttributeUnion = typeAttribute.union(typeAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, typeAttributeUnion);
        }
        return INSTANCE.create(arrayList);
    }

    public final TypeAttributes intersect(TypeAttributes other) {
        TypeAttribute typeAttributeIntersect;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            TypeAttribute<?> typeAttribute = getArrayMap().get(iIntValue);
            TypeAttribute<?> typeAttribute2 = other.getArrayMap().get(iIntValue);
            if (typeAttribute == null) {
                typeAttributeIntersect = typeAttribute2 != null ? typeAttribute2.intersect(typeAttribute) : null;
            } else {
                typeAttributeIntersect = typeAttribute.intersect(typeAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, typeAttributeIntersect);
        }
        return INSTANCE.create(arrayList);
    }

    public final TypeAttributes add(TypeAttributes other) {
        TypeAttribute typeAttributeAdd;
        Intrinsics.checkNotNullParameter(other, "other");
        if (isEmpty() && other.isEmpty()) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = INSTANCE.getIndices().iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            TypeAttribute<?> typeAttribute = getArrayMap().get(iIntValue);
            TypeAttribute<?> typeAttribute2 = other.getArrayMap().get(iIntValue);
            if (typeAttribute == null) {
                typeAttributeAdd = typeAttribute2 != null ? typeAttribute2.add(typeAttribute) : null;
            } else {
                typeAttributeAdd = typeAttribute.add(typeAttribute2);
            }
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList, typeAttributeAdd);
        }
        return INSTANCE.create(arrayList);
    }
}
