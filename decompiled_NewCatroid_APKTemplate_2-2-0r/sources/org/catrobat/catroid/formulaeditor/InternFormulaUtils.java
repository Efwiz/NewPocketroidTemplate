package org.catrobat.catroid.formulaeditor;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InternFormulaUtils {
    private InternFormulaUtils() {
        throw new AssertionError();
    }

    public static List<InternToken> getFunctionByFunctionBracketClose(List<InternToken> internTokenList, int functionBracketCloseInternTokenListIndex) {
        if (functionBracketCloseInternTokenListIndex == 0 || functionBracketCloseInternTokenListIndex == internTokenList.size()) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(internTokenList.get(functionBracketCloseInternTokenListIndex));
        int i = functionBracketCloseInternTokenListIndex - 1;
        int i2 = 1;
        while (i >= 0) {
            InternToken internToken = internTokenList.get(i);
            i--;
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i3 == 1) {
                i2--;
            } else if (i3 == 2) {
                i2++;
            }
            linkedList.add(internToken);
            if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN && i2 == 0) {
                if (i < 0) {
                    return null;
                }
                InternToken internToken2 = internTokenList.get(i);
                if (internToken2.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
                    return null;
                }
                linkedList.add(internToken2);
                Collections.reverse(linkedList);
                return linkedList;
            }
        }
        return null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaUtils$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.InternFormulaUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType;

        static {
            int[] iArr = new int[InternTokenType.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType = iArr;
            try {
                iArr[InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.BRACKET_CLOSE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[InternTokenType.FUNCTION_PARAMETER_DELIMITER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static List<InternToken> getFunctionByParameterDelimiter(List<InternToken> internTokenList, int functionParameterDelimiterInternTokenListIndex) {
        if (functionParameterDelimiterInternTokenListIndex == 0 || functionParameterDelimiterInternTokenListIndex == internTokenList.size()) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(internTokenList.get(functionParameterDelimiterInternTokenListIndex));
        int i = functionParameterDelimiterInternTokenListIndex - 1;
        int i2 = 1;
        while (i >= 0) {
            InternToken internToken = internTokenList.get(i);
            i--;
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i3 == 1) {
                i2--;
            } else if (i3 == 2) {
                i2++;
            }
            linkedList.add(internToken);
            if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN && i2 == 0) {
                if (i < 0) {
                    return null;
                }
                InternToken internToken2 = internTokenList.get(i);
                if (internToken2.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
                    return null;
                }
                linkedList.add(internToken2);
                Collections.reverse(linkedList);
                int i4 = functionParameterDelimiterInternTokenListIndex + 1;
                int i5 = 1;
                while (i4 < internTokenList.size()) {
                    InternToken internToken3 = internTokenList.get(i4);
                    i4++;
                    int i6 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken3.getInternTokenType().ordinal()];
                    if (i6 == 1) {
                        i5++;
                    } else if (i6 == 2) {
                        i5--;
                    }
                    linkedList.add(internToken3);
                    if (internToken3.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && i5 == 0) {
                        return linkedList;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static List<InternToken> getFunctionByFunctionBracketOpen(List<InternToken> internTokenList, int functionBracketOpenInternTokenListIndex) {
        if (functionBracketOpenInternTokenListIndex <= 0 || functionBracketOpenInternTokenListIndex >= internTokenList.size()) {
            return null;
        }
        int i = functionBracketOpenInternTokenListIndex - 1;
        if (internTokenList.get(i).getInternTokenType() != InternTokenType.FUNCTION_NAME) {
            return null;
        }
        return getFunctionByName(internTokenList, i);
    }

    public static List<InternToken> getFunctionByName(List<InternToken> internTokenList, int functionStartListIndex) {
        InternToken internToken = internTokenList.get(functionStartListIndex);
        LinkedList linkedList = new LinkedList();
        if (internToken.getInternTokenType() != InternTokenType.FUNCTION_NAME) {
            return null;
        }
        linkedList.add(internToken);
        int i = functionStartListIndex + 1;
        if (i >= internTokenList.size()) {
            return linkedList;
        }
        InternToken internToken2 = internTokenList.get(i);
        if (!internToken2.isFunctionParameterBracketOpen()) {
            return linkedList;
        }
        linkedList.add(internToken2);
        int i2 = functionStartListIndex + 2;
        int i3 = 1;
        while (i2 < internTokenList.size()) {
            InternToken internToken3 = internTokenList.get(i2);
            i2++;
            int i4 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken3.getInternTokenType().ordinal()];
            if (i4 == 1) {
                i3++;
            } else if (i4 == 2) {
                i3--;
            }
            linkedList.add(internToken3);
            if (internToken3.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && i3 == 0) {
                return linkedList;
            }
        }
        return null;
    }

    public static List<InternToken> generateTokenListByBracketOpen(List<InternToken> internTokenList, int internTokenListIndex) {
        if (internTokenListIndex == internTokenList.size() || internTokenList.get(internTokenListIndex).getInternTokenType() != InternTokenType.BRACKET_OPEN) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(internTokenList.get(internTokenListIndex));
        int i = 1;
        int i2 = internTokenListIndex + 1;
        while (i2 < internTokenList.size()) {
            InternToken internToken = internTokenList.get(i2);
            i2++;
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i3 == 3) {
                i++;
            } else if (i3 == 4) {
                i--;
            }
            linkedList.add(internToken);
            if (internToken.getInternTokenType() == InternTokenType.BRACKET_CLOSE && i == 0) {
                return linkedList;
            }
        }
        return null;
    }

    public static List<InternToken> generateTokenListByBracketClose(List<InternToken> internTokenList, int internTokenListIndex) {
        if (internTokenListIndex == internTokenList.size() || internTokenList.get(internTokenListIndex).getInternTokenType() != InternTokenType.BRACKET_CLOSE) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(internTokenList.get(internTokenListIndex));
        int i = 1;
        int i2 = internTokenListIndex - 1;
        while (i2 >= 0) {
            InternToken internToken = internTokenList.get(i2);
            i2--;
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i3 == 3) {
                i--;
            } else if (i3 == 4) {
                i++;
            }
            linkedList.add(internToken);
            if (internToken.getInternTokenType() == InternTokenType.BRACKET_OPEN && i == 0) {
                Collections.reverse(linkedList);
                return linkedList;
            }
        }
        return null;
    }

    public static List<List<InternToken>> getFunctionParameterInternTokensAsLists(List<InternToken> functionInternTokenList) {
        LinkedList linkedList = new LinkedList();
        if (functionInternTokenList == null || functionInternTokenList.size() < 4 || functionInternTokenList.get(0).getInternTokenType() != InternTokenType.FUNCTION_NAME || functionInternTokenList.get(1).getInternTokenType() != InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
            return null;
        }
        LinkedList linkedList2 = new LinkedList();
        int i = 1;
        int i2 = 2;
        while (i2 < functionInternTokenList.size()) {
            InternToken internToken = functionInternTokenList.get(i2);
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i3 == 1) {
                i++;
                linkedList2.add(internToken);
            } else if (i3 == 2) {
                i--;
                if (i != 0) {
                    linkedList2.add(internToken);
                }
            } else if (i3 == 5 && i == 1) {
                linkedList.add(linkedList2);
                linkedList2 = new LinkedList();
            } else {
                linkedList2.add(internToken);
            }
            i2++;
            if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && i == 0) {
                if (linkedList2.size() > 0) {
                    linkedList.add(linkedList2);
                }
                return linkedList;
            }
        }
        return null;
    }

    public static boolean isFunction(List<InternToken> internTokenList) {
        List<InternToken> functionByName = getFunctionByName(internTokenList, 0);
        return functionByName != null && functionByName.size() == internTokenList.size();
    }

    private static InternTokenType getFirstInternTokenType(List<InternToken> internTokens) {
        if (internTokens == null || internTokens.size() == 0) {
            return null;
        }
        return internTokens.get(0).getInternTokenType();
    }

    public static boolean isPeriodToken(List<InternToken> internTokens) {
        return internTokens != null && internTokens.size() == 1 && internTokens.get(0).getInternTokenType() == InternTokenType.PERIOD;
    }

    public static boolean isFunctionToken(List<InternToken> internTokens) {
        InternTokenType firstInternTokenType = getFirstInternTokenType(internTokens);
        return firstInternTokenType != null && firstInternTokenType == InternTokenType.FUNCTION_NAME;
    }

    public static boolean isNumberToken(List<InternToken> internTokens) {
        InternTokenType firstInternTokenType = getFirstInternTokenType(internTokens);
        return firstInternTokenType != null && internTokens.size() <= 1 && firstInternTokenType == InternTokenType.NUMBER;
    }

    public static List<InternToken> replaceFunctionByTokens(List<InternToken> functionToReplace, List<InternToken> internTokensToReplaceWith) {
        return isFunctionToken(internTokensToReplaceWith) ? replaceFunctionButKeepParameters(functionToReplace, internTokensToReplaceWith) : internTokensToReplaceWith;
    }

    public static List<InternToken> insertOperatorToNumberToken(InternToken numberTokenToBeModified, int externNumberOffset, InternToken operatorToInsert) {
        LinkedList linkedList = new LinkedList();
        String tokenStringValue = numberTokenToBeModified.getTokenStringValue();
        String strSubstring = tokenStringValue.substring(0, externNumberOffset);
        String strSubstring2 = tokenStringValue.substring(externNumberOffset);
        linkedList.add(new InternToken(InternTokenType.NUMBER, strSubstring));
        linkedList.add(operatorToInsert);
        linkedList.add(new InternToken(InternTokenType.NUMBER, strSubstring2));
        return linkedList;
    }

    public static InternToken insertIntoNumberToken(InternToken numberTokenToBeModified, int externNumberOffset, String numberToInsert) {
        String tokenStringValue = numberTokenToBeModified.getTokenStringValue();
        numberTokenToBeModified.setTokenStringValue(tokenStringValue.substring(0, externNumberOffset) + numberToInsert + tokenStringValue.substring(externNumberOffset));
        return numberTokenToBeModified;
    }

    public static List<InternToken> replaceFunctionButKeepParameters(List<InternToken> functionToReplace, List<InternToken> functionToReplaceWith) {
        List<List<InternToken>> functionParameterInternTokensAsLists = getFunctionParameterInternTokensAsLists(functionToReplace);
        LinkedList linkedList = new LinkedList();
        List<List<InternToken>> functionParameterInternTokensAsLists2 = getFunctionParameterInternTokensAsLists(functionToReplaceWith);
        if (functionToReplace == null || functionParameterInternTokensAsLists == null || functionParameterInternTokensAsLists2 == null) {
            return functionToReplaceWith;
        }
        linkedList.add(functionToReplaceWith.get(0));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        int functionParameterCount = getFunctionParameterCount(functionToReplaceWith);
        for (int i = 0; i < functionParameterCount; i++) {
            if (i < functionParameterInternTokensAsLists.size() && functionParameterInternTokensAsLists.get(i).size() > 0) {
                linkedList.addAll(functionParameterInternTokensAsLists.get(i));
            } else {
                linkedList.addAll(functionParameterInternTokensAsLists2.get(i));
            }
            if (i < functionParameterCount - 1) {
                linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
            }
        }
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }

    static int getFunctionParameterCount(List<InternToken> functionInternTokenList) {
        if (functionInternTokenList == null || functionInternTokenList.size() < 4 || functionInternTokenList.get(0).getInternTokenType() != InternTokenType.FUNCTION_NAME || functionInternTokenList.get(1).getInternTokenType() != InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
            return 0;
        }
        int i = 1;
        int i2 = 1;
        int i3 = 2;
        while (i3 < functionInternTokenList.size()) {
            InternToken internToken = functionInternTokenList.get(i3);
            int i4 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internToken.getInternTokenType().ordinal()];
            if (i4 == 1) {
                i++;
            } else if (i4 == 2) {
                i--;
            } else if (i4 == 5 && i == 1) {
                i2++;
            }
            i3++;
            if (internToken.getInternTokenType() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE && i == 0) {
                return i2;
            }
        }
        return 0;
    }

    public static InternToken deleteNumberByOffset(InternToken cursorPositionInternToken, int externNumberOffset) {
        String tokenStringValue = cursorPositionInternToken.getTokenStringValue();
        if (externNumberOffset < 1) {
            return cursorPositionInternToken;
        }
        if (externNumberOffset > tokenStringValue.length()) {
            externNumberOffset = tokenStringValue.length();
        }
        cursorPositionInternToken.setTokenStringValue(tokenStringValue.substring(0, externNumberOffset - 1) + tokenStringValue.substring(externNumberOffset));
        if (cursorPositionInternToken.getTokenStringValue().isEmpty()) {
            return null;
        }
        return cursorPositionInternToken;
    }

    public static boolean applyBracketCorrection(List<InternToken> internFormula) throws EmptyStackException {
        Stack stack = new Stack();
        for (int i = 0; i < internFormula.size(); i++) {
            int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$InternTokenType[internFormula.get(i).getInternTokenType().ordinal()];
            if (i2 == 1) {
                stack.push(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN);
            } else if (i2 != 2) {
                if (i2 == 3) {
                    stack.push(InternTokenType.BRACKET_OPEN);
                } else if (i2 != 4) {
                    continue;
                } else if (stack.peek() == InternTokenType.BRACKET_OPEN) {
                    stack.pop();
                } else {
                    if (!swapBrackets(internFormula, i, InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE)) {
                        return false;
                    }
                    stack.pop();
                }
            } else if (stack.peek() == InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN) {
                stack.pop();
            } else {
                if (!swapBrackets(internFormula, i, InternTokenType.BRACKET_CLOSE)) {
                    return false;
                }
                stack.pop();
            }
        }
        return true;
    }

    private static boolean swapBrackets(List<InternToken> internFormula, int firstBracketIndex, InternTokenType secondBracket) {
        for (int i = firstBracketIndex + 1; i < internFormula.size(); i++) {
            if (internFormula.get(i).getInternTokenType() == secondBracket) {
                InternToken internToken = internFormula.get(firstBracketIndex);
                internFormula.set(firstBracketIndex, internFormula.get(i));
                internFormula.set(i, internToken);
                return true;
            }
        }
        return false;
    }
}
