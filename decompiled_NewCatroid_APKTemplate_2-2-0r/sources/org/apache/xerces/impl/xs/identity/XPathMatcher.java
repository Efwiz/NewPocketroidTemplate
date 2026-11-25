package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xpath.XPath;
import org.apache.xerces.util.IntStack;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes13.dex */
public class XPathMatcher {
    protected static final boolean DEBUG_ALL = false;
    protected static final boolean DEBUG_ANY = false;
    protected static final boolean DEBUG_MATCH = false;
    protected static final boolean DEBUG_METHODS = false;
    protected static final boolean DEBUG_METHODS2 = false;
    protected static final boolean DEBUG_METHODS3 = false;
    protected static final boolean DEBUG_STACK = false;
    protected static final int MATCHED = 1;
    protected static final int MATCHED_ATTRIBUTE = 3;
    protected static final int MATCHED_DESCENDANT = 5;
    protected static final int MATCHED_DESCENDANT_PREVIOUS = 13;
    private final int[] fCurrentStep;
    private final XPath.LocationPath[] fLocationPaths;
    private final int[] fMatched;
    protected Object fMatchedString;
    private final int[] fNoMatchDepth;
    final QName fQName = new QName();
    private final IntStack[] fStepIndexes;

    public XPathMatcher(XPath xPath) {
        XPath.LocationPath[] locationPaths = xPath.getLocationPaths();
        this.fLocationPaths = locationPaths;
        this.fStepIndexes = new IntStack[locationPaths.length];
        int i = 0;
        while (true) {
            IntStack[] intStackArr = this.fStepIndexes;
            if (i >= intStackArr.length) {
                XPath.LocationPath[] locationPathArr = this.fLocationPaths;
                this.fCurrentStep = new int[locationPathArr.length];
                this.fNoMatchDepth = new int[locationPathArr.length];
                this.fMatched = new int[locationPathArr.length];
                return;
            }
            intStackArr[i] = new IntStack();
            i++;
        }
    }

    private static boolean matches(XPath.NodeTest nodeTest, QName qName) {
        if (nodeTest.type == 1) {
            return nodeTest.f478name.equals(qName);
        }
        return nodeTest.type != 4 || nodeTest.f478name.uri == qName.uri;
    }

