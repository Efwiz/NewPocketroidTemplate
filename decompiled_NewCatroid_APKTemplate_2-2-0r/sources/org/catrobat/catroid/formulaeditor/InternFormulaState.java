package org.catrobat.catroid.formulaeditor;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaState.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternFormulaState {
    private int externCursorPosition;
    private List<InternToken> internTokenFormulaList;
    private InternFormulaTokenSelection tokenSelection;

    public InternFormulaState(List<InternToken> internTokenFormulaList, InternFormulaTokenSelection tokenSelection, int externCursorPosition) {
        this.internTokenFormulaList = internTokenFormulaList;
        this.tokenSelection = tokenSelection;
        this.externCursorPosition = externCursorPosition;
    }

    public int hashCode() {
        int size = XKeySym.XK_Arabic_kasratan + this.externCursorPosition;
        InternFormulaTokenSelection internFormulaTokenSelection = this.tokenSelection;
        if (internFormulaTokenSelection != null) {
            size = (size * 41) + internFormulaTokenSelection.hashCode();
        }
        List<InternToken> list = this.internTokenFormulaList;
        if (list != null) {
            size = (size * 41) + list.size();
            for (int i = 0; i < this.internTokenFormulaList.size(); i++) {
                InternToken internToken = this.internTokenFormulaList.get(i);
                size = internToken.getTokenStringValue().hashCode() + (41 * ((size * 41) + internToken.getInternTokenType().hashCode()));
            }
        }
        return size;
    }

    public boolean equals(Object objectToCompare) {
        InternFormulaTokenSelection internFormulaTokenSelection;
        if (objectToCompare instanceof InternFormulaState) {
            InternFormulaState internFormulaState = (InternFormulaState) objectToCompare;
            if (this.externCursorPosition != internFormulaState.externCursorPosition || (((internFormulaTokenSelection = this.tokenSelection) == null && internFormulaState.tokenSelection != null) || !((internFormulaTokenSelection == null || internFormulaTokenSelection.equals(internFormulaState.tokenSelection)) && this.internTokenFormulaList.size() == internFormulaState.internTokenFormulaList.size()))) {
                return false;
            }
            for (int i = 0; i < this.internTokenFormulaList.size(); i++) {
                InternToken internToken = this.internTokenFormulaList.get(i);
                InternToken internToken2 = internFormulaState.internTokenFormulaList.get(i);
                if (internToken.getInternTokenType() != internToken2.getInternTokenType() || !internToken.getTokenStringValue().equals(internToken2.getTokenStringValue())) {
                    return false;
                }
            }
            return true;
        }
        return super.equals(objectToCompare);
    }

    public void setSelection(InternFormulaTokenSelection internFormulaTokenSelection) {
        this.tokenSelection = internFormulaTokenSelection;
    }

    public void setExternCursorPosition(int externCursorPosition) {
        this.externCursorPosition = externCursorPosition;
    }

    public InternFormula createInternFormulaFromState() {
        LinkedList linkedList = new LinkedList();
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next().deepCopy());
        }
        InternFormulaTokenSelection internFormulaTokenSelection = this.tokenSelection;
        return new InternFormula(linkedList, internFormulaTokenSelection != null ? internFormulaTokenSelection.deepCopy() : null, this.externCursorPosition);
    }

    public void updateUserDataTokens(InternTokenType type, String oldName, String newName) {
        for (InternToken internToken : this.internTokenFormulaList) {
            if (internToken.getInternTokenType().equals(type) && internToken.getTokenStringValue().equals(oldName)) {
                internToken.setTokenStringValue(newName);
            }
        }
    }
}
