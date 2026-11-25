package org.jetbrains.kotlin.js.inline.clean;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: IfStatementReduction.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\u0006J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0002J\u001c\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/IfStatementReduction;", "", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "hasChanges", "", "visitor", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "getVisitor", "()Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "apply", "extractSingleStatement", "statement", "lhsEqual", "a", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IfStatementReduction {
    private boolean hasChanges;
    private final JsStatement root;
    private final JsVisitorWithContextImpl visitor;

    public IfStatementReduction(JsStatement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        this.root = root;
        this.visitor = new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.clean.IfStatementReduction$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public boolean visit(JsIf x, JsContext<JsNode> ctx) {
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                JsStatement thenStatement = x.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                JsStatement elseStatement = x.getElseStatement();
                if (MetadataProperties.getSynthetic(x) && elseStatement != null) {
                    JsStatement jsStatementExtractSingleStatement = this.this$0.extractSingleStatement(thenStatement);
                    JsStatement jsStatementExtractSingleStatement2 = this.this$0.extractSingleStatement(elseStatement);
                    if ((jsStatementExtractSingleStatement instanceof JsExpressionStatement) && (jsStatementExtractSingleStatement2 instanceof JsExpressionStatement)) {
                        Pair<JsExpression, JsExpression> pairDecomposeAssignment = JsAstUtils.decomposeAssignment(((JsExpressionStatement) jsStatementExtractSingleStatement).getExpression());
                        Pair<JsExpression, JsExpression> pairDecomposeAssignment2 = JsAstUtils.decomposeAssignment(((JsExpressionStatement) jsStatementExtractSingleStatement2).getExpression());
                        if (pairDecomposeAssignment != null && pairDecomposeAssignment2 != null) {
                            JsExpression jsExpressionComponent1 = pairDecomposeAssignment.component1();
                            JsExpression jsExpressionComponent2 = pairDecomposeAssignment.component2();
                            JsExpression jsExpressionComponent3 = pairDecomposeAssignment2.component1();
                            JsExpression jsExpressionComponent4 = pairDecomposeAssignment2.component2();
                            if (this.this$0.lhsEqual(jsExpressionComponent1, jsExpressionComponent3)) {
                                this.this$0.hasChanges = true;
                                JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(JsAstUtils.assignment(jsExpressionComponent1, new JsConditional(x.getIfExpression(), jsExpressionComponent2, jsExpressionComponent4)));
                                MetadataProperties.setSynthetic(jsExpressionStatement, MetadataProperties.getSynthetic((HasMetadata) jsStatementExtractSingleStatement) && MetadataProperties.getSynthetic((HasMetadata) jsStatementExtractSingleStatement2));
                                JsExpressionStatement jsExpressionStatement2 = jsExpressionStatement;
                                ctx.replaceMe(jsExpressionStatement2);
                                accept(jsExpressionStatement2);
                                return false;
                            }
                        }
                    } else if ((jsStatementExtractSingleStatement instanceof JsVars) && (jsStatementExtractSingleStatement2 instanceof JsVars)) {
                        JsVars jsVars = (JsVars) jsStatementExtractSingleStatement;
                        if (jsVars.getVars().size() == 1) {
                            JsVars jsVars2 = (JsVars) jsStatementExtractSingleStatement2;
                            if (jsVars2.getVars().size() == 1) {
                                JsVars.JsVar jsVar = jsVars.getVars().get(0);
                                JsVars.JsVar jsVar2 = jsVars2.getVars().get(0);
                                JsExpression initExpression = jsVar.getInitExpression();
                                JsExpression initExpression2 = jsVar2.getInitExpression();
                                if (Intrinsics.areEqual(jsVar.getName(), jsVar2.getName()) && initExpression != null && initExpression2 != null) {
                                    this.this$0.hasChanges = true;
                                    JsVars jsVarsNewVar = JsAstUtils.newVar(jsVar.getName(), new JsConditional(x.getIfExpression(), initExpression, initExpression2));
                                    Intrinsics.checkNotNullExpressionValue(jsVarsNewVar, "newVar(...)");
                                    MetadataProperties.setSynthetic(jsVarsNewVar, MetadataProperties.getSynthetic((HasMetadata) jsStatementExtractSingleStatement) && MetadataProperties.getSynthetic((HasMetadata) jsStatementExtractSingleStatement2));
                                    JsVars jsVars3 = jsVarsNewVar;
                                    ctx.replaceMe(jsVars3);
                                    accept(jsVars3);
                                    return false;
                                }
                            }
                        }
                    } else if ((jsStatementExtractSingleStatement instanceof JsReturn) && (jsStatementExtractSingleStatement2 instanceof JsReturn)) {
                        JsExpression expression = ((JsReturn) jsStatementExtractSingleStatement).getExpression();
                        JsExpression expression2 = ((JsReturn) jsStatementExtractSingleStatement2).getExpression();
                        if (expression != null && expression2 != null) {
                            this.this$0.hasChanges = true;
                            JsReturn jsReturn = new JsReturn(new JsConditional(x.getIfExpression(), expression, expression2));
                            accept(jsReturn);
                            ctx.replaceMe(jsReturn);
                            return false;
                        }
                    }
                }
                return super.visit(x, ctx);
            }
        };
    }

    public final boolean apply() {
        this.visitor.accept(this.root);
        return this.hasChanges;
    }

    public final JsVisitorWithContextImpl getVisitor() {
        return this.visitor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsStatement extractSingleStatement(JsStatement statement) {
        while (statement instanceof JsBlock) {
            JsBlock jsBlock = (JsBlock) statement;
            if (jsBlock.getStatements().size() != 1) {
                break;
            }
            JsStatement jsStatement = jsBlock.getStatements().get(0);
            Intrinsics.checkNotNullExpressionValue(jsStatement, "get(...)");
            statement = jsStatement;
        }
        return statement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean lhsEqual(JsExpression a, JsExpression b) {
        if (a == null && b == null) {
            return true;
        }
        if ((a instanceof JsNameRef) && (b instanceof JsNameRef)) {
            JsNameRef jsNameRef = (JsNameRef) a;
            JsNameRef jsNameRef2 = (JsNameRef) b;
            if (Intrinsics.areEqual(jsNameRef.getName(), jsNameRef2.getName()) && lhsEqual(jsNameRef.getQualifier(), jsNameRef2.getQualifier())) {
                return true;
            }
        } else if ((a instanceof JsArrayAccess) && (b instanceof JsArrayAccess)) {
            JsArrayAccess jsArrayAccess = (JsArrayAccess) a;
            JsArrayAccess jsArrayAccess2 = (JsArrayAccess) b;
            if (lhsEqual(jsArrayAccess.getArrayExpression(), jsArrayAccess2.getArrayExpression()) && lhsEqual(jsArrayAccess.getIndexExpression(), jsArrayAccess2.getIndexExpression())) {
                return true;
            }
        }
        return false;
    }
}
