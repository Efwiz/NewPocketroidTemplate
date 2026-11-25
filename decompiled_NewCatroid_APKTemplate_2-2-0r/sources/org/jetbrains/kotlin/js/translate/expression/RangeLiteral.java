package org.jetbrains.kotlin.js.translate.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: LoopTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\n\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/RangeLiteral;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/js/translate/expression/RangeType;", "first", "Lorg/jetbrains/kotlin/psi/KtExpression;", "second", "step", "(Lorg/jetbrains/kotlin/js/translate/expression/RangeType;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getFirst", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getSecond", "getStep", "setStep", "(Lorg/jetbrains/kotlin/psi/KtExpression;)V", "getType", "()Lorg/jetbrains/kotlin/js/translate/expression/RangeType;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class RangeLiteral {
    private final KtExpression first;
    private final KtExpression second;
    private KtExpression step;
    private final RangeType type;

    public RangeLiteral(RangeType type, KtExpression first, KtExpression second, KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        this.type = type;
        this.first = first;
        this.second = second;
        this.step = ktExpression;
    }

    public final KtExpression getFirst() {
        return this.first;
    }

    public final KtExpression getSecond() {
        return this.second;
    }

    public final KtExpression getStep() {
        return this.step;
    }

    public final RangeType getType() {
        return this.type;
    }

    public final void setStep(KtExpression ktExpression) {
        this.step = ktExpression;
    }
}
