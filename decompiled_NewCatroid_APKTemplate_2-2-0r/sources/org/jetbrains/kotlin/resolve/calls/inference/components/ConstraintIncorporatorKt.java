package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.ArrayDeque;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ConstraintIncorporator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"getNestedArguments", "", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstraintIncorporatorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<TypeArgumentMarker> getNestedArguments(final TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker kotlinTypeMarker) {
        SmartList smartList = new SmartList();
        final ArrayDeque arrayDeque = new ArrayDeque();
        if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            arrayDeque.push(typeSystemInferenceExtensionContext.createTypeArgument(typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarker), TypeVariance.INV));
            arrayDeque.push(typeSystemInferenceExtensionContext.createTypeArgument(typeSystemInferenceExtensionContext.upperBound(flexibleTypeMarker), TypeVariance.INV));
        } else {
            arrayDeque.push(typeSystemInferenceExtensionContext.createTypeArgument(kotlinTypeMarker, TypeVariance.INV));
        }
        arrayDeque.push(typeSystemInferenceExtensionContext.createTypeArgument(kotlinTypeMarker, TypeVariance.INV));
        Function1<KotlinTypeMarker, Unit> function1 = new Function1<KotlinTypeMarker, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporatorKt$getNestedArguments$addArgumentsToStack$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KotlinTypeMarker kotlinTypeMarker2) {
                invoke2(kotlinTypeMarker2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(KotlinTypeMarker projectedType) {
                Intrinsics.checkNotNullParameter(projectedType, "projectedType");
                int iArgumentsCount = typeSystemInferenceExtensionContext.argumentsCount(projectedType);
                for (int i = 0; i < iArgumentsCount; i++) {
                    arrayDeque.add(typeSystemInferenceExtensionContext.getArgument(projectedType, i));
                }
            }
        };
        while (!arrayDeque.isEmpty()) {
            TypeArgumentMarker typeArgumentMarker = (TypeArgumentMarker) arrayDeque.pop();
            Intrinsics.checkNotNull(typeArgumentMarker);
            if (!typeSystemInferenceExtensionContext.isStarProjection(typeArgumentMarker)) {
                smartList.add(typeArgumentMarker);
                KotlinTypeMarker type = typeSystemInferenceExtensionContext.getType(typeArgumentMarker);
                if (type instanceof FlexibleTypeMarker) {
                    FlexibleTypeMarker flexibleTypeMarker2 = (FlexibleTypeMarker) type;
                    function1.invoke(typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarker2));
                    function1.invoke(typeSystemInferenceExtensionContext.upperBound(flexibleTypeMarker2));
                } else {
                    function1.invoke(type);
                }
            }
        }
        return smartList;
    }
}
