package org.jetbrains.kotlin.js.translate.utils.mutator;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class LastExpressionMutator {
    private final Mutator mutator;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 8 || i == 10 || i == 15 || i == 12 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 8 || i == 10 || i == 15 || i == 12 || i == 13) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "mutator";
        } else if (i == 4 || i == 6 || i == 8 || i == 10 || i == 15 || i == 12 || i == 13) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/utils/mutator/LastExpressionMutator";
        } else {
            objArr[0] = "node";
        }
        if (i == 4) {
            objArr[1] = "apply";
        } else if (i == 6) {
            objArr[1] = "applyToStatement";
        } else if (i == 8) {
            objArr[1] = "applyToIf";
        } else if (i == 10) {
            objArr[1] = "applyToTry";
        } else if (i == 15) {
            objArr[1] = "applyToSwitch";
        } else if (i == 12 || i == 13) {
            objArr[1] = "applyToBlock";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/utils/mutator/LastExpressionMutator";
        }
        switch (i) {
            case 2:
                objArr[2] = "<init>";
                break;
            case 3:
                objArr[2] = "apply";
                break;
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 15:
                break;
            case 5:
                objArr[2] = "applyToStatement";
                break;
            case 7:
                objArr[2] = "applyToIf";
                break;
            case 9:
                objArr[2] = "applyToTry";
                break;
            case 11:
                objArr[2] = "applyToBlock";
                break;
            case 14:
                objArr[2] = "applyToSwitch";
                break;
            default:
                objArr[2] = "mutateLastExpression";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 8 && i != 10 && i != 15 && i != 12 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static JsStatement mutateLastExpression(JsNode jsNode, Mutator mutator) {
        if (jsNode == null) {
            $$$reportNull$$$0(0);
        }
        if (mutator == null) {
            $$$reportNull$$$0(1);
        }
        return JsAstUtils.convertToStatement(new LastExpressionMutator(mutator).apply(jsNode));
    }

    private LastExpressionMutator(Mutator mutator) {
        if (mutator == null) {
            $$$reportNull$$$0(2);
        }
        this.mutator = mutator;
    }

    private JsNode apply(JsNode jsNode) {
        if (jsNode == null) {
            $$$reportNull$$$0(3);
        }
        if (jsNode instanceof JsBlock) {
            return applyToBlock((JsBlock) jsNode);
        }
        if (jsNode instanceof JsIf) {
            return applyToIf((JsIf) jsNode);
        }
        if (jsNode instanceof JsTry) {
            return applyToTry((JsTry) jsNode);
        }
        if (jsNode instanceof JsExpressionStatement) {
            return applyToStatement((JsExpressionStatement) jsNode);
        }
        if (jsNode instanceof JsSwitch) {
            return applyToSwitch((JsSwitch) jsNode);
        }
        JsNode jsNodeMutate = this.mutator.mutate(jsNode);
        if (jsNodeMutate == null) {
            $$$reportNull$$$0(4);
        }
        return jsNodeMutate;
    }

    private JsNode applyToStatement(JsExpressionStatement jsExpressionStatement) {
        if (jsExpressionStatement == null) {
            $$$reportNull$$$0(5);
        }
        JsStatement jsStatementConvertToStatement = JsAstUtils.convertToStatement(apply(jsExpressionStatement.getExpression()));
        if (jsStatementConvertToStatement == null) {
            $$$reportNull$$$0(6);
        }
        return jsStatementConvertToStatement;
    }

    private JsNode applyToIf(JsIf jsIf) {
        if (jsIf == null) {
            $$$reportNull$$$0(7);
        }
        jsIf.setThenStatement(JsAstUtils.convertToStatement(apply(jsIf.getThenStatement())));
        JsStatement elseStatement = jsIf.getElseStatement();
        if (elseStatement != null) {
            jsIf.setElseStatement(JsAstUtils.convertToStatement(apply(elseStatement)));
        }
        if (jsIf == null) {
            $$$reportNull$$$0(8);
        }
        return jsIf;
    }

    private JsNode applyToTry(JsTry jsTry) {
        if (jsTry == null) {
            $$$reportNull$$$0(9);
        }
        applyToBlock(jsTry.getTryBlock());
        Iterator<JsCatch> it = jsTry.getCatches().iterator();
        while (it.hasNext()) {
            applyToBlock(it.next().getBody());
        }
        if (jsTry == null) {
            $$$reportNull$$$0(10);
        }
        return jsTry;
    }

    private JsNode applyToBlock(JsBlock jsBlock) {
        if (jsBlock == null) {
            $$$reportNull$$$0(11);
        }
        List<JsStatement> statements = jsBlock.getStatements();
        if (statements.isEmpty()) {
            if (jsBlock == null) {
                $$$reportNull$$$0(12);
            }
            return jsBlock;
        }
        int size = statements.size() - 1;
        statements.set(size, JsAstUtils.convertToStatement(apply(statements.get(size))));
        if (jsBlock == null) {
            $$$reportNull$$$0(13);
        }
        return jsBlock;
    }

    private JsNode applyToSwitch(JsSwitch jsSwitch) {
        if (jsSwitch == null) {
            $$$reportNull$$$0(14);
        }
        for (JsSwitchMember jsSwitchMember : jsSwitch.getCases()) {
            int size = jsSwitchMember.getStatements().size();
            if (size >= 2 && (apply(jsSwitchMember.getStatements().get(size - 1)) instanceof JsBreak)) {
                int i = size - 2;
                jsSwitchMember.getStatements().set(i, JsAstUtils.convertToStatement(apply(jsSwitchMember.getStatements().get(i))));
            }
        }
        if (jsSwitch == null) {
            $$$reportNull$$$0(15);
        }
        return jsSwitch;
    }
}