    private String normalize(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '\n') {
                stringBuffer.append(cCharAt);
            } else {
                stringBuffer.append("\\n");
            }
        }
        return stringBuffer.toString();
    }

    public void endElement(QName qName, XSTypeDefinition xSTypeDefinition, boolean z, Object obj, short s, ShortList shortList) {
        int[] iArr;
        int i;
        for (int i2 = 0; i2 < this.fLocationPaths.length; i2++) {
            this.fCurrentStep[i2] = this.fStepIndexes[i2].pop();
            int[] iArr2 = this.fNoMatchDepth;
            int i3 = iArr2[i2];
            if (i3 > 0) {
                iArr2[i2] = i3 - 1;
            } else {
                int i4 = 0;
                while (i4 < i2 && (this.fMatched[i4] & 1) != 1) {
                    i4++;
                }
                if (i4 >= i2 && (i = (iArr = this.fMatched)[i4]) != 0) {
                    if ((i & 3) == 3) {
                        iArr[i2] = 0;
                    } else {
                        handleContent(xSTypeDefinition, z, obj, s, shortList);
                        this.fMatched[i2] = 0;
                    }
                }
            }
        }
    }

    protected void handleContent(XSTypeDefinition xSTypeDefinition, boolean z, Object obj, short s, ShortList shortList) {
    }

    public boolean isMatched() {
        for (int i = 0; i < this.fLocationPaths.length; i++) {
            int i2 = this.fMatched[i];
            if ((i2 & 1) == 1 && (i2 & 13) != 13 && (this.fNoMatchDepth[i] == 0 || (i2 & 5) == 5)) {
                return true;
            }
        }
        return false;
    }

    protected void matched(Object obj, short s, ShortList shortList, boolean z) {
    }

    public void startDocumentFragment() {
        this.fMatchedString = null;
        for (int i = 0; i < this.fLocationPaths.length; i++) {
            this.fStepIndexes[i].clear();
            this.fCurrentStep[i] = 0;
            this.fNoMatchDepth[i] = 0;
            this.fMatched[i] = 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x00bf A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:48:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:49:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:50:0x00cf  */
    public void startElement(QName qName, XMLAttributes xMLAttributes) {
        int[] iArr;
        int i;
        for (int i2 = 0; i2 < this.fLocationPaths.length; i2++) {
            int i3 = this.fCurrentStep[i2];
            this.fStepIndexes[i2].push(i3);
            int[] iArr2 = this.fMatched;
            int i4 = iArr2[i2];
            if ((i4 & 5) == 1 || this.fNoMatchDepth[i2] > 0) {
                int[] iArr3 = this.fNoMatchDepth;
                iArr3[i2] = iArr3[i2] + 1;
            } else {
                if ((i4 & 5) == 5) {
                    iArr2[i2] = 13;
                }
                XPath.Step[] stepArr = this.fLocationPaths[i2].steps;
                while (true) {
                    int i5 = this.fCurrentStep[i2];
                    if (i5 >= stepArr.length || stepArr[i5].axis.type != 3) {
                        break;
                    }
                    int[] iArr4 = this.fCurrentStep;
                    iArr4[i2] = iArr4[i2] + 1;
                }
                int i6 = this.fCurrentStep[i2];
                if (i6 == stepArr.length) {
                    this.fMatched[i2] = 1;
                } else {
                    while (true) {
                        int i7 = this.fCurrentStep[i2];
                        if (i7 >= stepArr.length || stepArr[i7].axis.type != 4) {
                            break;
                        }
                        int[] iArr5 = this.fCurrentStep;
                        iArr5[i2] = iArr5[i2] + 1;
                    }
                    int i8 = this.fCurrentStep[i2];
                    boolean z = i8 > i6;
                    if (i8 == stepArr.length) {
                        int[] iArr6 = this.fNoMatchDepth;
                        iArr6[i2] = iArr6[i2] + 1;
                    } else if ((i8 != i3 && i8 <= i6) || stepArr[i8].axis.type != 1) {
                        iArr = this.fCurrentStep;
                        i = iArr[i2];
                        if (i == stepArr.length) {
                            if (z) {
                                iArr[i2] = i6;
                                this.fMatched[i2] = 5;
                            } else {
                                this.fMatched[i2] = 1;
                            }
                        } else if (i >= stepArr.length) {
                        }
                    } else if (matches(stepArr[this.fCurrentStep[i2]].nodeTest, qName)) {
                        int[] iArr7 = this.fCurrentStep;
                        iArr7[i2] = iArr7[i2] + 1;
                        iArr = this.fCurrentStep;
                        i = iArr[i2];
                        if (i == stepArr.length) {
                            if (z) {
                                iArr[i2] = i6;
                                this.fMatched[i2] = 5;
                            } else {
                                this.fMatched[i2] = 1;
                            }
                        } else if (i >= stepArr.length && stepArr[i].axis.type == 2) {
                            int length = xMLAttributes.getLength();
                            if (length > 0) {
                                XPath.NodeTest nodeTest = stepArr[this.fCurrentStep[i2]].nodeTest;
                                for (int i9 = 0; i9 < length; i9++) {
                                    xMLAttributes.getName(i9, this.fQName);
                                    if (matches(nodeTest, this.fQName)) {
                                        int[] iArr8 = this.fCurrentStep;
                                        int i10 = iArr8[i2] + 1;
                                        iArr8[i2] = i10;
                                        if (i10 != stepArr.length) {
                                            break;
                                        }
                                        this.fMatched[i2] = 3;
                                        int i11 = 0;
                                        while (i11 < i2 && (this.fMatched[i11] & 1) != 1) {
                                            i11++;
                                        }
                                        if (i11 != i2) {
                                            break;
                                        }
                                        AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(i9).getItem(Constants.ATTRIBUTE_PSVI);
                                        Object actualNormalizedValue = attributePSVI.getActualNormalizedValue();
                                        this.fMatchedString = actualNormalizedValue;
                                        matched(actualNormalizedValue, attributePSVI.getActualNormalizedValueType(), attributePSVI.getItemValueTypes(), false);
                                        break;
                                    }
                                }
                            }
                            if ((this.fMatched[i2] & 1) != 1) {
                                int[] iArr9 = this.fCurrentStep;
                                if (iArr9[i2] > i6) {
                                    iArr9[i2] = i6;
                                } else {
                                    int[] iArr10 = this.fNoMatchDepth;
                                    iArr10[i2] = iArr10[i2] + 1;
                                }
                            }
                        }
                    } else {
                        int[] iArr11 = this.fCurrentStep;
                        if (iArr11[i2] > i6) {
                            iArr11[i2] = i6;
                        } else {
                            int[] iArr12 = this.fNoMatchDepth;
                            iArr12[i2] = iArr12[i2] + 1;
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = super.toString();
        int iLastIndexOf = string.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            string = string.substring(iLastIndexOf + 1);
        }
        stringBuffer.append(string);
        for (int i = 0; i < this.fLocationPaths.length; i++) {
            stringBuffer.append('[');
            XPath.Step[] stepArr = this.fLocationPaths[i].steps;
            for (int i2 = 0; i2 < stepArr.length; i2++) {
                if (i2 == this.fCurrentStep[i]) {
                    stringBuffer.append(Util.C_EXCEPTION_START);
                }
                stringBuffer.append(stepArr[i2].toString());
                if (i2 < stepArr.length - 1) {
                    stringBuffer.append('/');
                }
            }
            if (this.fCurrentStep[i] == stepArr.length) {
                stringBuffer.append(Util.C_EXCEPTION_START);
            }
            stringBuffer.append("],");
        }
        return stringBuffer.toString();
    }
}
