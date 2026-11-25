package org.jetbrains.kotlin.js.inline.clean;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;

/* JADX INFO: compiled from: FunctionPostProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\r\u001a\u00020\u000eR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/FunctionPostProcessor;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "optimizations", "", "Lkotlin/Function0;", "", "getOptimizations", "()Ljava/util/List;", "getRoot", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "apply", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionPostProcessor {
    private final List<Function0<Boolean>> optimizations;
    private final JsFunction root;

    public FunctionPostProcessor(JsFunction root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.optimizations = CollectionsKt.listOf((Object[]) new Function0[]{new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new RedundantLabelRemoval(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new EmptyStatementElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new WhileConditionFolding(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$4
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new DoWhileGuardElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$5
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(new TemporaryVariableElimination(this.this$0.getRoot()).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$6
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new RedundantCallElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$7
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new IfStatementReduction(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$8
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new DeadCodeElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$9
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new RedundantVariableDeclarationElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$10
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(new RedundantStatementElimination(this.this$0.getRoot()).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$11
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new CoroutineStateElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$12
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new BoxingUnboxingElimination(body).apply());
            }
        }, new Function0<Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.FunctionPostProcessor$optimizations$13
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                JsBlock body = this.this$0.getRoot().getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return Boolean.valueOf(new MoveTemporaryVariableDeclarationToAssignment(body).apply());
            }
        }});
    }

    public final JsFunction getRoot() {
        return this.root;
    }

    public final List<Function0<Boolean>> getOptimizations() {
        return this.optimizations;
    }

    public final void apply() {
        boolean zBooleanValue;
        do {
            Iterator<Function0<Boolean>> it = this.optimizations.iterator();
            zBooleanValue = false;
            while (it.hasNext()) {
                zBooleanValue |= it.next().invoke().booleanValue();
            }
        } while (zBooleanValue);
    }
}
