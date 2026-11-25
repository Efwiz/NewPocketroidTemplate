package org.jetbrains.kotlin.types;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: alternativeTypeSubstitution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"substituteAlternativesInPublicType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AlternativeTypeSubstitutionKt {
    public static final UnwrappedType substituteAlternativesInPublicType(final KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new NewTypeSubstitutor(type) { // from class: org.jetbrains.kotlin.types.AlternativeTypeSubstitutionKt$substituteAlternativesInPublicType$substitutor$1

            /* JADX INFO: renamed from: isEmpty$delegate, reason: from kotlin metadata */
            private final Lazy isEmpty;

            {
                this.isEmpty = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.types.AlternativeTypeSubstitutionKt$substituteAlternativesInPublicType$substitutor$1$isEmpty$2
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Boolean invoke() {
                        return Boolean.valueOf(!TypeUtilsKt.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.AlternativeTypeSubstitutionKt$substituteAlternativesInPublicType$substitutor$1$isEmpty$2.1
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(UnwrappedType it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return Boolean.valueOf(it.getConstructor() instanceof IntersectionTypeConstructor);
                            }
                        }));
                    }
                });
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
            public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
                KotlinType alternative;
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                if (!(constructor instanceof IntersectionTypeConstructor) || (alternative = ((IntersectionTypeConstructor) constructor).getAlternative()) == null) {
                    return null;
                }
                return safeSubstitute(alternative.unwrap());
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
            public boolean isEmpty() {
                return ((Boolean) this.isEmpty.getValue()).booleanValue();
            }
        }.safeSubstitute(type.unwrap());
    }
}
