package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClasspathChangesComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ImpactedSymbolsComputer$computeImpactedSymbols$1$impactedClasses$1 extends FunctionReference implements Function1<ClassId, Set<? extends ClassId>> {
    ImpactedSymbolsComputer$computeImpactedSymbols$1$impactedClasses$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getImpactedClasses(Lorg/jetbrains/kotlin/name/ClassId;)Ljava/util/Set;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getImpactedClasses";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ImpactedSymbolsResolver.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Set<ClassId> invoke(ClassId p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((ImpactedSymbolsResolver) this.receiver).getImpactedClasses(p0);
    }
}
