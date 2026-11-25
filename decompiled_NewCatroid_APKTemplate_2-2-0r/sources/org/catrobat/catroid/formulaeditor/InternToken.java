package org.catrobat.catroid.formulaeditor;

import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.sensing.CollisionDetection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternToken.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternToken {
    private InternTokenType internTokenType;
    private String tokenStringValue;

    public InternToken(InternTokenType internTokenType) {
        this.tokenStringValue = "";
        this.internTokenType = internTokenType;
    }

    public InternToken(InternTokenType internTokenType, String tokenStringValue) {
        this.tokenStringValue = tokenStringValue;
        this.internTokenType = internTokenType;
    }

    public void setTokenStringValue(String tokenString) {
        this.tokenStringValue = tokenString;
    }

    public String getTokenStringValue() {
        return this.tokenStringValue;
    }

    public void updateVariableReferences(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.USER_VARIABLE && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void updateListReferences(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.USER_LIST && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void updateCollisionFormula(String oldName, String newName) {
        if (this.internTokenType == InternTokenType.COLLISION_FORMULA && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public void updateCollisionFormulaToVersion() {
        if (this.internTokenType == InternTokenType.COLLISION_FORMULA) {
            String secondSpriteNameFromCollisionFormulaString = CollisionDetection.getSecondSpriteNameFromCollisionFormulaString(this.tokenStringValue, ProjectManager.getInstance().getCurrentProject());
            if (secondSpriteNameFromCollisionFormulaString != null) {
                this.tokenStringValue = secondSpriteNameFromCollisionFormulaString;
            }
        }
    }

    public void updateSensorTokens(String oldName, String newName) {
        if (isSensor() && this.tokenStringValue.equals(oldName)) {
            this.tokenStringValue = newName;
        }
    }

    public boolean isNumber() {
        return this.internTokenType == InternTokenType.NUMBER;
    }

    public boolean isOperator() {
        return this.internTokenType == InternTokenType.OPERATOR && Operators.isOperator(this.tokenStringValue);
    }

    public boolean isBracketClose() {
        return this.internTokenType == InternTokenType.BRACKET_CLOSE;
    }

    public boolean isFunctionParameterBracketOpen() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN;
    }

    public boolean isFunctionParameterBracketClose() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE;
    }

    public boolean isFunctionParameterDelimiter() {
        return this.internTokenType == InternTokenType.FUNCTION_PARAMETER_DELIMITER;
    }

    public boolean isFunctionName() {
        return this.internTokenType == InternTokenType.FUNCTION_NAME;
    }

    public boolean isSensor() {
        return this.internTokenType == InternTokenType.SENSOR;
    }

    public boolean isEndOfFileToken() {
        return this.internTokenType == InternTokenType.PARSER_END_OF_FILE;
    }

    public boolean isUserVariable() {
        return this.internTokenType == InternTokenType.USER_VARIABLE;
    }

    public boolean isUserList() {
        return this.internTokenType == InternTokenType.USER_LIST;
    }

    public boolean isString() {
        return this.internTokenType == InternTokenType.STRING;
    }

    public void appendToTokenStringValue(String stringToAppend) {
        this.tokenStringValue += stringToAppend;
    }

    public void appendToTokenStringValue(List<InternToken> internTokensToAppend) {
        StringBuilder sb = new StringBuilder(this.tokenStringValue);
        Iterator<InternToken> it = internTokensToAppend.iterator();
        while (it.hasNext()) {
            sb.append(it.next().tokenStringValue);
        }
        this.tokenStringValue = sb.toString();
    }

    public InternTokenType getInternTokenType() {
        return this.internTokenType;
    }

    public InternToken deepCopy() {
        return new InternToken(this.internTokenType, this.tokenStringValue);
    }
}
