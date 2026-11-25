package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.RangeMarkerEx;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public class FrozenDocument implements DocumentEx {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile SoftReference<LineSet> myLineSet;
    private final long myStamp;
    private final ImmutableCharSequence myText;
    private volatile SoftReference<String> myTextString;

    /* JADX WARN: Code duplicated, block: B:37:0x0073  */
    /* JADX WARN: Code duplicated, block: B:8:0x0010  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 9:
            case 11:
            case 12:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/FrozenDocument";
                break;
            case 2:
                objArr[0] = "event";
                break;
            case 4:
                objArr[0] = "chars";
                break;
            case 5:
            case 6:
                objArr[0] = "rangeMarker";
                break;
            case 7:
            case 8:
                objArr[0] = "processor";
                break;
            case 10:
                objArr[0] = "range";
                break;
            case 14:
            case 15:
                objArr[0] = "s";
                break;
            case 16:
                objArr[0] = "block";
                break;
            case 17:
            default:
                objArr[0] = "text";
                break;
            case 18:
            case 19:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 20:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
        }
        if (i == 1) {
            objArr[1] = "getLineSet";
        } else if (i == 3) {
            objArr[1] = "createLineIterator";
        } else if (i != 9) {
            switch (i) {
                case 11:
                    objArr[1] = "getText";
                    break;
                case 12:
                    objArr[1] = "getCharsSequence";
                    break;
                case 13:
                    objArr[1] = "getImmutableCharSequence";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/FrozenDocument";
                    break;
            }
        } else {
            objArr[1] = "getText";
        }
        switch (i) {
            case 1:
            case 3:
            case 9:
            case 11:
            case 12:
            case 13:
                break;
            case 2:
                objArr[2] = "applyEvent";
                break;
            case 4:
                objArr[2] = "replaceText";
                break;
            case 5:
                objArr[2] = "removeRangeMarker";
                break;
            case 6:
                objArr[2] = "registerRangeMarker";
                break;
            case 7:
                objArr[2] = "processRangeMarkers";
                break;
            case 8:
                objArr[2] = "processRangeMarkersOverlappingWith";
                break;
            case 10:
                objArr[2] = "getText";
                break;
            case 14:
                objArr[2] = "insertString";
                break;
            case 15:
                objArr[2] = "replaceString";
                break;
            case 16:
                objArr[2] = "removeGuardedBlock";
                break;
            case 17:
                objArr[2] = "setText";
                break;
            case 18:
                objArr[2] = "getUserData";
                break;
            case 19:
                objArr[2] = "putUserData";
                break;
            case 20:
                objArr[2] = "removeEditReadOnlyListener";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public boolean isWritable() {
        return false;
    }

    FrozenDocument(ImmutableCharSequence immutableCharSequence, LineSet lineSet, long j, String str) {
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(0);
        }
        this.myText = immutableCharSequence;
        this.myLineSet = lineSet == null ? null : new SoftReference<>(lineSet);
        this.myStamp = j;
        this.myTextString = str != null ? new SoftReference<>(str) : null;
    }

    private LineSet getLineSet() {
        LineSet lineSet = (LineSet) SoftReference.dereference(this.myLineSet);
        if (lineSet == null) {
            LineSet lineSetCreateLineSet = LineSet.createLineSet(this.myText);
            this.myLineSet = new SoftReference<>(lineSetCreateLineSet);
            lineSet = lineSetCreateLineSet;
        }
        if (lineSet == null) {
            $$$reportNull$$$0(1);
        }
        return lineSet;
    }

    public FrozenDocument applyEvent(DocumentEvent documentEvent, int i) {
        if (documentEvent == null) {
            $$$reportNull$$$0(2);
        }
        int offset = documentEvent.getOffset();
        int oldLength = offset + documentEvent.getOldLength();
        return new FrozenDocument(this.myText.replace(offset, oldLength, documentEvent.getNewFragment()), getLineSet().update(this.myText, offset, oldLength, documentEvent.getNewFragment(), documentEvent.isWholeTextReplaced()), i, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public void setModificationStamp(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx
    public boolean removeRangeMarker(RangeMarkerEx rangeMarkerEx) {
        if (rangeMarkerEx == null) {
            $$$reportNull$$$0(5);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public String getText() {
        String str = (String) SoftReference.dereference(this.myTextString);
        if (str == null) {
            String string = this.myText.toString();
            this.myTextString = new SoftReference<>(string);
            str = string;
        }
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public CharSequence getCharsSequence() {
        ImmutableCharSequence immutableCharSequence = this.myText;
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(12);
        }
        return immutableCharSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public CharSequence getImmutableCharSequence() {
        ImmutableCharSequence immutableCharSequence = this.myText;
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(13);
        }
        return immutableCharSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineCount() {
        return getLineSet().getLineCount();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineNumber(int i) {
        return getLineSet().findLineIndex(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineStartOffset(int i) {
        if (i == 0) {
            return 0;
        }
        return getLineSet().getLineStart(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public int getLineEndOffset(int i) {
        if (getTextLength() == 0 && i == 0) {
            return 0;
        }
        return getLineSet().getLineEnd(i) - getLineSeparatorLength(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void replaceString(int i, int i2, CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(15);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public long getModificationStamp() {
        return this.myStamp;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void setReadOnly(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.Document
    public void setText(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(17);
        }
        throw new UnsupportedOperationException();
    }

    public int getLineSeparatorLength(int i) {
        return getLineSet().getSeparatorLength(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(18);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(19);
        }
        throw new UnsupportedOperationException();
    }
}
