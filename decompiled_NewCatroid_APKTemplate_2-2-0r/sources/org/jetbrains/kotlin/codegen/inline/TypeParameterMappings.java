package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ReifiedTypeInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003BE\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0006\b\u0001\u0012\u00020\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\u0010\u000fJ1\u0010\u0015\u001a\u00020\u00162\u001e\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0013\u0012\u0004\u0012\u00020\u00160\fH\u0080\bø\u0001\u0000¢\u0006\u0002\b\u0018J\u0019\u0010\u0019\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00132\u0006\u0010\u001a\u001a\u00020\u0012H\u0086\u0002J\u0006\u0010\u001b\u001a\u00020\nR6\u0010\u0010\u001a*\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00130\u0011j\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0013`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "KT", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "", "typeSystem", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "allReified", "", "mapType", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/codegen/signature/BothSignatureWriter;", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;Ljava/util/Map;ZLkotlin/jvm/functions/Function2;)V", "mappingsByName", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMapping;", "Lkotlin/collections/HashMap;", "forEach", "", "block", "forEach$backend", "get", "name", "hasReifiedParameters", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterMappings<KT extends KotlinTypeMarker> {
    private final HashMap<String, TypeParameterMapping<KT>> mappingsByName;

    public TypeParameterMappings(TypeSystemCommonBackendContext typeSystem, Map<? extends TypeParameterMarker, ? extends KT> typeArguments, boolean z, Function2<? super KT, ? super BothSignatureWriter, Type> mapType) {
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(mapType, "mapType");
        this.mappingsByName = new HashMap<>();
        for (Map.Entry<? extends TypeParameterMarker, ? extends KT> entry : typeArguments.entrySet()) {
            TypeParameterMarker key = entry.getKey();
            KT value = entry.getValue();
            String identifier = typeSystem.getName(key).getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.TYPE);
            HashMap<String, TypeParameterMapping<KT>> map = this.mappingsByName;
            Type typeInvoke = mapType.invoke(value, bothSignatureWriter);
            String string = bothSignatureWriter.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            boolean z2 = z || typeSystem.isReified(key);
            Pair<TypeParameterMarker, ReificationArgument> pairExtractReificationArgument = CodegenUtilKt.extractReificationArgument(typeSystem, value);
            map.put(identifier, new TypeParameterMapping<>(value, typeInvoke, string, z2, pairExtractReificationArgument != null ? pairExtractReificationArgument.getSecond() : null, CodegenUtilKt.extractUsedReifiedParameters(typeSystem, value)));
        }
    }

    public final TypeParameterMapping<KT> get(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.mappingsByName.get(name2);
    }

    public final boolean hasReifiedParameters() {
        Collection<TypeParameterMapping<KT>> collectionValues = this.mappingsByName.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        Collection<TypeParameterMapping<KT>> collection = collectionValues;
        if (collection.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (((TypeParameterMapping) it.next()).getIsReified()) {
                return true;
            }
        }
        return false;
    }

    public final void forEach$backend(Function2<? super String, ? super TypeParameterMapping<KT>, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Set<Map.Entry> setEntrySet = this.mappingsByName.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        for (Map.Entry entry : setEntrySet) {
            Intrinsics.checkNotNull(entry);
            String str = (String) entry.getKey();
            TypeParameterMapping typeParameterMapping = (TypeParameterMapping) entry.getValue();
            Intrinsics.checkNotNull(str);
            Intrinsics.checkNotNull(typeParameterMapping);
            block.invoke(str, typeParameterMapping);
        }
    }
}
