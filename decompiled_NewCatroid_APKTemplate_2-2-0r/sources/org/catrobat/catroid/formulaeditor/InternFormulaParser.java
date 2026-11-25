package org.catrobat.catroid.formulaeditor;

import android.util.Log;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.content.Scope;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaParser.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternFormulaParser {
    private static final int MAXIMUM_TOKENS_TO_PARSE = 1000;
    public static final int PARSER_INPUT_SYNTAX_ERROR = -3;
    public static final int PARSER_NO_INPUT = -4;
    public static final int PARSER_OK = -1;
    public static final int PARSER_STACK_OVERFLOW = -2;
    private static final String TAG = "InternFormulaParser";
    private InternToken currentToken;
    private int currentTokenParseIndex;
    private int errorTokenIndex;
    private List<InternToken> internTokensToParse;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaParser$InternFormulaParserException.class
      classes14.dex
     */
    private class InternFormulaParserException extends Exception {
        private static final long serialVersionUID = 1;

        InternFormulaParserException(String errorMessage) {
            super(errorMessage);
        }
    }

    public InternFormulaParser(List<InternToken> internTokensToParse) {
        this.internTokensToParse = internTokensToParse;
    }

    private void getNextToken() {
        int i = this.currentTokenParseIndex + 1;
        this.currentTokenParseIndex = i;
        this.currentToken = this.internTokensToParse.get(i);
    }

    public int getErrorTokenIndex() {
        return this.errorTokenIndex;
    }

    private FormulaElement findLowerOrEqualPriorityOperatorElement(Operators currentOperator, FormulaElement currentElement) {
        FormulaElement parent = currentElement.getParent();
        boolean z = true;
        while (z) {
            if (parent != null && Operators.getOperatorByValue(parent.getValue()).compareOperatorTo(currentOperator) >= 0) {
                FormulaElement formulaElement = parent;
                parent = parent.getParent();
                currentElement = formulaElement;
            } else {
                parent = currentElement;
                z = false;
            }
        }
        return parent;
    }

    public void handleOperator(String operator, FormulaElement currentElement, FormulaElement newElement) {
        if (currentElement.getParent() == null) {
            new FormulaElement(FormulaElement.ElementType.OPERATOR, operator, null, currentElement, newElement);
            return;
        }
        Operators operatorByValue = Operators.getOperatorByValue(currentElement.getParent().getValue());
        Operators operatorByValue2 = Operators.getOperatorByValue(operator);
        if (operatorByValue.compareOperatorTo(operatorByValue2) >= 0) {
            FormulaElement formulaElementFindLowerOrEqualPriorityOperatorElement = findLowerOrEqualPriorityOperatorElement(operatorByValue2, currentElement);
            if (formulaElementFindLowerOrEqualPriorityOperatorElement.getParent() != null) {
                formulaElementFindLowerOrEqualPriorityOperatorElement.replaceWithSubElement(operator, newElement);
                return;
            } else {
                new FormulaElement(FormulaElement.ElementType.OPERATOR, operator, null, formulaElementFindLowerOrEqualPriorityOperatorElement, newElement);
                return;
            }
        }
        currentElement.replaceWithSubElement(operator, newElement);
    }

    private void addEndOfFileToken() {
        this.internTokensToParse.add(new InternToken(InternTokenType.PARSER_END_OF_FILE));
    }

    private void removeEndOfFileToken() {
        List<InternToken> list = this.internTokensToParse;
        list.remove(list.size() - 1);
    }

    public FormulaElement parseFormula(Scope scope) {
        this.errorTokenIndex = -1;
        this.currentTokenParseIndex = 0;
        List<InternToken> list = this.internTokensToParse;
        FormulaElement formulaElementFormula = null;
        if (list == null || list.size() == 0) {
            this.errorTokenIndex = -4;
            return null;
        }
        if (this.internTokensToParse.size() > 1000) {
            this.errorTokenIndex = 0;
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList(this.internTokensToParse);
            if (InternFormulaUtils.applyBracketCorrection(arrayList)) {
                this.internTokensToParse.clear();
                this.internTokensToParse.addAll(arrayList);
            }
        } catch (EmptyStackException e) {
            Log.d(TAG, "Bracket correction failed.", e);
        }
        addEndOfFileToken();
        this.currentToken = this.internTokensToParse.get(0);
        try {
            formulaElementFormula = formula(scope);
        } catch (InternFormulaParserException unused) {
            this.errorTokenIndex = this.currentTokenParseIndex;
        }
        removeEndOfFileToken();
        return formulaElementFormula;
    }

    private FormulaElement formula(Scope scope) throws InternFormulaParserException {
        FormulaElement formulaElementTermList = termList(scope);
        if (this.currentToken.isEndOfFileToken()) {
            return formulaElementTermList;
        }
        throw new InternFormulaParserException("Parse Error");
    }

    private FormulaElement termList(Scope scope) throws InternFormulaParserException {
        FormulaElement formulaElementTerm = term(scope);
        while (this.currentToken.isOperator() && !this.currentToken.getTokenStringValue().equals(Operators.LOGICAL_NOT.name())) {
            String tokenStringValue = this.currentToken.getTokenStringValue();
            getNextToken();
            FormulaElement formulaElementTerm2 = term(scope);
            handleOperator(tokenStringValue, formulaElementTerm, formulaElementTerm2);
            formulaElementTerm = formulaElementTerm2;
        }
        return formulaElementTerm.getRoot();
    }

    private FormulaElement term(Scope scope) throws InternFormulaParserException {
        FormulaElement formulaElement;
        FormulaElement formulaElement2 = new FormulaElement(FormulaElement.ElementType.NUMBER, null, null);
        if (this.currentToken.isOperator() && this.currentToken.getTokenStringValue().equals(Operators.MINUS.name())) {
            formulaElement = new FormulaElement(FormulaElement.ElementType.NUMBER, null, formulaElement2, null, null);
            formulaElement2.replaceElement(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.MINUS.name(), null, null, formulaElement));
            getNextToken();
        } else if (this.currentToken.isOperator() && this.currentToken.getTokenStringValue().equals(Operators.LOGICAL_NOT.name())) {
            formulaElement = new FormulaElement(FormulaElement.ElementType.NUMBER, null, formulaElement2, null, null);
            formulaElement2.replaceElement(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.LOGICAL_NOT.name(), null, null, formulaElement));
            getNextToken();
        } else {
            formulaElement = formulaElement2;
        }
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[this.currentToken.getInternTokenType().ordinal()]) {
            case 1:
                formulaElement.replaceElement(FormulaElement.ElementType.NUMBER, number());
                return formulaElement2;
            case 2:
                getNextToken();
                formulaElement.replaceElement(new FormulaElement(FormulaElement.ElementType.BRACKET, null, null, null, termList(scope)));
                if (!this.currentToken.isBracketClose()) {
                    throw new InternFormulaParserException("Parse Error");
                }
                getNextToken();
                return formulaElement2;
            case 3:
                formulaElement.replaceElement(function(scope));
                return formulaElement2;
            case 4:
                formulaElement.replaceElement(sensor());
                return formulaElement2;
            case 5:
                formulaElement.replaceElement(userVariable(scope));
                return formulaElement2;
            case 6:
                formulaElement.replaceElement(userList(scope));
                return formulaElement2;
            case 7:
                formulaElement.replaceElement(userDefinedBrickInput(scope));
                return formulaElement2;
            case 8:
                formulaElement.replaceElement(FormulaElement.ElementType.STRING, string());
                return formulaElement2;
            case 9:
                formulaElement.replaceElement(collision());
                return formulaElement2;
            default:
                throw new InternFormulaParserException("Parse Error");
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaParser$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.InternFormulaParser$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType;

        static {
            int[] iArr = new int[InternTokenType.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType = iArr;
            try {
                iArr[InternTokenType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_NAME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.SENSOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_VARIABLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_LIST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.USER_DEFINED_BRICK_INPUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.COLLISION_FORMULA.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private FormulaElement userVariable(Scope scope) throws InternFormulaParserException {
        if (UserDataWrapper.getUserVariable(this.currentToken.getTokenStringValue(), scope) == null) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.USER_VARIABLE, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return formulaElement;
    }

    private FormulaElement collision() {
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.COLLISION_FORMULA, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return formulaElement;
    }

    private FormulaElement userList(Scope scope) throws InternFormulaParserException {
        if (UserDataWrapper.getUserList(this.currentToken.getTokenStringValue(), scope) == null) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.USER_LIST, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return formulaElement;
    }

    private FormulaElement userDefinedBrickInput(Scope scope) throws InternFormulaParserException {
        if (UserDataWrapper.getUserDefinedBrickInput(this.currentToken.getTokenStringValue(), scope.getSequence()) == null) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.USER_DEFINED_BRICK_INPUT, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return formulaElement;
    }

    private FormulaElement sensor() throws InternFormulaParserException {
        if (!Sensors.isSensor(this.currentToken.getTokenStringValue())) {
            throw new InternFormulaParserException("Parse Error");
        }
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.SENSOR, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        return formulaElement;
    }

    private FormulaElement function(Scope scope) throws InternFormulaParserException {
        String tokenStringValue = this.currentToken.getTokenStringValue();
        boolean zIsFunction = Functions.isFunction(tokenStringValue);
        boolean z = CustomFormulaManager.INSTANCE.getFormulaByUniqueName(tokenStringValue) != null;
        if (!zIsFunction && !z) {
            Log.e(TAG, "Parse Error: Unknown function name '" + tokenStringValue + "' at token index " + this.currentTokenParseIndex);
            throw new InternFormulaParserException("Parse Error: Unknown function name '" + tokenStringValue + FormatHelper.QUOTE);
        }
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.FUNCTION, this.currentToken.getTokenStringValue(), null);
        getNextToken();
        if (this.currentToken.isFunctionParameterBracketOpen()) {
            getNextToken();
            formulaElement.setLeftChild(termList(scope));
            if (this.currentToken.isFunctionParameterDelimiter()) {
                getNextToken();
                formulaElement.setRightChild(termList(scope));
                while (this.currentToken.isFunctionParameterDelimiter()) {
                    getNextToken();
                    formulaElement.addAdditionalChild(termList(scope));
                }
            }
            if (!this.currentToken.isFunctionParameterBracketClose()) {
                throw new InternFormulaParserException("Parse Error");
            }
            getNextToken();
        }
        return formulaElement;
    }

    private String number() throws InternFormulaParserException {
        String tokenStringValue = this.currentToken.getTokenStringValue();
        if (!tokenStringValue.matches("-?(\\d)+(\\.(\\d)+)?")) {
            throw new InternFormulaParserException("Parse Error");
        }
        getNextToken();
        return tokenStringValue;
    }

    private String string() {
        String tokenStringValue = this.currentToken.getTokenStringValue();
        getNextToken();
        return tokenStringValue;
    }
}
