package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: AbstractFirSpecificAnnotationResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsHelper;", "", "()V", "REQUIRED_ANNOTATIONS", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getREQUIRED_ANNOTATIONS", "()Ljava/util/Set;", "REQUIRED_ANNOTATIONS_WITH_ARGUMENTS", "getREQUIRED_ANNOTATIONS_WITH_ARGUMENTS$resolve", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilerRequiredAnnotationsHelper {
    public static final CompilerRequiredAnnotationsHelper INSTANCE = new CompilerRequiredAnnotationsHelper();
    private static final Set<ClassId> REQUIRED_ANNOTATIONS;
    private static final Set<ClassId> REQUIRED_ANNOTATIONS_WITH_ARGUMENTS;

    private CompilerRequiredAnnotationsHelper() {
    }

    public final Set<ClassId> getREQUIRED_ANNOTATIONS_WITH_ARGUMENTS$resolve() {
        return REQUIRED_ANNOTATIONS_WITH_ARGUMENTS;
    }

    static {
        Set<ClassId> of = SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.Annotations.INSTANCE.getDeprecated(), StandardClassIds.Annotations.INSTANCE.getTarget(), StandardClassIds.Annotations.Java.INSTANCE.getTarget()});
        REQUIRED_ANNOTATIONS_WITH_ARGUMENTS = of;
        REQUIRED_ANNOTATIONS = SetsKt.plus((Set) of, (Iterable) SetsKt.setOf((Object[]) new ClassId[]{StandardClassIds.Annotations.Java.INSTANCE.getDeprecated(), StandardClassIds.Annotations.INSTANCE.getDeprecatedSinceKotlin(), StandardClassIds.Annotations.INSTANCE.getSinceKotlin(), StandardClassIds.Annotations.INSTANCE.getWasExperimental(), StandardClassIds.Annotations.INSTANCE.getJvmRecord()}));
    }

    public final Set<ClassId> getREQUIRED_ANNOTATIONS() {
        return REQUIRED_ANNOTATIONS;
    }
}
