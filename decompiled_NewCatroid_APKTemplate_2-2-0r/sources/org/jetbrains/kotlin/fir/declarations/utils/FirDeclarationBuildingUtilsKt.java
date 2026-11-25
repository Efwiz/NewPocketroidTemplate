package org.jetbrains.kotlin.fir.declarations.utils;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;

/* JADX INFO: compiled from: FirDeclarationBuildingUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\t¨\u0006\n"}, d2 = {"addDeclaration", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "addDeclarations", "declarations", "", "addDefaultBoundIfNecessary", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParameterBuilder;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationBuildingUtilsKt {
    public static final void addDefaultBoundIfNecessary(FirTypeParameterBuilder firTypeParameterBuilder) {
        Intrinsics.checkNotNullParameter(firTypeParameterBuilder, "<this>");
        if (firTypeParameterBuilder.getBounds().isEmpty()) {
            firTypeParameterBuilder.getBounds().add(firTypeParameterBuilder.getModuleData().getSession().getBuiltinTypes().getNullableAnyType());
        }
    }

    public static final void addDeclaration(FirRegularClassBuilder firRegularClassBuilder, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        firRegularClassBuilder.getDeclarations().add(declaration);
    }

    public static final void addDeclarations(FirRegularClassBuilder firRegularClassBuilder, Collection<? extends FirDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            addDeclaration(firRegularClassBuilder, (FirDeclaration) it.next());
        }
    }
}
