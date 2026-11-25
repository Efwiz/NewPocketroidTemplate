package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;

/* JADX INFO: compiled from: Impact.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bp\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&\u0082\u0001\u0003\t\n\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/Impact;", "", "getResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactedSymbolsResolver;", "allClasses", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "getReverseResolver", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ImpactingClassesResolver;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AllImpacts;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ConstantsInCompanionObjectsImpact;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/SupertypesInheritorsImpact;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Impact {
    ImpactedSymbolsResolver getResolver(Iterable<? extends AccessibleClassSnapshot> allClasses);

    ImpactingClassesResolver getReverseResolver(Iterable<? extends AccessibleClassSnapshot> allClasses);
}
