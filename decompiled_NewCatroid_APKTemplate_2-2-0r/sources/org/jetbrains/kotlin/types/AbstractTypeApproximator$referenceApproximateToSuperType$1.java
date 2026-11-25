package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: AbstractTypeApproximator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class AbstractTypeApproximator$referenceApproximateToSuperType$1 extends FunctionReference implements Function3<SimpleTypeMarker, TypeApproximatorConfiguration, Integer, KotlinTypeMarker> {
    AbstractTypeApproximator$referenceApproximateToSuperType$1(Object obj) {
        super(3, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "approximateSimpleToSuperType(Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;I)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "approximateSimpleToSuperType";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AbstractTypeApproximator.class);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ KotlinTypeMarker invoke(SimpleTypeMarker simpleTypeMarker, TypeApproximatorConfiguration typeApproximatorConfiguration, Integer num) {
        return invoke(simpleTypeMarker, typeApproximatorConfiguration, num.intValue());
    }

    public final KotlinTypeMarker invoke(SimpleTypeMarker p0, TypeApproximatorConfiguration p1, int i) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((AbstractTypeApproximator) this.receiver).approximateSimpleToSuperType(p0, p1, i);
    }
}
