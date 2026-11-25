package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: TypeRemapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB3\b\u0002\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0000\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004J\u0016\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0004J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004J\u0014\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f2\u0006\u0010\u001a\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0004J\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u00042\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u000fR*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\nj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000e\u001a*\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u000f0\nj\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u000f`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "", "typeMapping", "", "", "parent", "isRootInlineLambda", "", "(Ljava/util/Map;Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;Z)V", "additionalMappings", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "getParent", "()Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "typeParametersMapping", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMapping;", "addAdditionalMappings", "", "oldName", "newName", "addMapping", ModuleXmlParser.TYPE, "newType", "hasNoAdditionalMapping", "map", "mapTypeParameter", "name", "registerTypeParameter", "mapping", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeRemapper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final HashMap<String, String> additionalMappings;
    private final boolean isRootInlineLambda;
    private final TypeRemapper parent;
    private final Map<String, String> typeMapping;
    private final HashMap<String, TypeParameterMapping<?>> typeParametersMapping;

    public /* synthetic */ TypeRemapper(Map map, TypeRemapper typeRemapper, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, typeRemapper, z);
    }

    @JvmStatic
    public static final TypeRemapper createFrom(Map<String, String> map) {
        return INSTANCE.createFrom(map);
    }

    @JvmStatic
    public static final TypeRemapper createFrom(TypeRemapper typeRemapper, Map<String, String> map) {
        return INSTANCE.createFrom(typeRemapper, map);
    }

    @JvmStatic
    public static final TypeRemapper createFrom(TypeRemapper typeRemapper, Map<String, String> map, boolean z) {
        return INSTANCE.createFrom(typeRemapper, map, z);
    }

    @JvmStatic
    public static final TypeRemapper createRoot(TypeParameterMappings<?> typeParameterMappings) {
        return INSTANCE.createRoot(typeParameterMappings);
    }

    private TypeRemapper(Map<String, String> map, TypeRemapper typeRemapper, boolean z) {
        this.typeMapping = map;
        this.parent = typeRemapper;
        this.isRootInlineLambda = z;
        this.additionalMappings = new HashMap<>();
        this.typeParametersMapping = new HashMap<>();
    }

    /* synthetic */ TypeRemapper(Map map, TypeRemapper typeRemapper, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, (i & 2) != 0 ? null : typeRemapper, (i & 4) != 0 ? false : z);
    }

    public final TypeRemapper getParent() {
        return this.parent;
    }

    public final void addMapping(String type, String newType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(newType, "newType");
        this.typeMapping.put(type, newType);
    }

    public final boolean hasNoAdditionalMapping(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.typeMapping.containsKey(type);
    }

    public final String map(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        String str = this.typeMapping.get(type);
        return (str == null && (str = this.additionalMappings.get(type)) == null) ? type : str;
    }

    public final void addAdditionalMappings(String oldName, String newName) {
        Intrinsics.checkNotNullParameter(oldName, "oldName");
        Intrinsics.checkNotNullParameter(newName, "newName");
        this.additionalMappings.put(oldName, newName);
    }

    public final void registerTypeParameter(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.typeParametersMapping.put(name2, null);
    }

    public final void registerTypeParameter(String name2, TypeParameterMapping<?> mapping) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        this.typeParametersMapping.put(name2, mapping);
    }

    public final TypeParameterMapping<?> mapTypeParameter(String name2) {
        TypeRemapper typeRemapper;
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.typeParametersMapping.containsKey(name2)) {
            return this.typeParametersMapping.get(name2);
        }
        if (this.isRootInlineLambda || (typeRemapper = this.parent) == null) {
            return null;
        }
        return typeRemapper.mapTypeParameter(name2);
    }

    /* JADX INFO: compiled from: TypeRemapper.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0007J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J4\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\u0006\u0010\r\u001a\u00020\u00042\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\tH\u0002J\u0014\u0010\u000f\u001a\u00020\u00042\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper$Companion;", "", "()V", "createFrom", "Lorg/jetbrains/kotlin/codegen/inline/TypeRemapper;", "mappings", "", "", "parentRemapper", "", "isRootInlineLambda", "", "createNewAndMerge", "remapper", "additionalTypeMappings", "createRoot", "formalTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final TypeRemapper createFrom(TypeRemapper parentRemapper, Map<String, String> mappings) {
            Intrinsics.checkNotNullParameter(parentRemapper, "parentRemapper");
            Intrinsics.checkNotNullParameter(mappings, "mappings");
            return createFrom$default(this, parentRemapper, mappings, false, 4, null);
        }

        private Companion() {
        }

        @JvmStatic
        public final TypeRemapper createRoot(TypeParameterMappings<?> formalTypeParameters) {
            Intrinsics.checkNotNullParameter(formalTypeParameters, "formalTypeParameters");
            TypeRemapper typeRemapper = new TypeRemapper(new HashMap(), null, false, 6, null);
            Set<Map.Entry> setEntrySet = ((TypeParameterMappings) formalTypeParameters).mappingsByName.entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
            for (Map.Entry entry : setEntrySet) {
                Intrinsics.checkNotNull(entry);
                String str = (String) entry.getKey();
                TypeParameterMapping<?> typeParameterMapping = (TypeParameterMapping) entry.getValue();
                Intrinsics.checkNotNull(str);
                Intrinsics.checkNotNull(typeParameterMapping);
                typeRemapper.registerTypeParameter(str, typeParameterMapping);
            }
            return typeRemapper;
        }

        @JvmStatic
        public final TypeRemapper createFrom(Map<String, String> mappings) {
            Intrinsics.checkNotNullParameter(mappings, "mappings");
            return new TypeRemapper(mappings, null, false, 6, null);
        }

        public static /* synthetic */ TypeRemapper createFrom$default(Companion companion, TypeRemapper typeRemapper, Map map, boolean z, int i, Object obj) {
            if ((i & 4) != 0) {
                z = false;
            }
            return companion.createFrom(typeRemapper, map, z);
        }

        @JvmStatic
        public final TypeRemapper createFrom(TypeRemapper parentRemapper, Map<String, String> mappings, boolean isRootInlineLambda) {
            Intrinsics.checkNotNullParameter(parentRemapper, "parentRemapper");
            Intrinsics.checkNotNullParameter(mappings, "mappings");
            return new TypeRemapper(createNewAndMerge(parentRemapper, mappings), parentRemapper, isRootInlineLambda, null);
        }

        private final Map<String, String> createNewAndMerge(TypeRemapper remapper, Map<String, String> additionalTypeMappings) {
            HashMap map = new HashMap(remapper.typeMapping);
            map.putAll(additionalTypeMappings);
            return map;
        }
    }
}
