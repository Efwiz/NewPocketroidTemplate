package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormula.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternFormula {
    private static final String TAG = "InternFormula";
    public InternToken cursorPositionInternToken;
    private int cursorPositionInternTokenIndex;
    private CursorTokenPosition cursorTokenPosition;
    public int externCursorPosition;
    private String externFormulaString;
    public ExternInternRepresentationMapping externInternRepresentationMapping;
    public InternFormulaTokenSelection internFormulaTokenSelection;
    private List<InternToken> internTokenFormulaList;
    private InternFormulaParser internTokenFormulaParser;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormula$CursorTokenPosition.class
      classes14.dex
     */
    public enum CursorTokenPosition {
        LEFT,
        MIDDLE,
        RIGHT
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormula$CursorTokenPropertiesAfterModification.class
      classes14.dex
     */
    public enum CursorTokenPropertiesAfterModification {
        LEFT,
        RIGHT,
        SELECT,
        DO_NOT_MODIFY
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormula$TokenSelectionType.class
      classes14.dex
     */
    public enum TokenSelectionType {
        USER_SELECTION,
        PARSER_ERROR_SELECTION
    }

    public InternFormula(List<InternToken> internTokenList) {
        this.internTokenFormulaList = internTokenList;
        this.externFormulaString = null;
        this.externInternRepresentationMapping = new ExternInternRepresentationMapping();
        this.internFormulaTokenSelection = null;
        this.externCursorPosition = 0;
        this.cursorPositionInternTokenIndex = 0;
    }

    public InternFormula(List<InternToken> internTokenList, InternFormulaTokenSelection internFormulaTokenSelection, int externCursorPosition) {
        this.internTokenFormulaList = internTokenList;
        this.externFormulaString = null;
        this.externInternRepresentationMapping = new ExternInternRepresentationMapping();
        this.internFormulaTokenSelection = internFormulaTokenSelection;
        this.externCursorPosition = externCursorPosition;
        updateInternCursorPosition();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x007e  */
    public void insertTokens(Context context, List<InternToken> tokensToInsert) {
        int size;
        int i;
        int i2;
        if (tokensToInsert == null || tokensToInsert.isEmpty()) {
            Log.w(TAG, "insertTokens: tokensToInsert is null or empty.");
            return;
        }
        if (isTokenSelected()) {
            int startIndex = this.internFormulaTokenSelection.getStartIndex();
            for (int endIndex = this.internFormulaTokenSelection.getEndIndex(); endIndex >= startIndex; endIndex--) {
                if (endIndex < this.internTokenFormulaList.size()) {
                    this.internTokenFormulaList.remove(endIndex);
                } else {
                    Log.w(TAG, "insertTokens: Attempted to remove token at index " + endIndex + " but list size is " + this.internTokenFormulaList.size());
                }
            }
            this.internTokenFormulaList.addAll(startIndex, tokensToInsert);
            int size2 = (startIndex + tokensToInsert.size()) - 1;
            this.cursorPositionInternTokenIndex = size2;
            this.cursorPositionInternToken = this.internTokenFormulaList.get(size2);
            this.internFormulaTokenSelection = null;
        } else {
            if (this.cursorPositionInternToken == null && this.internTokenFormulaList.isEmpty()) {
                size = 0;
            } else if (this.cursorPositionInternToken == null && !this.internTokenFormulaList.isEmpty()) {
                if (this.externCursorPosition != 0 || this.internTokenFormulaList.size() <= 0) {
                    int i3 = this.cursorPositionInternTokenIndex;
                    if (i3 >= 0 && i3 < this.internTokenFormulaList.size()) {
                        if (this.cursorTokenPosition != CursorTokenPosition.LEFT && this.cursorTokenPosition == CursorTokenPosition.RIGHT) {
                            i = this.cursorPositionInternTokenIndex;
                            size = i + 1;
                        } else {
                            size = this.cursorPositionInternTokenIndex;
                        }
                    } else if ((this.cursorPositionInternTokenIndex == -1 && this.internTokenFormulaList.isEmpty()) || (this.cursorPositionInternTokenIndex == -1 && !this.internTokenFormulaList.isEmpty() && this.externCursorPosition == 0)) {
                        size = 0;
                    } else {
                        size = this.internTokenFormulaList.size();
                    }
                } else {
                    size = 0;
                }
            } else {
                int i4 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[this.cursorTokenPosition.ordinal()];
                if (i4 == 1 || i4 == 2) {
                    size = this.cursorPositionInternTokenIndex;
                } else if (i4 == 3) {
                    i = this.cursorPositionInternTokenIndex;
                    size = i + 1;
                } else {
                    Log.w(TAG, "insertTokens: Unexpected cursorTokenPosition: " + this.cursorTokenPosition);
                    size = this.internTokenFormulaList.size();
                }
            }
            if (size > this.internTokenFormulaList.size()) {
                size = this.internTokenFormulaList.size();
            }
            if (size < 0) {
                size = 0;
            }
            this.internTokenFormulaList.addAll(size, tokensToInsert);
            int size3 = (size + tokensToInsert.size()) - 1;
            this.cursorPositionInternTokenIndex = size3;
            if (size3 < this.internTokenFormulaList.size() && (i2 = this.cursorPositionInternTokenIndex) >= 0) {
                this.cursorPositionInternToken = this.internTokenFormulaList.get(i2);
            } else if (!this.internTokenFormulaList.isEmpty()) {
                int size4 = this.internTokenFormulaList.size() - 1;
                this.cursorPositionInternTokenIndex = size4;
                this.cursorPositionInternToken = this.internTokenFormulaList.get(size4);
            } else {
                this.cursorPositionInternToken = null;
                this.cursorPositionInternTokenIndex = -1;
            }
        }
        generateExternFormulaStringAndInternExternMapping(context);
        if (!tokensToInsert.isEmpty()) {
            setExternCursorPositionRightTo(this.cursorPositionInternTokenIndex);
        } else if (!this.internTokenFormulaList.isEmpty()) {
            setExternCursorPositionLeftTo(0);
        } else {
            this.externCursorPosition = 0;
        }
        updateInternCursorPosition();
        Log.d(TAG, "Tokens inserted. New state size: " + this.internTokenFormulaList.size() + ", new extern cursor: " + this.externCursorPosition);
    }

    public void setCursorAndSelection(int externCursorPosition, boolean isSelected) {
        this.externCursorPosition = externCursorPosition;
        updateInternCursorPosition();
        this.internFormulaTokenSelection = null;
        if (!isSelected) {
            if (this.externInternRepresentationMapping.getInternTokenByExternIndex(externCursorPosition) == Integer.MIN_VALUE) {
                return;
            }
            InternToken firstLeftInternToken = getFirstLeftInternToken(externCursorPosition - 1);
            InternToken internToken = this.cursorPositionInternToken;
            if (firstLeftInternToken != internToken && !internToken.isFunctionParameterBracketOpen()) {
                return;
            }
            if (!this.cursorPositionInternToken.isFunctionName() && ((!this.cursorPositionInternToken.isFunctionParameterBracketOpen() || this.cursorTokenPosition != CursorTokenPosition.LEFT) && !this.cursorPositionInternToken.isSensor() && !this.cursorPositionInternToken.isUserVariable() && !this.cursorPositionInternToken.isUserList() && !this.cursorPositionInternToken.isString())) {
                return;
            }
        }
        selectCursorPositionInternToken(TokenSelectionType.USER_SELECTION);
    }

    public void handleKeyInput(int resourceId, Context context, String name2) {
        List<InternToken> listCreateInternTokenListByResourceId = new InternFormulaKeyboardAdapter().createInternTokenListByResourceId(resourceId, name2);
        CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        if (resourceId == R.id.formula_editor_keyboard_delete) {
            cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = handleDeletion();
        } else if (isTokenSelected()) {
            cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = replaceSelection(listCreateInternTokenListByResourceId);
        } else if (this.cursorTokenPosition == null) {
            cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertRightToCurrentToken(listCreateInternTokenListByResourceId);
        } else {
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[this.cursorTokenPosition.ordinal()];
            if (i == 1) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertLeftToCurrentToken(listCreateInternTokenListByResourceId);
            } else if (i == 2) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = replaceCursorPositionInternTokenByTokenList(listCreateInternTokenListByResourceId);
            } else if (i == 3) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertRightToCurrentToken(listCreateInternTokenListByResourceId);
            }
        }
        generateExternFormulaStringAndInternExternMapping(context);
        updateExternCursorPosition(cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken);
        updateInternCursorPosition();
    }

    public void updateVariableReferences(String oldName, String newName, Context context) {
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            it.next().updateVariableReferences(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateListReferences(String oldName, String newName, Context context) {
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            it.next().updateListReferences(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateCollisionFormula(String oldName, String newName, Context context) {
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            it.next().updateCollisionFormula(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateSensorTokens(String oldName, String newName, Context context) {
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            it.next().updateSensorTokens(oldName, newName);
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateCollisionFormulaToVersion(Context context) {
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            it.next().updateCollisionFormulaToVersion();
        }
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public void updateInternCursorPosition() {
        int internTokenByExternIndex = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition);
        int internTokenByExternIndex2 = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition - 1);
        int internTokenByExternIndex3 = this.externInternRepresentationMapping.getInternTokenByExternIndex(this.externCursorPosition - 2);
        if (internTokenByExternIndex != Integer.MIN_VALUE) {
            if (internTokenByExternIndex2 != Integer.MIN_VALUE && internTokenByExternIndex == internTokenByExternIndex2) {
                this.cursorTokenPosition = CursorTokenPosition.MIDDLE;
            } else {
                this.cursorTokenPosition = CursorTokenPosition.LEFT;
            }
        } else if (internTokenByExternIndex2 != Integer.MIN_VALUE) {
            this.cursorTokenPosition = CursorTokenPosition.RIGHT;
        } else if (internTokenByExternIndex3 != Integer.MIN_VALUE) {
            this.cursorTokenPosition = CursorTokenPosition.RIGHT;
            internTokenByExternIndex2 = internTokenByExternIndex3;
        } else {
            this.cursorTokenPosition = null;
            this.cursorPositionInternToken = null;
            return;
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[this.cursorTokenPosition.ordinal()];
        if (i == 1) {
            this.cursorPositionInternToken = this.internTokenFormulaList.get(internTokenByExternIndex);
            this.cursorPositionInternTokenIndex = internTokenByExternIndex;
        } else if (i == 2) {
            this.cursorPositionInternToken = this.internTokenFormulaList.get(internTokenByExternIndex);
            this.cursorPositionInternTokenIndex = internTokenByExternIndex;
        } else {
            if (i != 3) {
                return;
            }
            this.cursorPositionInternToken = this.internTokenFormulaList.get(internTokenByExternIndex2);
            this.cursorPositionInternTokenIndex = internTokenByExternIndex2;
        }
    }

    private void updateExternCursorPosition(CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterInput) {
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPropertiesAfterModification[cursorTokenPropertiesAfterInput.ordinal()];
        if (i == 1) {
            setExternCursorPositionLeftTo(this.cursorPositionInternTokenIndex);
        } else {
            if (i != 2) {
                return;
            }
            setExternCursorPositionRightTo(this.cursorPositionInternTokenIndex);
        }
    }

    private CursorTokenPropertiesAfterModification replaceSelection(List<InternToken> tokenListToInsert) {
        if (InternFormulaUtils.isPeriodToken(tokenListToInsert)) {
            tokenListToInsert = new LinkedList<>();
            tokenListToInsert.add(new InternToken(InternTokenType.NUMBER, "0."));
        }
        int startIndex = this.internFormulaTokenSelection.getStartIndex();
        int endIndex = this.internFormulaTokenSelection.getEndIndex();
        if (startIndex > endIndex || startIndex < 0 || endIndex < 0) {
            this.internFormulaTokenSelection = null;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i <= endIndex - startIndex; i++) {
            linkedList.add(this.internTokenFormulaList.get(startIndex + i));
        }
        if (InternFormulaUtils.isFunction(linkedList)) {
            this.cursorPositionInternToken = (InternToken) linkedList.get(0);
            this.cursorPositionInternTokenIndex = startIndex;
            return replaceCursorPositionInternTokenByTokenList(tokenListToInsert);
        }
        replaceInternTokens(tokenListToInsert, startIndex, endIndex);
        return setCursorPositionAndSelectionAfterInput(startIndex);
    }

    public void deleteSelection(Context context) {
        updateExternCursorPosition(handleDeletion());
        generateExternFormulaStringAndInternExternMapping(context);
        updateInternCursorPosition();
    }

    private void deleteInternTokens(int deleteIndexStart, int deleteIndexEnd) {
        replaceInternTokens(new LinkedList(), deleteIndexStart, deleteIndexEnd);
    }

    private void replaceInternTokens(List<InternToken> tokenListToInsert, int replaceIndexStart, int replaceIndexEnd) {
        for (int i = replaceIndexEnd - replaceIndexStart; i >= 0; i--) {
            this.internTokenFormulaList.remove(replaceIndexStart);
        }
        this.internTokenFormulaList.addAll(replaceIndexStart, tokenListToInsert);
    }

    private CursorTokenPropertiesAfterModification handleDeletion() {
        CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterModification = CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection != null) {
            deleteInternTokens(internFormulaTokenSelection.getStartIndex(), this.internFormulaTokenSelection.getEndIndex());
            this.cursorPositionInternTokenIndex = this.internFormulaTokenSelection.getStartIndex();
            this.cursorPositionInternToken = null;
            this.internFormulaTokenSelection = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[this.cursorTokenPosition.ordinal()];
        if (i == 1) {
            InternToken firstLeftInternToken = getFirstLeftInternToken(this.externCursorPosition - 1);
            if (firstLeftInternToken == null) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            if (firstLeftInternToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETER_DELIMITER) {
                setExternCursorPositionLeftTo(this.internTokenFormulaList.indexOf(firstLeftInternToken));
                return cursorTokenPropertiesAfterModification;
            }
            return deleteInternTokenByIndex(this.internTokenFormulaList.indexOf(firstLeftInternToken));
        }
        if (i == 2) {
            return deleteInternTokenByIndex(this.cursorPositionInternTokenIndex);
        }
        if (i != 3) {
            return cursorTokenPropertiesAfterModification;
        }
        InternToken firstLeftInternToken2 = getFirstLeftInternToken(this.externCursorPosition);
        if (firstLeftInternToken2.getInternTokenType() == InternTokenType.FUNCTION_PARAMETER_DELIMITER) {
            setExternCursorPositionLeftTo(this.internTokenFormulaList.indexOf(firstLeftInternToken2));
            return cursorTokenPropertiesAfterModification;
        }
        return deleteInternTokenByIndex(this.cursorPositionInternTokenIndex);
    }

    private CursorTokenPropertiesAfterModification deleteInternTokenByIndex(int internTokenIndex) {
        InternToken internToken = this.internTokenFormulaList.get(internTokenIndex);
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
        if (i == 1) {
            int externTokenStartOffset = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, internTokenIndex);
            if (externTokenStartOffset == -1) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            if (InternFormulaUtils.deleteNumberByOffset(internToken, externTokenStartOffset) == null) {
                this.internTokenFormulaList.remove(internTokenIndex);
                this.cursorPositionInternTokenIndex = internTokenIndex;
                this.cursorPositionInternToken = null;
                return CursorTokenPropertiesAfterModification.LEFT;
            }
            this.externCursorPosition--;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (i == 2) {
            List<InternToken> functionByName = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, internTokenIndex);
            if (functionByName == null || functionByName.size() == 0) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            deleteInternTokens(internTokenIndex, this.internTokenFormulaList.indexOf(functionByName.get(functionByName.size() - 1)));
            setExternCursorPositionLeftTo(internTokenIndex);
            this.cursorPositionInternTokenIndex = internTokenIndex;
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        if (i == 3) {
            List<InternToken> functionByFunctionBracketOpen = InternFormulaUtils.getFunctionByFunctionBracketOpen(this.internTokenFormulaList, internTokenIndex);
            if (functionByFunctionBracketOpen == null || functionByFunctionBracketOpen.size() == 0) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            int size = functionByFunctionBracketOpen.size() - 1;
            int iIndexOf = this.internTokenFormulaList.indexOf(functionByFunctionBracketOpen.get(0));
            deleteInternTokens(iIndexOf, this.internTokenFormulaList.indexOf(functionByFunctionBracketOpen.get(size)));
            this.cursorPositionInternTokenIndex = iIndexOf;
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        if (i == 4) {
            List<InternToken> functionByFunctionBracketClose = InternFormulaUtils.getFunctionByFunctionBracketClose(this.internTokenFormulaList, internTokenIndex);
            if (functionByFunctionBracketClose == null || functionByFunctionBracketClose.size() == 0) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            int size2 = functionByFunctionBracketClose.size() - 1;
            int iIndexOf2 = this.internTokenFormulaList.indexOf(functionByFunctionBracketClose.get(0));
            deleteInternTokens(iIndexOf2, this.internTokenFormulaList.indexOf(functionByFunctionBracketClose.get(size2)));
            this.cursorPositionInternTokenIndex = iIndexOf2;
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        if (i == 5) {
            List<InternToken> functionByParameterDelimiter = InternFormulaUtils.getFunctionByParameterDelimiter(this.internTokenFormulaList, internTokenIndex);
            if (functionByParameterDelimiter == null || functionByParameterDelimiter.size() == 0) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            int size3 = functionByParameterDelimiter.size() - 1;
            int iIndexOf3 = this.internTokenFormulaList.indexOf(functionByParameterDelimiter.get(0));
            deleteInternTokens(iIndexOf3, this.internTokenFormulaList.indexOf(functionByParameterDelimiter.get(size3)));
            this.cursorPositionInternTokenIndex = iIndexOf3;
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.LEFT;
        }
        deleteInternTokens(internTokenIndex, internTokenIndex);
        this.cursorPositionInternTokenIndex = internTokenIndex;
        this.cursorPositionInternToken = null;
        return CursorTokenPropertiesAfterModification.LEFT;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormula$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.InternFormula$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPropertiesAfterModification;

        static {
            int[] iArr = new int[InternTokenType.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType = iArr;
            try {
                iArr[InternTokenType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_NAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETER_DELIMITER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_OPEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_CLOSE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[CursorTokenPropertiesAfterModification.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPropertiesAfterModification = iArr2;
            try {
                iArr2[CursorTokenPropertiesAfterModification.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPropertiesAfterModification[CursorTokenPropertiesAfterModification.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr3 = new int[CursorTokenPosition.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition = iArr3;
            try {
                iArr3[CursorTokenPosition.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[CursorTokenPosition.MIDDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[CursorTokenPosition.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public void setExternCursorPositionLeftTo(int internTokenIndex) {
        if (this.internTokenFormulaList.size() < 1) {
            this.externCursorPosition = 1;
            return;
        }
        if (internTokenIndex >= this.internTokenFormulaList.size()) {
            setExternCursorPositionRightTo(this.internTokenFormulaList.size() - 1);
            return;
        }
        int externTokenStartIndex = this.externInternRepresentationMapping.getExternTokenStartIndex(internTokenIndex);
        if (externTokenStartIndex == Integer.MIN_VALUE) {
            return;
        }
        this.externCursorPosition = externTokenStartIndex;
        this.cursorTokenPosition = CursorTokenPosition.LEFT;
    }

    public void setExternCursorPositionRightTo(int internTokenIndex) {
        if (this.internTokenFormulaList.size() < 1) {
            return;
        }
        if (internTokenIndex >= this.internTokenFormulaList.size()) {
            internTokenIndex = this.internTokenFormulaList.size() - 1;
        }
        int externTokenEndIndex = this.externInternRepresentationMapping.getExternTokenEndIndex(internTokenIndex);
        if (externTokenEndIndex == Integer.MIN_VALUE) {
            return;
        }
        this.externCursorPosition = externTokenEndIndex;
        this.cursorTokenPosition = CursorTokenPosition.RIGHT;
    }

    public void generateExternFormulaStringAndInternExternMapping(Context context) {
        InternToExternGenerator internToExternGenerator = new InternToExternGenerator(context);
        internToExternGenerator.generateExternStringAndMapping(this.internTokenFormulaList);
        this.externFormulaString = internToExternGenerator.getGeneratedExternFormulaString();
        this.externInternRepresentationMapping = internToExternGenerator.getGeneratedExternInternRepresentationMapping();
    }

    public String trimExternFormulaString(Context context) {
        InternToExternGenerator internToExternGenerator = new InternToExternGenerator(context);
        internToExternGenerator.trimExternString(this.internTokenFormulaList);
        this.externFormulaString = internToExternGenerator.getGeneratedExternFormulaString();
        this.externInternRepresentationMapping = internToExternGenerator.getGeneratedExternInternRepresentationMapping();
        return this.externFormulaString;
    }

    public void selectCursorPositionInternToken(TokenSelectionType internTokenSelectionType) {
        this.internFormulaTokenSelection = null;
        if (this.cursorPositionInternToken == null) {
        }
        switch (this.cursorPositionInternToken.getInternTokenType()) {
            case FUNCTION_NAME:
                List<InternToken> functionByName = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (functionByName != null && functionByName.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf(functionByName.get(functionByName.size() - 1)));
                    break;
                }
                break;
            case FUNCTION_PARAMETERS_BRACKET_OPEN:
                List<InternToken> functionByFunctionBracketOpen = InternFormulaUtils.getFunctionByFunctionBracketOpen(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (functionByFunctionBracketOpen != null && functionByFunctionBracketOpen.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionByFunctionBracketOpen.get(0)), this.internTokenFormulaList.indexOf(functionByFunctionBracketOpen.get(functionByFunctionBracketOpen.size() - 1)));
                    break;
                }
                break;
            case FUNCTION_PARAMETERS_BRACKET_CLOSE:
                List<InternToken> functionByFunctionBracketClose = InternFormulaUtils.getFunctionByFunctionBracketClose(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (functionByFunctionBracketClose != null && functionByFunctionBracketClose.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionByFunctionBracketClose.get(0)), this.internTokenFormulaList.indexOf(functionByFunctionBracketClose.get(functionByFunctionBracketClose.size() - 1)));
                    break;
                }
                break;
            case FUNCTION_PARAMETER_DELIMITER:
                List<InternToken> functionByParameterDelimiter = InternFormulaUtils.getFunctionByParameterDelimiter(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (functionByParameterDelimiter != null && functionByParameterDelimiter.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(functionByParameterDelimiter.get(0)), this.internTokenFormulaList.indexOf(functionByParameterDelimiter.get(functionByParameterDelimiter.size() - 1)));
                    break;
                }
                break;
            case BRACKET_OPEN:
                List<InternToken> listGenerateTokenListByBracketOpen = InternFormulaUtils.generateTokenListByBracketOpen(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (listGenerateTokenListByBracketOpen != null && listGenerateTokenListByBracketOpen.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf(listGenerateTokenListByBracketOpen.get(listGenerateTokenListByBracketOpen.size() - 1)));
                    break;
                }
                break;
            case BRACKET_CLOSE:
                List<InternToken> listGenerateTokenListByBracketClose = InternFormulaUtils.generateTokenListByBracketClose(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
                if (listGenerateTokenListByBracketClose != null && listGenerateTokenListByBracketClose.size() != 0) {
                    this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, this.internTokenFormulaList.indexOf(listGenerateTokenListByBracketClose.get(0)), this.internTokenFormulaList.indexOf(listGenerateTokenListByBracketClose.get(listGenerateTokenListByBracketClose.size() - 1)));
                    break;
                }
                break;
            default:
                int i = this.cursorPositionInternTokenIndex;
                this.internFormulaTokenSelection = new InternFormulaTokenSelection(internTokenSelectionType, i, i);
                break;
        }
    }

    private CursorTokenPropertiesAfterModification insertLeftToCurrentToken(List<InternToken> internTokensToInsert) {
        int i = this.cursorPositionInternTokenIndex;
        InternToken internToken = i > 0 ? this.internTokenFormulaList.get(i - 1) : null;
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, 0, internTokensToInsert.get(0).getTokenStringValue());
            this.externCursorPosition++;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, 0, "0.");
            this.externCursorPosition += 2;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (internToken != null && internToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            internToken.appendToTokenStringValue(internTokensToInsert);
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (internToken != null && internToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (internToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            internToken.appendToTokenStringValue(".");
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            return CursorTokenPropertiesAfterModification.RIGHT;
        }
        this.internTokenFormulaList.addAll(this.cursorPositionInternTokenIndex, internTokensToInsert);
        return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
    }

    private CursorTokenPropertiesAfterModification insertRightToCurrentToken(List<InternToken> internTokensToInsert) {
        InternToken internToken = this.cursorPositionInternToken;
        if (internToken == null) {
            if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
                internTokensToInsert = new LinkedList<>();
                internTokensToInsert.add(new InternToken(InternTokenType.NUMBER, "0."));
            }
            this.internTokenFormulaList.addAll(0, internTokensToInsert);
            return setCursorPositionAndSelectionAfterInput(0);
        }
        if (internToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToInsert)) {
            this.cursorPositionInternToken.appendToTokenStringValue(internTokensToInsert);
            return CursorTokenPropertiesAfterModification.RIGHT;
        }
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            if (!this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                this.cursorPositionInternToken.appendToTokenStringValue(".");
                return CursorTokenPropertiesAfterModification.RIGHT;
            }
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (InternFormulaUtils.isPeriodToken(internTokensToInsert)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex + 1, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            this.cursorPositionInternTokenIndex++;
            return CursorTokenPropertiesAfterModification.RIGHT;
        }
        this.internTokenFormulaList.addAll(this.cursorPositionInternTokenIndex + 1, internTokensToInsert);
        return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex + 1);
    }

    private CursorTokenPropertiesAfterModification setCursorPositionAndSelectionAfterInput(int insertedInternTokenIndex) {
        if (this.internTokenFormulaList.isEmpty()) {
            return CursorTokenPropertiesAfterModification.RIGHT;
        }
        if (this.internTokenFormulaList.get(insertedInternTokenIndex).getInternTokenType() == InternTokenType.FUNCTION_NAME) {
            List<InternToken> functionByName = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, insertedInternTokenIndex);
            if (functionByName.size() >= 4) {
                List<List<InternToken>> functionParameterInternTokensAsLists = InternFormulaUtils.getFunctionParameterInternTokensAsLists(functionByName);
                List<InternToken> list = functionParameterInternTokensAsLists.get(0);
                String tokenStringValue = functionByName.get(0).getTokenStringValue();
                if (list.isEmpty()) {
                    this.internFormulaTokenSelection = null;
                    this.cursorPositionInternTokenIndex = insertedInternTokenIndex + 1;
                } else {
                    if (userListNotFirstParameter(tokenStringValue, list.get(0))) {
                        list = functionParameterInternTokensAsLists.get(1);
                        insertedInternTokenIndex += 2;
                    }
                    InternFormulaTokenSelection internFormulaTokenSelection = new InternFormulaTokenSelection(TokenSelectionType.USER_SELECTION, insertedInternTokenIndex + 2, insertedInternTokenIndex + list.size() + 1);
                    this.internFormulaTokenSelection = internFormulaTokenSelection;
                    this.cursorPositionInternTokenIndex = internFormulaTokenSelection.getEndIndex();
                }
            } else {
                this.cursorPositionInternTokenIndex = (insertedInternTokenIndex + functionByName.size()) - 1;
                this.internFormulaTokenSelection = null;
            }
        } else {
            this.cursorPositionInternTokenIndex = insertedInternTokenIndex;
            this.internFormulaTokenSelection = null;
        }
        this.cursorPositionInternToken = null;
        return CursorTokenPropertiesAfterModification.RIGHT;
    }

    private boolean userListNotFirstParameter(String functionName, InternToken functionFirstParameter) {
        return (functionName.equals(Functions.LIST_ITEM.name()) || functionName.equals(Functions.INDEX_OF_ITEM.name())) && !functionFirstParameter.isUserList();
    }

    public CursorTokenPropertiesAfterModification replaceCursorPositionInternTokenByTokenList(List<InternToken> internTokensToReplaceWith) {
        Log.i(TAG, "replaceCursorPositionInternTokenByTokenList:enter");
        if (this.cursorPositionInternToken.isNumber() && internTokensToReplaceWith.size() == 1 && internTokensToReplaceWith.get(0).isOperator()) {
            List<InternToken> listInsertOperatorToNumberToken = InternFormulaUtils.insertOperatorToNumberToken(this.cursorPositionInternToken, this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex), internTokensToReplaceWith.get(0));
            int i = this.cursorPositionInternTokenIndex;
            replaceInternTokens(listInsertOperatorToNumberToken, i, i);
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        }
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isNumberToken(internTokensToReplaceWith)) {
            InternToken internToken = internTokensToReplaceWith.get(0);
            int externTokenStartOffset = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex);
            if (externTokenStartOffset == -1) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, externTokenStartOffset, internToken.getTokenStringValue());
            this.externCursorPosition++;
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (this.cursorPositionInternToken.isNumber() && InternFormulaUtils.isPeriodToken(internTokensToReplaceWith)) {
            if (this.cursorPositionInternToken.getTokenStringValue().contains(".")) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            int externTokenStartOffset2 = this.externInternRepresentationMapping.getExternTokenStartOffset(this.externCursorPosition, this.cursorPositionInternTokenIndex);
            if (externTokenStartOffset2 != -1) {
                InternFormulaUtils.insertIntoNumberToken(this.cursorPositionInternToken, externTokenStartOffset2, ".");
                this.externCursorPosition++;
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        }
        if (this.cursorPositionInternToken.isFunctionName()) {
            List<InternToken> functionByName = InternFormulaUtils.getFunctionByName(this.internTokenFormulaList, this.cursorPositionInternTokenIndex);
            if (functionByName == null) {
                return CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
            }
            replaceInternTokens(InternFormulaUtils.replaceFunctionByTokens(functionByName, internTokensToReplaceWith), this.cursorPositionInternTokenIndex, this.internTokenFormulaList.indexOf(functionByName.get(functionByName.size() - 1)));
            return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
        }
        if (InternFormulaUtils.isPeriodToken(internTokensToReplaceWith)) {
            this.internTokenFormulaList.add(this.cursorPositionInternTokenIndex + 1, new InternToken(InternTokenType.NUMBER, "0."));
            this.cursorPositionInternToken = null;
            this.cursorPositionInternTokenIndex++;
            return CursorTokenPropertiesAfterModification.RIGHT;
        }
        int i2 = this.cursorPositionInternTokenIndex;
        replaceInternTokens(internTokensToReplaceWith, i2, i2);
        return setCursorPositionAndSelectionAfterInput(this.cursorPositionInternTokenIndex);
    }

    public InternToken getFirstLeftInternToken(int externIndex) {
        while (externIndex >= 0) {
            if (this.externInternRepresentationMapping.getInternTokenByExternIndex(externIndex) != Integer.MIN_VALUE) {
                return this.internTokenFormulaList.get(this.externInternRepresentationMapping.getInternTokenByExternIndex(externIndex));
            }
            externIndex--;
        }
        return null;
    }

    public int getExternCursorPosition() {
        return this.externCursorPosition;
    }

    public InternFormulaParser getInternFormulaParser() {
        InternFormulaParser internFormulaParser = new InternFormulaParser(this.internTokenFormulaList);
        this.internTokenFormulaParser = internFormulaParser;
        return internFormulaParser;
    }

    public void selectParseErrorTokenAndSetCursor() {
        int errorTokenIndex;
        if (this.internTokenFormulaParser == null || this.internTokenFormulaList.size() == 0 || (errorTokenIndex = this.internTokenFormulaParser.getErrorTokenIndex()) < 0) {
            return;
        }
        if (errorTokenIndex >= this.internTokenFormulaList.size()) {
            errorTokenIndex = this.internTokenFormulaList.size() - 1;
        }
        setExternCursorPositionRightTo(errorTokenIndex);
        this.cursorPositionInternTokenIndex = errorTokenIndex;
        this.cursorPositionInternToken = this.internTokenFormulaList.get(errorTokenIndex);
        selectCursorPositionInternToken(TokenSelectionType.PARSER_ERROR_SELECTION);
    }

    public TokenSelectionType getExternSelectionType() {
        if (isTokenSelected()) {
            return this.internFormulaTokenSelection.getTokenSelectionType();
        }
        return null;
    }

    public void selectWholeFormula() {
        if (this.internTokenFormulaList.size() == 0) {
            return;
        }
        this.internFormulaTokenSelection = new InternFormulaTokenSelection(TokenSelectionType.USER_SELECTION, 0, this.internTokenFormulaList.size() - 1);
    }

    public InternFormulaState getInternFormulaState() {
        LinkedList linkedList = new LinkedList();
        Iterator<InternToken> it = this.internTokenFormulaList.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next().deepCopy());
        }
        return new InternFormulaState(linkedList, isTokenSelected() ? this.internFormulaTokenSelection.deepCopy() : null, this.externCursorPosition);
    }

    public InternFormulaTokenSelection getSelection() {
        return this.internFormulaTokenSelection;
    }

    public int getExternSelectionStartIndex() {
        int externTokenStartIndex;
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection == null || (externTokenStartIndex = this.externInternRepresentationMapping.getExternTokenStartIndex(internFormulaTokenSelection.getStartIndex())) == Integer.MIN_VALUE) {
            return -1;
        }
        return externTokenStartIndex;
    }

    public int getExternSelectionEndIndex() {
        int externTokenEndIndex;
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection == null || (externTokenEndIndex = this.externInternRepresentationMapping.getExternTokenEndIndex(internFormulaTokenSelection.getEndIndex())) == Integer.MIN_VALUE) {
            return -1;
        }
        return externTokenEndIndex;
    }

    public void addTokens(Context context, List<InternToken> tokens) {
        CursorTokenPropertiesAfterModification cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = CursorTokenPropertiesAfterModification.DO_NOT_MODIFY;
        if (isTokenSelected()) {
            int startIndex = this.internFormulaTokenSelection.getStartIndex();
            replaceInternTokens(tokens, startIndex, this.internFormulaTokenSelection.getEndIndex());
            cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = setCursorPositionAndSelectionAfterInput(startIndex);
        } else if (this.cursorTokenPosition == null) {
            cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertRightToCurrentToken(tokens);
        } else {
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternFormula$CursorTokenPosition[this.cursorTokenPosition.ordinal()];
            if (i == 1) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertLeftToCurrentToken(tokens);
            } else if (i == 2) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = replaceCursorPositionInternTokenByTokenList(tokens);
            } else if (i == 3) {
                cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken = insertRightToCurrentToken(tokens);
            }
        }
        generateExternFormulaStringAndInternExternMapping(context);
        updateExternCursorPosition(cursorTokenPropertiesAfterModificationInsertLeftToCurrentToken);
        updateInternCursorPosition();
    }

    public List<InternToken> getSelectedTokenForCopy() {
        InternFormulaTokenSelection internFormulaTokenSelection;
        ArrayList arrayList = new ArrayList();
        if (this.internTokenFormulaList == null || (internFormulaTokenSelection = this.internFormulaTokenSelection) == null) {
            return null;
        }
        for (int startIndex = internFormulaTokenSelection.getStartIndex(); startIndex <= this.internFormulaTokenSelection.getEndIndex(); startIndex++) {
            arrayList.add(this.internTokenFormulaList.get(startIndex));
        }
        return arrayList;
    }

    private InternToken getSelectedToken() {
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection != null && internFormulaTokenSelection.getTokenSelectionType() == TokenSelectionType.USER_SELECTION) {
            int i = 0;
            for (InternToken internToken : this.internTokenFormulaList) {
                if (internToken.getInternTokenType() == InternTokenType.STRING && this.internFormulaTokenSelection.getStartIndex() == i) {
                    return internToken;
                }
                i++;
            }
        }
        return null;
    }

    public boolean isSelectedTokenFirstParamOfRegularExpression() {
        int startIndex;
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        return internFormulaTokenSelection != null && (startIndex = internFormulaTokenSelection.getStartIndex()) >= 2 && isSelectedTokenTypeString(startIndex) && isTokenBeforeSelectedTypeBracketOpen(startIndex) && isTwoTokensBeforeSelectedAFunctionAndNamedRegex(startIndex);
    }

    private boolean isSelectedTokenTypeString(int index) {
        InternTokenType internTokenType = InternTokenType.FUNCTION_NAME;
        InternTokenType internTokenType2 = InternTokenType.STRING;
        InternTokenType internTokenType3 = this.internTokenFormulaList.get(index).getInternTokenType();
        return internTokenType3 == internTokenType2 || internTokenType3 == internTokenType;
    }

    private boolean isTokenBeforeSelectedTypeBracketOpen(int index) {
        return this.internTokenFormulaList.get(index - 1).getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN;
    }

    private boolean isTwoTokensBeforeSelectedAFunctionAndNamedRegex(int index) {
        InternTokenType internTokenType = InternTokenType.FUNCTION_NAME;
        String strName = Functions.REGEX.name();
        InternToken internToken = this.internTokenFormulaList.get(index - 2);
        return internToken.getInternTokenType() == internTokenType && internToken.getTokenStringValue().equals(strName);
    }

    public int getIndexOfInternTokenSelection() {
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection != null) {
            return internFormulaTokenSelection.getStartIndex();
        }
        return -1;
    }

    public void setSelectionToFirstParamOfRegularExpressionAtInternalIndex(int indexOfRegularExpression) {
        if (indexOfRegularExpression < 0) {
            return;
        }
        this.cursorPositionInternTokenIndex = indexOfRegularExpression + 2;
        updateExternCursorPosition(CursorTokenPropertiesAfterModification.RIGHT);
        setCursorAndSelection(this.externCursorPosition, true);
    }

    public int getIndexOfCorrespondingRegularExpression() {
        InternFormulaTokenSelection internFormulaTokenSelection = this.internFormulaTokenSelection;
        if (internFormulaTokenSelection == null) {
            return -1;
        }
        int startIndex = internFormulaTokenSelection.getStartIndex();
        InternToken internToken = this.internTokenFormulaList.get(startIndex);
        return (internToken.getInternTokenType() == InternTokenType.FUNCTION_NAME && internToken.getTokenStringValue().equals(Functions.REGEX.name())) ? startIndex : getIndexOfRegularExpressionIfParamIsSelected(startIndex);
    }

    private int getIndexOfRegularExpressionIfParamIsSelected(int index) {
        if (index < 2) {
            return -1;
        }
        int i = 0;
        for (int i2 = index - 1; i2 >= 0; i2--) {
            InternToken internToken = this.internTokenFormulaList.get(i2);
            if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE) {
                i--;
            } else if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
                i++;
            }
            if (i == 1 && i2 > 0) {
                int i3 = i2 - 1;
                InternToken internToken2 = this.internTokenFormulaList.get(i3);
                if (internToken2.getInternTokenType() == InternTokenType.FUNCTION_NAME && internToken2.getTokenStringValue().equals(Functions.REGEX.name())) {
                    return i3;
                }
            }
        }
        return -1;
    }

    public String getSelectedText() {
        InternToken selectedToken = getSelectedToken();
        if (selectedToken == null) {
            return null;
        }
        return selectedToken.getTokenStringValue();
    }

    public void overrideSelectedText(String string, Context context) {
        InternToken selectedToken = getSelectedToken();
        if (selectedToken == null) {
            return;
        }
        selectedToken.setTokenStringValue(string);
        generateExternFormulaStringAndInternExternMapping(context);
    }

    public String getExternFormulaString() {
        return this.externFormulaString;
    }

    private boolean isTokenSelected() {
        return this.internFormulaTokenSelection != null;
    }

    public boolean isThereSomethingToDelete() {
        if (this.internFormulaTokenSelection != null) {
            return true;
        }
        CursorTokenPosition cursorTokenPosition = this.cursorTokenPosition;
        return (cursorTokenPosition == null || (cursorTokenPosition == CursorTokenPosition.LEFT && getFirstLeftInternToken(this.externCursorPosition - 1) == null)) ? false : true;
    }

    public void setInternTokenFormulaList(List<InternToken> list) {
        this.internTokenFormulaList = list;
    }

    public List<InternToken> getInternTokenFormulaList() {
        return this.internTokenFormulaList;
    }
}
