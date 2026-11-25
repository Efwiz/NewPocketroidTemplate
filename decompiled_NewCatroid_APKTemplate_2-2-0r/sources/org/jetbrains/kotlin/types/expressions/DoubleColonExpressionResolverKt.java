package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.resolve.PossiblyBareType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0005H\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\b"}, d2 = {"isWithoutValueArguments", "", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "(Lorg/jetbrains/kotlin/psi/KtCallExpression;)Z", "refineBareType", "Lorg/jetbrains/kotlin/resolve/PossiblyBareType;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", ModuleXmlParser.TYPE, "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DoubleColonExpressionResolverKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final PossiblyBareType refineBareType(KotlinTypeRefiner kotlinTypeRefiner, PossiblyBareType possiblyBareType) {
        if (possiblyBareType.isBare()) {
            return possiblyBareType;
        }
        KotlinType actualType = possiblyBareType.getActualType();
        Intrinsics.checkNotNull(actualType);
        PossiblyBareType possiblyBareTypeType = PossiblyBareType.type(kotlinTypeRefiner.refineType((KotlinTypeMarker) actualType));
        Intrinsics.checkNotNullExpressionValue(possiblyBareTypeType, "type(...)");
        return possiblyBareTypeType;
    }

    public static final boolean isWithoutValueArguments(KtCallExpression ktCallExpression) {
        Intrinsics.checkNotNullParameter(ktCallExpression, "<this>");
        return ktCallExpression.getValueArgumentList() == null && ktCallExpression.getLambdaArguments().isEmpty();
    }
}
