package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.coroutine.CoroutineMetadataPropertiesKt;

/* JADX INFO: compiled from: CoroutineStateElimination.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/CoroutineStateElimination;", "", "body", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;)V", "apply", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineStateElimination {
    private final JsBlock body;

    public CoroutineStateElimination(JsBlock body) {
        Intrinsics.checkNotNullParameter(body, "body");
        this.body = body;
    }

    public final boolean apply() {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        this.body.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination.apply.1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) {
                Intrinsics.checkNotNullParameter(x, "x");
                List<JsStatement> statements = x.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                visitStatements(statements);
                super.visitBlock(x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitCase(JsCase x) {
                Intrinsics.checkNotNullParameter(x, "x");
                List<JsStatement> statements = x.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                visitStatements(statements);
                super.visitCase(x);
            }

            /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder] */
            /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder] */
            /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder] */
            private final void visitStatements(List<JsStatement> statements) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                ?? r1 = new Object() { // from class: org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder
                    private Integer value;

                    public final Integer getValue() {
                        return this.value;
                    }

                    public final void setValue(Integer num) {
                        this.value = num;
                    }
                };
                ?? r2 = new Object() { // from class: org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder
                    private Integer value;

                    public final Integer getValue() {
                        return this.value;
                    }

                    public final void setValue(Integer num) {
                        this.value = num;
                    }
                };
                ?? r3 = new Object() { // from class: org.jetbrains.kotlin.js.inline.clean.CoroutineStateElimination$apply$1$visitStatements$IndexHolder
                    private Integer value;

                    public final Integer getValue() {
                        return this.value;
                    }

                    public final void setValue(Integer num) {
                        this.value = num;
                    }
                };
                int i = 0;
                for (JsStatement jsStatement : statements) {
                    int i2 = i + 1;
                    ArrayList<CoroutineStateElimination$apply$1$visitStatements$IndexHolder> arrayList = new ArrayList();
                    if (jsStatement instanceof JsExpressionStatement) {
                        JsExpressionStatement jsExpressionStatement = (JsExpressionStatement) jsStatement;
                        if (CoroutineMetadataPropertiesKt.getTargetBlock(jsExpressionStatement)) {
                            arrayList.add(r1);
                        }
                        if (CoroutineMetadataPropertiesKt.getTargetExceptionBlock(jsExpressionStatement)) {
                            arrayList.add(r2);
                        }
                        if (CoroutineMetadataPropertiesKt.getFinallyPath(jsExpressionStatement)) {
                            arrayList.add(r3);
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        for (CoroutineStateElimination$apply$1$visitStatements$IndexHolder coroutineStateElimination$apply$1$visitStatements$IndexHolder : arrayList) {
                            Integer value = coroutineStateElimination$apply$1$visitStatements$IndexHolder.getValue();
                            if (value != null) {
                                linkedHashSet.add(Integer.valueOf(value.intValue()));
                            }
                            coroutineStateElimination$apply$1$visitStatements$IndexHolder.setValue(Integer.valueOf(i));
                        }
                    } else {
                        r1.setValue(null);
                        r2.setValue(null);
                        r3.setValue(null);
                    }
                    i = i2;
                }
                Iterator it = CollectionsKt.reversed(CollectionsKt.sorted(linkedHashSet)).iterator();
                while (it.hasNext()) {
                    statements.remove(((Number) it.next()).intValue());
                }
                if (linkedHashSet.isEmpty()) {
                    return;
                }
                booleanRef.element = true;
            }
        });
        return booleanRef.element;
    }
}
