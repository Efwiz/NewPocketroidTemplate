package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ClasspathSnapshotShrinker$findTransitivelyReferencedClasses$transitivelyReferencedClassIds$1 extends FunctionReference implements Function1<ClassId, Set<? extends ClassId>> {
    ClasspathSnapshotShrinker$findTransitivelyReferencedClasses$transitivelyReferencedClassIds$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getImpactingClasses(Lorg/jetbrains/kotlin/name/ClassId;)Ljava/util/Set;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getImpactingClasses";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ImpactingClassesResolver.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Set<ClassId> invoke(ClassId p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((ImpactingClassesResolver) this.receiver).getImpactingClasses(p0);
    }
}
