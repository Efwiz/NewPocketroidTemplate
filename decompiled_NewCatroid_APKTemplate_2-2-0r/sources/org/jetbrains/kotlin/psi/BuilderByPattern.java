package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ&\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013J\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0015\u001a\u00020\u0013J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0017\u001a\u00020\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0015\u001a\u00020\u0013J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ-\u0010\u001d\u001a\u00028\u00002 \u0010\u001e\u001a\u001c\u0012\u0004\u0012\u00020\u0013\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020 \u0012\u0004\u0012\u00028\u00000\u001f¢\u0006\u0002\u0010!R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00060\u0007j\u0002`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/psi/BuilderByPattern;", "TElement", "", "()V", "arguments", "Ljava/util/ArrayList;", "patternBuilder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "appendChildRange", "range", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "appendExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "appendExpressions", "expressions", "", "separator", "", "appendFixedText", "text", "appendName", "name", "Lorg/jetbrains/kotlin/name/Name;", "appendNonFormattedText", "appendTypeReference", "typeRef", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "factory", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderByPattern<TElement> {
    private final StringBuilder patternBuilder = new StringBuilder();
    private final ArrayList<Object> arguments = new ArrayList<>();

    public final BuilderByPattern<TElement> appendFixedText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.patternBuilder.append(text);
        return this;
    }

    public final BuilderByPattern<TElement> appendNonFormattedText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.patternBuilder.append("$" + this.arguments.size());
        this.arguments.add(text);
        return this;
    }

    public final BuilderByPattern<TElement> appendExpression(KtExpression expression) {
        if (expression != null) {
            this.patternBuilder.append("$" + this.arguments.size());
            this.arguments.add(expression);
        }
        return this;
    }

    public static /* synthetic */ BuilderByPattern appendExpressions$default(BuilderByPattern builderByPattern, Iterable iterable, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = ",";
        }
        return builderByPattern.appendExpressions(iterable, str);
    }

    public final BuilderByPattern<TElement> appendExpressions(Iterable<? extends KtExpression> expressions, String separator) {
        Intrinsics.checkNotNullParameter(expressions, "expressions");
        Intrinsics.checkNotNullParameter(separator, "separator");
        int i = 0;
        for (KtExpression ktExpression : expressions) {
            int i2 = i + 1;
            if (i > 0) {
                appendFixedText(separator);
            }
            appendExpression(ktExpression);
            i = i2;
        }
        return this;
    }

    public final BuilderByPattern<TElement> appendTypeReference(KtTypeReference typeRef) {
        if (typeRef != null) {
            this.patternBuilder.append("$" + this.arguments.size());
            this.arguments.add(typeRef);
        }
        return this;
    }

    public final BuilderByPattern<TElement> appendName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.patternBuilder.append("$" + this.arguments.size());
        this.arguments.add(name2);
        return this;
    }

    public final BuilderByPattern<TElement> appendChildRange(PsiChildRange range) {
        Intrinsics.checkNotNullParameter(range, "range");
        this.patternBuilder.append("$" + this.arguments.size());
        this.arguments.add(range);
        return this;
    }

    public final TElement create(Function2<? super String, ? super Object[], ? extends TElement> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        String string = this.patternBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Object array = this.arguments.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        return factory.invoke(string, array);
    }
}
