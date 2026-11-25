package org.jetbrains.kotlin.types.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: MarkerExtensions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004\u001a \u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a \u0010\u000e\u001a\u00020\t*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u001a\u0012\u0010\u0012\u001a\u00020\r*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001a\u0010\u0013\u001a\u00020\n*\u00020\u00142\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\n¨\u0006\u0016"}, d2 = {"captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "c", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "defaultType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "dependsOnTypeConstructor", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "typeConstructors", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "dependsOnTypeParameters", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "freshTypeConstructor", "safeSubstitute", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", ModuleXmlParser.TYPE, "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MarkerExtensionsKt {
    public static final TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker, TypeSystemInferenceExtensionContext c) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        return c.freshTypeConstructor(typeVariableMarker);
    }

    public static final KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, TypeSystemInferenceExtensionContext c, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(type, "type");
        return c.safeSubstitute(typeSubstitutorMarker, type);
    }

    public static final SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker, TypeSystemInferenceExtensionContext c) {
        Intrinsics.checkNotNullParameter(typeVariableMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        return c.defaultType(typeVariableMarker);
    }

    public static final boolean dependsOnTypeConstructor(KotlinTypeMarker kotlinTypeMarker, final TypeSystemInferenceExtensionContext c, final Set<? extends TypeConstructorMarker> typeConstructors) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeConstructors, "typeConstructors");
        return c.contains(kotlinTypeMarker, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.types.model.MarkerExtensionsKt$dependsOnTypeConstructor$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(typeConstructors.contains(c.typeConstructor(it)));
            }
        });
    }

    public static final boolean dependsOnTypeParameters(KotlinTypeMarker kotlinTypeMarker, TypeSystemInferenceExtensionContext c, Collection<? extends TypeParameterMarker> typeParameters) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(c.getTypeConstructor((TypeParameterMarker) it.next()));
        }
        return dependsOnTypeConstructor(kotlinTypeMarker, c, linkedHashSet);
    }

    public static final CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker, TypeSystemInferenceExtensionContext c) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(c, "c");
        return c.captureStatus(capturedTypeMarker);
    }
}
