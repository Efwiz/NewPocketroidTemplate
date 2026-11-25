package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0007H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeConstructorSubstitution;", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "()V", "get", "Lorg/jetbrains/kotlin/types/TypeProjection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeConstructorSubstitution extends TypeSubstitution {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final TypeSubstitution create(KotlinType kotlinType) {
        return INSTANCE.create(kotlinType);
    }

    @JvmStatic
    public static final TypeSubstitution create(TypeConstructor typeConstructor, List<? extends TypeProjection> list) {
        return INSTANCE.create(typeConstructor, list);
    }

    @JvmStatic
    public static final TypeConstructorSubstitution createByConstructorsMap(Map<TypeConstructor, ? extends TypeProjection> map) {
        return INSTANCE.createByConstructorsMap(map);
    }

    @JvmStatic
    public static final TypeConstructorSubstitution createByConstructorsMap(Map<TypeConstructor, ? extends TypeProjection> map, boolean z) {
        return INSTANCE.createByConstructorsMap(map, z);
    }

    @JvmStatic
    public static final TypeConstructorSubstitution createByParametersMap(Map<TypeParameterDescriptor, ? extends TypeProjection> map) {
        return INSTANCE.createByParametersMap(map);
    }

    public abstract TypeProjection get(TypeConstructor key);

    @Override // org.jetbrains.kotlin.types.TypeSubstitution
    /* JADX INFO: renamed from: get */
    public TypeProjection mo7475get(KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return get(key.getConstructor());
    }

    /* JADX INFO: compiled from: TypeSubstitution.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0007J&\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000b0\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007J\u001c\u0010\u0012\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u000b0\u000fH\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeConstructorSubstitution$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "createByConstructorsMap", "Lorg/jetbrains/kotlin/types/TypeConstructorSubstitution;", "map", "", "approximateCapturedTypes", "", "createByParametersMap", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final TypeConstructorSubstitution createByConstructorsMap(Map<TypeConstructor, ? extends TypeProjection> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            return createByConstructorsMap$default(this, map, false, 2, null);
        }

        private Companion() {
        }

        public static /* synthetic */ TypeConstructorSubstitution createByConstructorsMap$default(Companion companion, Map map, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.createByConstructorsMap(map, z);
        }

        @JvmStatic
        public final TypeConstructorSubstitution createByConstructorsMap(final Map<TypeConstructor, ? extends TypeProjection> map, final boolean approximateCapturedTypes) {
            Intrinsics.checkNotNullParameter(map, "map");
            return new TypeConstructorSubstitution() { // from class: org.jetbrains.kotlin.types.TypeConstructorSubstitution$Companion$createByConstructorsMap$1
                @Override // org.jetbrains.kotlin.types.TypeConstructorSubstitution
                public TypeProjection get(TypeConstructor key) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return map.get(key);
                }

                @Override // org.jetbrains.kotlin.types.TypeSubstitution
                public boolean isEmpty() {
                    return map.isEmpty();
                }

                @Override // org.jetbrains.kotlin.types.TypeSubstitution
                /* JADX INFO: renamed from: approximateCapturedTypes, reason: from getter */
                public boolean get$approximateCapturedTypes() {
                    return approximateCapturedTypes;
                }
            };
        }

        @JvmStatic
        public final TypeConstructorSubstitution createByParametersMap(final Map<TypeParameterDescriptor, ? extends TypeProjection> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            return new TypeConstructorSubstitution() { // from class: org.jetbrains.kotlin.types.TypeConstructorSubstitution$Companion$createByParametersMap$1
                @Override // org.jetbrains.kotlin.types.TypeConstructorSubstitution
                public TypeProjection get(TypeConstructor key) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return map.get(key.getThis$0());
                }

                @Override // org.jetbrains.kotlin.types.TypeSubstitution
                public boolean isEmpty() {
                    return map.isEmpty();
                }
            };
        }

        @JvmStatic
        public final TypeSubstitution create(KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return create(kotlinType.getConstructor(), kotlinType.getArguments());
        }

        @JvmStatic
        public final TypeSubstitution create(TypeConstructor typeConstructor, List<? extends TypeProjection> arguments) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.lastOrNull((List) parameters);
            if (typeParameterDescriptor != null && typeParameterDescriptor.isCapturedFromOuterDeclaration()) {
                List<TypeParameterDescriptor> parameters2 = typeConstructor.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                List<TypeParameterDescriptor> list = parameters2;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TypeParameterDescriptor) it.next()).getTypeConstructor());
                }
                return createByConstructorsMap$default(this, MapsKt.toMap(CollectionsKt.zip(arrayList, arguments)), false, 2, null);
            }
            return new IndexedParametersSubstitution(parameters, arguments);
        }
    }
}
