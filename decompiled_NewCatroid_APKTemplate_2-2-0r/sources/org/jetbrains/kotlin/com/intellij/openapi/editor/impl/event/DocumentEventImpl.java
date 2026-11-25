package org.jetbrains.kotlin.com.intellij.openapi.editor.impl.event;

import java.util.ArrayList;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.LineSet;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.diff.Diff;
import org.jetbrains.kotlin.com.intellij.util.diff.FilesTooBigForDiffException;
import org.jetbrains.kotlin.com.intellij.util.text.MergingCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public class DocumentEventImpl extends DocumentEvent {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Diff.Change TOO_BIG_FILE = new Diff.Change(0, 0, 0, 0, null);
    private Diff.Change myChange;
    private final int myInitialOldLength;
    private final int myInitialStartOffset;
    private final boolean myIsWholeDocReplaced;
    private final int myMoveOffset;
    private final int myNewLength;
    private final CharSequence myNewString;
    private final int myOffset;
    private LineSet myOldFragmentLineSet;
    private int myOldFragmentLineSetStart;
    private final int myOldLength;
    private final CharSequence myOldString;
    private final long myOldTimeStamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "oldString";
        } else if (i == 2) {
            objArr[0] = "newString";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/event/DocumentEventImpl";
        } else {
            objArr[0] = "document";
        }
        if (i == 3) {
            objArr[1] = "getOldFragment";
        } else if (i == 4) {
            objArr[1] = "getNewFragment";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/event/DocumentEventImpl";
        } else {
            objArr[1] = "getOldLines";
        }
        if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DocumentEventImpl(Document document, int i, CharSequence charSequence, CharSequence charSequence2, long j, boolean z, int i2, int i3, int i4) {
        super(document);
        boolean z2 = false;
        if (document == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(2);
        }
        this.myOffset = i;
        this.myOldString = charSequence;
        this.myOldLength = charSequence.length();
        this.myNewString = charSequence2;
        this.myNewLength = charSequence2.length();
        this.myInitialStartOffset = i2;
        this.myInitialOldLength = i3;
        this.myMoveOffset = i4;
        this.myOldTimeStamp = j;
        if (getDocument().getTextLength() != 0 && z) {
            z2 = true;
        }
        this.myIsWholeDocReplaced = z2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public int getOffset() {
        return this.myOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public int getOldLength() {
        return this.myOldLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public int getNewLength() {
        return this.myNewLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public CharSequence getOldFragment() {
        CharSequence charSequence = this.myOldString;
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public CharSequence getNewFragment() {
        CharSequence charSequence = this.myNewString;
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return charSequence;
    }

    public int getInitialStartOffset() {
        return this.myInitialStartOffset;
    }

    public int getInitialOldLength() {
        return this.myInitialOldLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public int getMoveOffset() {
        return this.myMoveOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public long getOldTimeStamp() {
        return this.myOldTimeStamp;
    }

    @Override // java.util.EventObject
    public String toString() {
        return "DocumentEventImpl[myOffset=" + this.myOffset + ", myOldLength=" + this.myOldLength + ", myNewLength=" + this.myNewLength + "]" + (isWholeTextReplaced() ? " Whole." : ".");
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent
    public boolean isWholeTextReplaced() {
        return this.myIsWholeDocReplaced;
    }

    public int translateLineViaDiffStrict(int i) throws FilesTooBigForDiffException {
        int lineNumber;
        Diff.Change changeReBuildDiffIfNeeded = reBuildDiffIfNeeded();
        if (changeReBuildDiffIfNeeded == null || i < (lineNumber = getDocument().getLineNumber(getOffset()))) {
            return i;
        }
        int iTranslateLine = Diff.translateLine(changeReBuildDiffIfNeeded, i - lineNumber);
        if (iTranslateLine < 0) {
            return -1;
        }
        return iTranslateLine + lineNumber;
    }

    private Diff.Change reBuildDiffIfNeeded() throws FilesTooBigForDiffException {
        Diff.Change change = this.myChange;
        if (change == TOO_BIG_FILE) {
            throw new FilesTooBigForDiffException();
        }
        if (change == null) {
            try {
                this.myChange = Diff.buildChanges(getOldLines(), Diff.splitLines(this.myNewString));
            } catch (FilesTooBigForDiffException e) {
                this.myChange = TOO_BIG_FILE;
                throw e;
            }
        }
        return this.myChange;
    }

    private String[] getOldLines() {
        createOldFragmentLineSetIfNeeded();
        int i = this.myOffset - this.myOldFragmentLineSetStart;
        LineIterator lineIteratorCreateIterator = this.myOldFragmentLineSet.createIterator();
        ArrayList arrayList = new ArrayList(this.myOldFragmentLineSet.getLineCount());
        while (!lineIteratorCreateIterator.atEnd()) {
            int start = lineIteratorCreateIterator.getStart() - i;
            int end = (lineIteratorCreateIterator.getEnd() - lineIteratorCreateIterator.getSeparatorLength()) - i;
            if (start >= 0 && end <= this.myOldString.length()) {
                arrayList.add(this.myOldString.subSequence(start, end).toString());
            }
            lineIteratorCreateIterator.advance();
        }
        String[] stringArray = arrayList.isEmpty() ? new String[]{""} : ArrayUtil.toStringArray(arrayList);
        if (stringArray == null) {
            $$$reportNull$$$0(5);
        }
        return stringArray;
    }

    private void createOldFragmentLineSetIfNeeded() {
        if (this.myOldFragmentLineSet != null) {
            return;
        }
        CharSequence immutableCharSequence = getDocument().getImmutableCharSequence();
        CharSequence oldFragment = getOldFragment();
        int offset = getOffset();
        this.myOldFragmentLineSetStart = offset;
        if (offset > 0 && immutableCharSequence.charAt(offset - 1) == '\r') {
            this.myOldFragmentLineSetStart--;
            oldFragment = new MergingCharSequence("\r", oldFragment);
        }
        int offset2 = getOffset() + getNewLength();
        if (offset2 < immutableCharSequence.length() && immutableCharSequence.charAt(offset2) == '\n') {
            oldFragment = new MergingCharSequence(oldFragment, "\n");
        }
        this.myOldFragmentLineSet = LineSet.createLineSet(oldFragment);
    }
}
