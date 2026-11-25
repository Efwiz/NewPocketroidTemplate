package org.antlr.stringtemplate.language;

import antlr.RecognitionException;
import antlr.collections.AST;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

/* JADX INFO: loaded from: classes12.dex */
public class ConditionalExpr extends ASTExpr {
    List elseIfSubtemplates;
    StringTemplate elseSubtemplate;
    StringTemplate subtemplate;

    protected static class ElseIfClauseData {
        ASTExpr expr;
        StringTemplate st;

        protected ElseIfClauseData() {
        }
    }

    public ConditionalExpr(StringTemplate stringTemplate, AST ast) {
        super(stringTemplate, ast, null);
        this.subtemplate = null;
        this.elseIfSubtemplates = null;
        this.elseSubtemplate = null;
    }

    public void setSubtemplate(StringTemplate stringTemplate) {
        this.subtemplate = stringTemplate;
    }

    public void addElseIfSubtemplate(ASTExpr aSTExpr, StringTemplate stringTemplate) {
        if (this.elseIfSubtemplates == null) {
            this.elseIfSubtemplates = new ArrayList();
        }
        this.elseIfSubtemplates.add(new ElseIfClauseData(aSTExpr, stringTemplate) { // from class: org.antlr.stringtemplate.language.ConditionalExpr.1
            private final /* synthetic */ ASTExpr val$conditionalTree;
            private final /* synthetic */ StringTemplate val$subtemplate;

            {
                this.val$conditionalTree = aSTExpr;
                this.val$subtemplate = stringTemplate;
                this.expr = aSTExpr;
                this.st = stringTemplate;
            }
        });
    }

    public StringTemplate getSubtemplate() {
        return this.subtemplate;
    }

    public StringTemplate getElseSubtemplate() {
        return this.elseSubtemplate;
    }

    public void setElseSubtemplate(StringTemplate stringTemplate) {
        this.elseSubtemplate = stringTemplate;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x005b A[Catch: RecognitionException -> 0x0075, TRY_LEAVE, TryCatch #0 {RecognitionException -> 0x0075, blocks: (B:26:0x0057, B:28:0x005b), top: B:43:0x0057 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0078  */
    /* JADX WARN: Code duplicated, block: B:36:0x007f A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:46:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:50:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:? A[RETURN, SYNTHETIC] */
    @Override // org.antlr.stringtemplate.language.ASTExpr, org.antlr.stringtemplate.language.Expr
    public int write(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws IOException {
        int iWriteSubTemplate;
        StringTemplate stringTemplate2;
        int iWrite;
        int i = 0;
        if (this.exprTree == null || stringTemplate == null || stringTemplateWriter == null) {
            return 0;
        }
        ActionEvaluator actionEvaluator = new ActionEvaluator(stringTemplate, this, stringTemplateWriter);
        try {
            if (actionEvaluator.ifCondition(this.exprTree.getFirstChild())) {
                iWriteSubTemplate = writeSubTemplate(stringTemplate, stringTemplateWriter, this.subtemplate);
            } else {
                List list = this.elseIfSubtemplates;
                if (list != null && list.size() > 0) {
                    int i2 = 0;
                    while (true) {
                        if (i2 < this.elseIfSubtemplates.size()) {
                            ElseIfClauseData elseIfClauseData = (ElseIfClauseData) this.elseIfSubtemplates.get(i2);
                            if (actionEvaluator.ifCondition(elseIfClauseData.expr.exprTree)) {
                                writeSubTemplate(stringTemplate, stringTemplateWriter, elseIfClauseData.st);
                                iWriteSubTemplate = 0;
                            } else {
                                i2++;
                            }
                        }
                    }
                }
                iWriteSubTemplate = 0;
                if (i == 0) {
                    try {
                        stringTemplate2 = this.elseSubtemplate;
                        if (stringTemplate2 != null) {
                            StringTemplate instanceOf = stringTemplate2.getInstanceOf();
                            instanceOf.setEnclosingInstance(stringTemplate);
                            instanceOf.setGroup(stringTemplate.getGroup());
                            instanceOf.setNativeGroup(stringTemplate.getNativeGroup());
                            iWrite = instanceOf.write(stringTemplateWriter);
                        } else {
                            iWrite = iWriteSubTemplate;
                        }
                    } catch (RecognitionException e) {
                        e = e;
                        i = iWriteSubTemplate;
                        stringTemplate.error(new StringBuffer("can't evaluate tree: ").append(this.exprTree.toStringList()).toString(), e);
                        return i;
                    }
                } else {
                    iWrite = iWriteSubTemplate;
                }
                if (i == 0) {
                    return iWrite;
                }
                try {
                    if (this.elseSubtemplate == null) {
                        return -1;
                    }
                    return iWrite;
                } catch (RecognitionException e2) {
                    i = iWrite;
                    e = e2;
                    stringTemplate.error(new StringBuffer("can't evaluate tree: ").append(this.exprTree.toStringList()).toString(), e);
                    return i;
                }
            }
            i = 1;
            if (i == 0) {
                stringTemplate2 = this.elseSubtemplate;
                if (stringTemplate2 != null) {
                    StringTemplate instanceOf2 = stringTemplate2.getInstanceOf();
                    instanceOf2.setEnclosingInstance(stringTemplate);
                    instanceOf2.setGroup(stringTemplate.getGroup());
                    instanceOf2.setNativeGroup(stringTemplate.getNativeGroup());
                    iWrite = instanceOf2.write(stringTemplateWriter);
                } else {
                    iWrite = iWriteSubTemplate;
                }
            } else {
                iWrite = iWriteSubTemplate;
            }
            if (i == 0) {
                return iWrite;
            }
            if (this.elseSubtemplate == null) {
                return -1;
            }
            return iWrite;
        } catch (RecognitionException e3) {
            e = e3;
        }
    }

    protected int writeSubTemplate(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter, StringTemplate stringTemplate2) throws IOException {
        StringTemplate instanceOf = stringTemplate2.getInstanceOf();
        instanceOf.setEnclosingInstance(stringTemplate);
        instanceOf.setGroup(stringTemplate.getGroup());
        instanceOf.setNativeGroup(stringTemplate.getNativeGroup());
        return instanceOf.write(stringTemplateWriter);
    }
}
