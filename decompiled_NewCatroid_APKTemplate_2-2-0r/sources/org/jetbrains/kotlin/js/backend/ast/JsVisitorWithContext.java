package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JsVisitorWithContext {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
            case 51:
            case 53:
            case 55:
            case 57:
            case 59:
            case 61:
            case 63:
            case 65:
            case 67:
            case 69:
            case 71:
            case 73:
            case 75:
            case 77:
            case 79:
            case 81:
            case 83:
            case 85:
            case 87:
            case 89:
            case 91:
            case 93:
            case 95:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 117:
            case 119:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
            case 133:
            case 135:
            case 137:
            case 139:
            case 141:
            case 143:
            case 145:
            case 147:
            case 149:
            case 151:
            case 153:
            case 155:
            case 157:
            case 159:
            case 161:
            case 163:
            case 165:
            case 167:
            case 169:
            case 171:
            case 173:
            case 175:
            case 177:
            case 179:
            case 181:
            case 183:
            case 185:
            case 187:
            case 189:
            case 191:
            case 193:
            case 195:
            case 197:
                objArr[0] = "ctx";
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 42:
            case 44:
            case 46:
            case 48:
            case 50:
            case 52:
            case 54:
            case 56:
            case 58:
            case 60:
            case 62:
            case 64:
            case 66:
            case 68:
            case 70:
            case 72:
            case 74:
            case 76:
            case 78:
            case 80:
            case 82:
            case 84:
            case 86:
            case 88:
            case 90:
            case 92:
            case 94:
            case 96:
            case 98:
            case 100:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 118:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130:
            case 132:
            case 134:
            case 136:
            case 138:
            case 140:
            case 142:
            case 144:
            case 146:
            case 148:
            case 150:
            case 152:
            case 154:
            case 156:
            case 158:
            case 160:
            case 162:
            case 164:
            case 166:
            case 168:
            case 170:
            case 172:
            case 174:
            case 176:
            case 178:
            case 180:
            case 182:
            case 184:
            case 186:
            case 188:
            case 190:
            case 192:
            case 194:
            case 196:
            default:
                objArr[0] = "x";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext";
        switch (i) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
                objArr[2] = "visit";
                break;
            default:
                objArr[2] = "endVisit";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract <T extends JsNode> T doAccept(T t);

    protected abstract <T extends JsNode> void doAcceptList(List<T> list);

    protected abstract JsExpression doAcceptLvalue(JsExpression jsExpression);

    protected abstract <T extends JsStatement> JsStatement doAcceptStatement(T t);

    protected abstract void doAcceptStatementList(List<JsStatement> list);

    protected abstract <T extends JsNode> void doTraverse(T t, JsContext jsContext);

    public void endVisit(JsBlock jsBlock, JsContext jsContext) {
        if (jsBlock == null) {
            $$$reportNull$$$0(8);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(9);
        }
    }

    public void endVisit(JsBreak jsBreak, JsContext jsContext) {
        if (jsBreak == null) {
            $$$reportNull$$$0(12);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(13);
        }
    }

    public void endVisit(JsCase jsCase, JsContext jsContext) {
        if (jsCase == null) {
            $$$reportNull$$$0(14);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(15);
        }
    }

    public void endVisit(JsCatch jsCatch, JsContext jsContext) {
        if (jsCatch == null) {
            $$$reportNull$$$0(16);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(17);
        }
    }

    public void endVisit(JsContinue jsContinue, JsContext jsContext) {
        if (jsContinue == null) {
            $$$reportNull$$$0(22);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(23);
        }
    }

    public void endVisit(JsDebugger jsDebugger, JsContext jsContext) {
        if (jsDebugger == null) {
            $$$reportNull$$$0(24);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(25);
        }
    }

    public void endVisit(JsDefault jsDefault, JsContext jsContext) {
        if (jsDefault == null) {
            $$$reportNull$$$0(26);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(27);
        }
    }

    public void endVisit(JsEmpty jsEmpty, JsContext jsContext) {
        if (jsEmpty == null) {
            $$$reportNull$$$0(30);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(31);
        }
    }

    public void endVisit(JsExport jsExport, JsContext jsContext) {
        if (jsExport == null) {
            $$$reportNull$$$0(94);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(95);
        }
    }

    public void endVisit(JsExpression jsExpression, JsContext jsContext) {
        if (jsExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(1);
        }
    }

    public void endVisit(JsExpressionStatement jsExpressionStatement, JsContext jsContext) {
        if (jsExpressionStatement == null) {
            $$$reportNull$$$0(32);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(33);
        }
    }

    public void endVisit(JsIf jsIf, JsContext jsContext) {
        if (jsIf == null) {
            $$$reportNull$$$0(40);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(41);
        }
    }

    public void endVisit(JsImport jsImport, JsContext jsContext) {
        if (jsImport == null) {
            $$$reportNull$$$0(96);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(97);
        }
    }

    public void endVisit(JsLabel jsLabel, JsContext jsContext) {
        if (jsLabel == null) {
            $$$reportNull$$$0(44);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(45);
        }
    }

    public void endVisit(JsLoop jsLoop, JsContext jsContext) {
        if (jsLoop == null) {
            $$$reportNull$$$0(46);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(47);
        }
    }

    public void endVisit(JsMultiLineComment jsMultiLineComment, JsContext jsContext) {
        if (jsMultiLineComment == null) {
            $$$reportNull$$$0(92);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(93);
        }
    }

    public void endVisit(JsName jsName, JsContext jsContext) {
        if (jsName == null) {
            $$$reportNull$$$0(48);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(49);
        }
    }

    public void endVisit(JsNew jsNew, JsContext jsContext) {
        if (jsNew == null) {
            $$$reportNull$$$0(52);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(53);
        }
    }

    public void endVisit(JsParameter jsParameter, JsContext jsContext) {
        if (jsParameter == null) {
            $$$reportNull$$$0(60);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(61);
        }
    }

    public void endVisit(JsProgram jsProgram, JsContext jsContext) {
        if (jsProgram == null) {
            $$$reportNull$$$0(66);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(67);
        }
    }

    public void endVisit(JsPropertyInitializer jsPropertyInitializer, JsContext jsContext) {
        if (jsPropertyInitializer == null) {
            $$$reportNull$$$0(68);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(69);
        }
    }

    public void endVisit(JsReturn jsReturn, JsContext jsContext) {
        if (jsReturn == null) {
            $$$reportNull$$$0(72);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(73);
        }
    }

    public void endVisit(JsSingleLineComment jsSingleLineComment, JsContext jsContext) {
        if (jsSingleLineComment == null) {
            $$$reportNull$$$0(90);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(91);
        }
    }

    public void endVisit(JsSwitch jsSwitch, JsContext jsContext) {
        if (jsSwitch == null) {
            $$$reportNull$$$0(76);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(77);
        }
    }

    public void endVisit(JsThrow jsThrow, JsContext jsContext) {
        if (jsThrow == null) {
            $$$reportNull$$$0(82);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(83);
        }
    }

    public void endVisit(JsTry jsTry, JsContext jsContext) {
        if (jsTry == null) {
            $$$reportNull$$$0(84);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(85);
        }
    }

    public void endVisit(JsVars.JsVar jsVar, JsContext jsContext) {
        if (jsVar == null) {
            $$$reportNull$$$0(86);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(87);
        }
    }

    public void endVisit(JsVars jsVars, JsContext jsContext) {
        if (jsVars == null) {
            $$$reportNull$$$0(88);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(89);
        }
    }

    public boolean visit(JsArrayAccess jsArrayAccess, JsContext jsContext) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(100);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(101);
        return true;
    }

    public boolean visit(JsArrayLiteral jsArrayLiteral, JsContext jsContext) {
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(102);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(103);
        return true;
    }

    public boolean visit(JsBinaryOperation jsBinaryOperation, JsContext jsContext) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(104);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(105);
        return true;
    }

    public boolean visit(JsBlock jsBlock, JsContext jsContext) {
        if (jsBlock == null) {
            $$$reportNull$$$0(106);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(107);
        return true;
    }

    public boolean visit(JsBooleanLiteral jsBooleanLiteral, JsContext jsContext) {
        if (jsBooleanLiteral == null) {
            $$$reportNull$$$0(108);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(109);
        return true;
    }

    public boolean visit(JsBreak jsBreak, JsContext jsContext) {
        if (jsBreak == null) {
            $$$reportNull$$$0(110);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(111);
        return true;
    }

    public boolean visit(JsCase jsCase, JsContext jsContext) {
        if (jsCase == null) {
            $$$reportNull$$$0(112);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(113);
        return true;
    }

    public boolean visit(JsCatch jsCatch, JsContext jsContext) {
        if (jsCatch == null) {
            $$$reportNull$$$0(114);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(115);
        return true;
    }

    public boolean visit(JsClass jsClass, JsContext jsContext) {
        if (jsClass == null) {
            $$$reportNull$$$0(116);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(117);
        return true;
    }

    public boolean visit(JsConditional jsConditional, JsContext jsContext) {
        if (jsConditional == null) {
            $$$reportNull$$$0(118);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(119);
        return true;
    }

    public boolean visit(JsContinue jsContinue, JsContext jsContext) {
        if (jsContinue == null) {
            $$$reportNull$$$0(120);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(121);
        return true;
    }

    public boolean visit(JsDebugger jsDebugger, JsContext jsContext) {
        if (jsDebugger == null) {
            $$$reportNull$$$0(122);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(123);
        return true;
    }

    public boolean visit(JsDefault jsDefault, JsContext jsContext) {
        if (jsDefault == null) {
            $$$reportNull$$$0(124);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(125);
        return true;
    }

    public boolean visit(JsEmpty jsEmpty, JsContext jsContext) {
        if (jsEmpty == null) {
            $$$reportNull$$$0(128);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(129);
        return true;
    }

    public boolean visit(JsExport jsExport, JsContext jsContext) {
        if (jsExport == null) {
            $$$reportNull$$$0(194);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(195);
        return true;
    }

    public boolean visit(JsExpressionStatement jsExpressionStatement, JsContext jsContext) {
        if (jsExpressionStatement == null) {
            $$$reportNull$$$0(130);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(131);
        return true;
    }

    public boolean visit(JsFunction jsFunction, JsContext jsContext) {
        if (jsFunction == null) {
            $$$reportNull$$$0(136);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(137);
        return true;
    }

    public boolean visit(JsIf jsIf, JsContext jsContext) {
        if (jsIf == null) {
            $$$reportNull$$$0(138);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(139);
        return true;
    }

    public boolean visit(JsImport jsImport, JsContext jsContext) {
        if (jsImport == null) {
            $$$reportNull$$$0(196);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(197);
        return true;
    }

    public boolean visit(JsInvocation jsInvocation, JsContext jsContext) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(140);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(141);
        return true;
    }

    public boolean visit(JsLabel jsLabel, JsContext jsContext) {
        if (jsLabel == null) {
            $$$reportNull$$$0(142);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(143);
        return true;
    }

    public boolean visit(JsLoop jsLoop, JsContext jsContext) {
        if (jsLoop == null) {
            $$$reportNull$$$0(144);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(145);
        return true;
    }

    public boolean visit(JsMultiLineComment jsMultiLineComment, JsContext jsContext) {
        if (jsMultiLineComment == null) {
            $$$reportNull$$$0(192);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(193);
        return true;
    }

    public boolean visit(JsName jsName, JsContext jsContext) {
        if (jsName == null) {
            $$$reportNull$$$0(146);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(147);
        return true;
    }

    public boolean visit(JsNameRef jsNameRef, JsContext jsContext) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(148);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(149);
        return true;
    }

    public boolean visit(JsNew jsNew, JsContext jsContext) {
        if (jsNew == null) {
            $$$reportNull$$$0(150);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(151);
        return true;
    }

    public boolean visit(JsNullLiteral jsNullLiteral, JsContext jsContext) {
        if (jsNullLiteral == null) {
            $$$reportNull$$$0(152);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(153);
        return true;
    }

    public boolean visit(JsNumberLiteral jsNumberLiteral, JsContext jsContext) {
        if (jsNumberLiteral == null) {
            $$$reportNull$$$0(154);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(155);
        return true;
    }

    public boolean visit(JsObjectLiteral jsObjectLiteral, JsContext jsContext) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(156);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(157);
        return true;
    }

    public boolean visit(JsParameter jsParameter, JsContext jsContext) {
        if (jsParameter == null) {
            $$$reportNull$$$0(158);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(159);
        return true;
    }

    public boolean visit(JsPostfixOperation jsPostfixOperation, JsContext jsContext) {
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(160);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(161);
        return true;
    }

    public boolean visit(JsPrefixOperation jsPrefixOperation, JsContext jsContext) {
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(162);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(163);
        return true;
    }

    public boolean visit(JsProgram jsProgram, JsContext jsContext) {
        if (jsProgram == null) {
            $$$reportNull$$$0(164);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(165);
        return true;
    }

    public boolean visit(JsPropertyInitializer jsPropertyInitializer, JsContext jsContext) {
        if (jsPropertyInitializer == null) {
            $$$reportNull$$$0(166);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(167);
        return true;
    }

    public boolean visit(JsRegExp jsRegExp, JsContext jsContext) {
        if (jsRegExp == null) {
            $$$reportNull$$$0(168);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(169);
        return true;
    }

    public boolean visit(JsReturn jsReturn, JsContext jsContext) {
        if (jsReturn == null) {
            $$$reportNull$$$0(170);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(171);
        return true;
    }

    public boolean visit(JsSingleLineComment jsSingleLineComment, JsContext jsContext) {
        if (jsSingleLineComment == null) {
            $$$reportNull$$$0(190);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(191);
        return true;
    }

    public boolean visit(JsStringLiteral jsStringLiteral, JsContext jsContext) {
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(172);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(173);
        return true;
    }

    public boolean visit(JsSuperRef jsSuperRef, JsContext jsContext) {
        if (jsSuperRef == null) {
            $$$reportNull$$$0(178);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(179);
        return true;
    }

    public boolean visit(JsSwitch jsSwitch, JsContext jsContext) {
        if (jsSwitch == null) {
            $$$reportNull$$$0(174);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(175);
        return true;
    }

    public boolean visit(JsThisRef jsThisRef, JsContext jsContext) {
        if (jsThisRef == null) {
            $$$reportNull$$$0(176);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(177);
        return true;
    }

    public boolean visit(JsThrow jsThrow, JsContext jsContext) {
        if (jsThrow == null) {
            $$$reportNull$$$0(180);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(181);
        return true;
    }

    public boolean visit(JsTry jsTry, JsContext jsContext) {
        if (jsTry == null) {
            $$$reportNull$$$0(182);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(183);
        return true;
    }

    public boolean visit(JsVars.JsVar jsVar, JsContext jsContext) {
        if (jsVar == null) {
            $$$reportNull$$$0(184);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(185);
        return true;
    }

    public boolean visit(JsVars jsVars, JsContext jsContext) {
        if (jsVars == null) {
            $$$reportNull$$$0(186);
        }
        if (jsContext != null) {
            return true;
        }
        $$$reportNull$$$0(187);
        return true;
    }

    public final <T extends JsNode> T accept(T t) {
        if (t == null) {
            return null;
        }
        return (T) doAccept(t);
    }

    public JsExpression acceptLvalue(JsExpression jsExpression) {
        if (jsExpression == null) {
            return null;
        }
        return doAcceptLvalue(jsExpression);
    }

    public final <T extends JsNode> void acceptList(List<T> list) {
        doAcceptList(list);
    }

    public final <T extends JsStatement> T acceptStatement(T t) {
        if (t == null) {
            return null;
        }
        return (T) doAcceptStatement(t);
    }

    public final void acceptStatementList(List<JsStatement> list) {
        doAcceptStatementList(list);
    }

    public void endVisit(JsArrayAccess jsArrayAccess, JsContext jsContext) {
        if (jsArrayAccess == null) {
            $$$reportNull$$$0(2);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(3);
        }
        endVisit((JsExpression) jsArrayAccess, jsContext);
    }

    public void endVisit(JsArrayLiteral jsArrayLiteral, JsContext jsContext) {
        if (jsArrayLiteral == null) {
            $$$reportNull$$$0(4);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(5);
        }
        endVisit((JsExpression) jsArrayLiteral, jsContext);
    }

    public void endVisit(JsBinaryOperation jsBinaryOperation, JsContext jsContext) {
        if (jsBinaryOperation == null) {
            $$$reportNull$$$0(6);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(7);
        }
        endVisit((JsExpression) jsBinaryOperation, jsContext);
    }

    public void endVisit(JsBooleanLiteral jsBooleanLiteral, JsContext jsContext) {
        if (jsBooleanLiteral == null) {
            $$$reportNull$$$0(10);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(11);
        }
        endVisit((JsExpression) jsBooleanLiteral, jsContext);
    }

    public void endVisit(JsClass jsClass, JsContext jsContext) {
        if (jsClass == null) {
            $$$reportNull$$$0(18);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(19);
        }
        endVisit((JsExpression) jsClass, jsContext);
    }

    public void endVisit(JsConditional jsConditional, JsContext jsContext) {
        if (jsConditional == null) {
            $$$reportNull$$$0(20);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(21);
        }
        endVisit((JsExpression) jsConditional, jsContext);
    }

    public void endVisit(JsDoWhile jsDoWhile, JsContext jsContext) {
        if (jsDoWhile == null) {
            $$$reportNull$$$0(28);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(29);
        }
        endVisit((JsLoop) jsDoWhile, jsContext);
    }

    public void endVisit(JsFor jsFor, JsContext jsContext) {
        if (jsFor == null) {
            $$$reportNull$$$0(34);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(35);
        }
        endVisit((JsLoop) jsFor, jsContext);
    }

    public void endVisit(JsForIn jsForIn, JsContext jsContext) {
        if (jsForIn == null) {
            $$$reportNull$$$0(36);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(37);
        }
        endVisit((JsLoop) jsForIn, jsContext);
    }

    public void endVisit(JsFunction jsFunction, JsContext jsContext) {
        if (jsFunction == null) {
            $$$reportNull$$$0(38);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(39);
        }
        endVisit((JsExpression) jsFunction, jsContext);
    }

    public void endVisit(JsInvocation jsInvocation, JsContext jsContext) {
        if (jsInvocation == null) {
            $$$reportNull$$$0(42);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(43);
        }
        endVisit((JsExpression) jsInvocation, jsContext);
    }

    public void endVisit(JsNameRef jsNameRef, JsContext jsContext) {
        if (jsNameRef == null) {
            $$$reportNull$$$0(50);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(51);
        }
        endVisit((JsExpression) jsNameRef, jsContext);
    }

    public void endVisit(JsNullLiteral jsNullLiteral, JsContext jsContext) {
        if (jsNullLiteral == null) {
            $$$reportNull$$$0(54);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(55);
        }
        endVisit((JsExpression) jsNullLiteral, jsContext);
    }

    public void endVisit(JsNumberLiteral jsNumberLiteral, JsContext jsContext) {
        if (jsNumberLiteral == null) {
            $$$reportNull$$$0(56);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(57);
        }
        endVisit((JsExpression) jsNumberLiteral, jsContext);
    }

    public void endVisit(JsObjectLiteral jsObjectLiteral, JsContext jsContext) {
        if (jsObjectLiteral == null) {
            $$$reportNull$$$0(58);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(59);
        }
        endVisit((JsExpression) jsObjectLiteral, jsContext);
    }

    public void endVisit(JsPostfixOperation jsPostfixOperation, JsContext jsContext) {
        if (jsPostfixOperation == null) {
            $$$reportNull$$$0(62);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(63);
        }
        endVisit((JsExpression) jsPostfixOperation, jsContext);
    }

    public void endVisit(JsPrefixOperation jsPrefixOperation, JsContext jsContext) {
        if (jsPrefixOperation == null) {
            $$$reportNull$$$0(64);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(65);
        }
        endVisit((JsExpression) jsPrefixOperation, jsContext);
    }

    public void endVisit(JsRegExp jsRegExp, JsContext jsContext) {
        if (jsRegExp == null) {
            $$$reportNull$$$0(70);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(71);
        }
        endVisit((JsExpression) jsRegExp, jsContext);
    }

    public void endVisit(JsStringLiteral jsStringLiteral, JsContext jsContext) {
        if (jsStringLiteral == null) {
            $$$reportNull$$$0(74);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(75);
        }
        endVisit((JsExpression) jsStringLiteral, jsContext);
    }

    public void endVisit(JsThisRef jsThisRef, JsContext jsContext) {
        if (jsThisRef == null) {
            $$$reportNull$$$0(78);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(79);
        }
        endVisit((JsExpression) jsThisRef, jsContext);
    }

    public void endVisit(JsSuperRef jsSuperRef, JsContext jsContext) {
        if (jsSuperRef == null) {
            $$$reportNull$$$0(80);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(81);
        }
        endVisit((JsExpression) jsSuperRef, jsContext);
    }

    public void endVisit(JsWhile jsWhile, JsContext jsContext) {
        if (jsWhile == null) {
            $$$reportNull$$$0(98);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(99);
        }
        endVisit((JsLoop) jsWhile, jsContext);
    }

    public boolean visit(JsDoWhile jsDoWhile, JsContext jsContext) {
        if (jsDoWhile == null) {
            $$$reportNull$$$0(126);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(127);
        }
        return visit((JsLoop) jsDoWhile, jsContext);
    }

    public boolean visit(JsFor jsFor, JsContext jsContext) {
        if (jsFor == null) {
            $$$reportNull$$$0(132);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(133);
        }
        return visit((JsLoop) jsFor, jsContext);
    }

    public boolean visit(JsForIn jsForIn, JsContext jsContext) {
        if (jsForIn == null) {
            $$$reportNull$$$0(134);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(135);
        }
        return visit((JsLoop) jsForIn, jsContext);
    }

    public boolean visit(JsWhile jsWhile, JsContext jsContext) {
        if (jsWhile == null) {
            $$$reportNull$$$0(188);
        }
        if (jsContext == null) {
            $$$reportNull$$$0(189);
        }
        return visit((JsLoop) jsWhile, jsContext);
    }
}
