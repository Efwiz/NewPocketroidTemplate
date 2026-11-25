package org.jetbrains.kotlin.fir.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbolKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ImplicitIntegerCoercion.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005\"\u0019\u0010\u0002\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0007¨\u0006\b"}, d2 = {"implicitIntegerCoercionAnnotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "isMarkedWithImplicitIntegerCoercion", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Z", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplicitIntegerCoercionKt {
    private static final ClassId implicitIntegerCoercionAnnotationClassId = new ClassId(StandardNames.KOTLIN_INTERNAL_FQ_NAME, Name.identifier("ImplicitIntegerCoercion"));

    public static final boolean isMarkedWithImplicitIntegerCoercion(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        if (!CollectionsKt.contains(((FirCallableDeclaration) firCallableSymbol.getFir()).getModuleData().getCapabilities(), ImplicitIntegerCoercionModuleCapability.INSTANCE)) {
            List<ClassId> resolvedAnnotationClassIds = firCallableSymbol.getResolvedAnnotationClassIds();
            if (!(resolvedAnnotationClassIds instanceof Collection) || !resolvedAnnotationClassIds.isEmpty()) {
                Iterator<T> it = resolvedAnnotationClassIds.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual((ClassId) it.next(), implicitIntegerCoercionAnnotationClassId)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isMarkedWithImplicitIntegerCoercion(FirCallableDeclaration firCallableDeclaration) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        if (!CollectionsKt.contains(firCallableDeclaration.getModuleData().getCapabilities(), ImplicitIntegerCoercionModuleCapability.INSTANCE)) {
            List<ClassId> listResolvedAnnotationClassIds = FirBasedSymbolKt.resolvedAnnotationClassIds(firCallableDeclaration, firCallableDeclaration.getSymbol());
            if (!(listResolvedAnnotationClassIds instanceof Collection) || !listResolvedAnnotationClassIds.isEmpty()) {
                Iterator<T> it = listResolvedAnnotationClassIds.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual((ClassId) it.next(), implicitIntegerCoercionAnnotationClassId)) {
                    }
                }
            }
            return false;
        }
        return true;
    }
}
