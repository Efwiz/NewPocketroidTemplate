package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;

/* JADX INFO: compiled from: LocalClassesNavigation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"collectLocalClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LocalClassesNavigationKt {
    public static final LocalClassesNavigationInfo collectLocalClassesNavigationInfo(FirClassLikeDeclaration firClassLikeDeclaration) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        NavigationInfoVisitor navigationInfoVisitor = new NavigationInfoVisitor();
        firClassLikeDeclaration.accept(navigationInfoVisitor, null);
        return new LocalClassesNavigationInfo(navigationInfoVisitor.getParentForClass(), navigationInfoVisitor.getResultingMap());
    }
}
