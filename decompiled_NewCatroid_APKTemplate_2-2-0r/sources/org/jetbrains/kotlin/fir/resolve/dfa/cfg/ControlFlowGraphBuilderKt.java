package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirControlFlowGraphOwner;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;

/* JADX INFO: compiled from: ControlFlowGraphBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0006\u001a\u00020\u0001*\u0004\u0018\u00010\u0007\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003¨\u0006\b"}, d2 = {"isUsedInControlFlowGraphBuilderForClass", "", "Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;", "(Lorg/jetbrains/kotlin/fir/declarations/FirControlFlowGraphOwner;)Z", "memberShouldHaveGraph", "getMemberShouldHaveGraph", "isLocalClassOrAnonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ControlFlowGraphBuilderKt {
    public static final boolean isLocalClassOrAnonymousObject(FirDeclaration firDeclaration) {
        FirRegularClass firRegularClass = firDeclaration instanceof FirRegularClass ? (FirRegularClass) firDeclaration : null;
        return (firRegularClass != null && firRegularClass.getSymbol().getClassId().isLocal()) || (firDeclaration instanceof FirAnonymousObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getMemberShouldHaveGraph(FirControlFlowGraphOwner firControlFlowGraphOwner) {
        if (firControlFlowGraphOwner instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) firControlFlowGraphOwner;
            if (firProperty.getInitializer() == null && firProperty.getDelegate() == null && !DeclarationAttributesKt.getHasExplicitBackingField(firProperty)) {
                return false;
            }
        } else if ((firControlFlowGraphOwner instanceof FirField) && ((FirField) firControlFlowGraphOwner).getInitializer() == null) {
            return false;
        }
        return true;
    }

    public static final boolean isUsedInControlFlowGraphBuilderForClass(FirControlFlowGraphOwner firControlFlowGraphOwner) {
        Intrinsics.checkNotNullParameter(firControlFlowGraphOwner, "<this>");
        if (firControlFlowGraphOwner instanceof FirProperty ? true : firControlFlowGraphOwner instanceof FirField) {
            return getMemberShouldHaveGraph(firControlFlowGraphOwner);
        }
        if (firControlFlowGraphOwner instanceof FirConstructor ? true : firControlFlowGraphOwner instanceof FirAnonymousInitializer) {
            return true;
        }
        return !(firControlFlowGraphOwner instanceof FirFunction ? true : firControlFlowGraphOwner instanceof FirClass);
    }
}
