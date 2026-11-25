package org.jetbrains.kotlin.js.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: InlineableResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/InlineableResult;", "", "inlineableBody", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "resultExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "getInlineableBody", "()Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getResultExpression", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class InlineableResult {
    private final JsStatement inlineableBody;
    private final JsExpression resultExpression;

    public static /* synthetic */ InlineableResult copy$default(InlineableResult inlineableResult, JsStatement jsStatement, JsExpression jsExpression, int i, Object obj) {
        if ((i & 1) != 0) {
            jsStatement = inlineableResult.inlineableBody;
        }
        if ((i & 2) != 0) {
            jsExpression = inlineableResult.resultExpression;
        }
        return inlineableResult.copy(jsStatement, jsExpression);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final JsStatement getInlineableBody() {
        return this.inlineableBody;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JsExpression getResultExpression() {
        return this.resultExpression;
    }

    public final InlineableResult copy(JsStatement inlineableBody, JsExpression resultExpression) {
        Intrinsics.checkNotNullParameter(inlineableBody, "inlineableBody");
        return new InlineableResult(inlineableBody, resultExpression);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InlineableResult)) {
            return false;
        }
        InlineableResult inlineableResult = (InlineableResult) other;
        return Intrinsics.areEqual(this.inlineableBody, inlineableResult.inlineableBody) && Intrinsics.areEqual(this.resultExpression, inlineableResult.resultExpression);
    }

    public int hashCode() {
        int iHashCode = this.inlineableBody.hashCode() * 31;
        JsExpression jsExpression = this.resultExpression;
        return iHashCode + (jsExpression == null ? 0 : jsExpression.hashCode());
    }

    public String toString() {
        return "InlineableResult(inlineableBody=" + this.inlineableBody + ", resultExpression=" + this.resultExpression + ')';
    }

    public InlineableResult(JsStatement inlineableBody, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(inlineableBody, "inlineableBody");
        this.inlineableBody = inlineableBody;
        this.resultExpression = jsExpression;
    }

    public final JsStatement getInlineableBody() {
        return this.inlineableBody;
    }

    public final JsExpression getResultExpression() {
        return this.resultExpression;
    }
}
