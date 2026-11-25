package io.github.rosemoe.sora.text;

import io.github.rosemoe.sora.text.bidi.ContentBidi;
import io.github.rosemoe.sora.text.bidi.Directions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes11.dex */
public class Content implements CharSequence {
    public static final int DEFAULT_LIST_CAPACITY = 1000;
    public static final int DEFAULT_MAX_UNDO_STACK_SIZE = 500;
    private static int sInitialListCapacity;
    private final ContentBidi bidi;
    private final List<ContentListener> contentListeners;
    private Cursor cursor;
    private final AtomicLong documentVersion;
    private final Indexer indexer;
    private LineRemoveListener lineListener;
    private final List<ContentLine> lines;
    private final ReadWriteLock lock;
    private int nestedBatchEdit;
    private int textLength;
    private UndoManager undoManager;

    public interface ContentLineConsumer {
        void accept(int i, ContentLine contentLine, Directions directions);
    }

    public interface ContentLineConsumer2 {

        public static class AbortFlag {
            public boolean set = false;
        }

        void accept(int i, ContentLine contentLine, AbortFlag abortFlag);
    }

    static {
        setInitialLineCapacity(1000);
    }

    public Content() {
        this(null);
    }

    public Content(CharSequence charSequence) {
        this(charSequence, true);
    }

    public Content(CharSequence charSequence, boolean z) {
        this.documentVersion = new AtomicLong(1L);
        charSequence = charSequence == null ? "" : charSequence;
        if (z) {
            this.lock = new ReentrantReadWriteLock();
        } else {
            this.lock = null;
        }
        this.textLength = 0;
        this.nestedBatchEdit = 0;
        ArrayList arrayList = new ArrayList(getInitialLineCapacity());
        this.lines = arrayList;
        arrayList.add(new ContentLine());
        this.contentListeners = new ArrayList();
        this.bidi = new ContentBidi(this);
        this.undoManager = new UndoManager();
        setMaxUndoStackSize(500);
        this.indexer = new CachedIndexer(this);
        if (charSequence.length() == 0) {
            setUndoEnabled(true);
            return;
        }
        setUndoEnabled(false);
        insert(0, 0, charSequence);
        setUndoEnabled(true);
    }

    public static int getInitialLineCapacity() {
        return sInitialListCapacity;
    }

    public static void setInitialLineCapacity(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("capacity can not be negative or zero");
        }
        sInitialListCapacity = i;
    }

    private static boolean equals(ContentLine contentLine, ContentLine contentLine2) {
        if (contentLine.length() != contentLine2.length()) {
            return false;
        }
        for (int i = 0; i < contentLine.length(); i++) {
            if (contentLine.charAt(i) != contentLine2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isThreadSafe() {
        return this.lock != null;
    }

    protected void lock(boolean z) {
        ReadWriteLock readWriteLock = this.lock;
        if (readWriteLock == null) {
            return;
        }
        (z ? readWriteLock.writeLock() : readWriteLock.readLock()).lock();
    }

    protected void unlock(boolean z) {
        ReadWriteLock readWriteLock = this.lock;
        if (readWriteLock == null) {
            return;
        }
        (z ? readWriteLock.writeLock() : readWriteLock.readLock()).unlock();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        checkIndex(i);
        lock(false);
        try {
            CharPosition charPosition = getIndexer().getCharPosition(i);
            return this.lines.get(charPosition.line).charAt(charPosition.column);
        } finally {
            unlock(false);
        }
    }

    public char charAt(int i, int i2) {
        lock(false);
        try {
            checkLineAndColumn(i, i2);
            return this.lines.get(i).charAt(i2);
        } finally {
            unlock(false);
        }
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.textLength;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        if (i > i2) {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        lock(false);
        try {
            CharPosition charPosition = getIndexer().getCharPosition(i);
            CharPosition charPosition2 = getIndexer().getCharPosition(i2);
            return subContentInternal(charPosition.getLine(), charPosition.getColumn(), charPosition2.getLine(), charPosition2.getColumn());
        } finally {
            unlock(false);
        }
    }

    public String substring(int i, int i2) {
        if (i > i2) {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        lock(false);
        try {
            CharPosition charPosition = getIndexer().getCharPosition(i);
            CharPosition charPosition2 = getIndexer().getCharPosition(i2);
            return subStringBuilder(charPosition.getLine(), charPosition.getColumn(), charPosition2.getLine(), charPosition2.getColumn(), (i2 - i) + 1).toString();
        } finally {
            unlock(false);
        }
    }

    public void setLineListener(LineRemoveListener lineRemoveListener) {
        this.lineListener = lineRemoveListener;
    }

    public ContentLine getLine(int i) {
        lock(false);
        try {
            return this.lines.get(i);
        } finally {
            unlock(false);
        }
    }

    public int getLineCount() {
        return this.lines.size();
    }

    public int getColumnCount(int i) {
        return getLine(i).length();
    }

    public String getLineString(int i) {
        lock(false);
        try {
            checkLine(i);
            return this.lines.get(i).toString();
        } finally {
            unlock(false);
        }
    }

    public void getRegionOnLine(int i, int i2, int i3, char[] cArr, int i4) {
        lock(false);
        try {
            this.lines.get(i).getChars(i2, i3, cArr, i4);
        } finally {
            unlock(false);
        }
    }

    public void getLineChars(int i, char[] cArr) {
        getRegionOnLine(i, 0, getColumnCount(i), cArr, 0);
    }

    public int getCharIndex(int i, int i2) {
        lock(false);
        try {
            return getIndexer().getCharIndex(i, i2);
        } finally {
            unlock(false);
        }
    }

    public boolean isValidPosition(CharPosition charPosition) {
        if (charPosition == null) {
            return false;
        }
        int i = charPosition.line;
        int i2 = charPosition.column;
        int i3 = charPosition.index;
        lock(false);
        if (i >= 0) {
            try {
                if (i < getLineCount()) {
                    ContentLine line = getLine(i);
                    if (i2 <= line.length() + line.getLineSeparator().getLength() && i2 >= 0) {
                        return getIndexer().getCharIndex(i, i2) == i3;
                    }
                    return false;
                }
            } finally {
                unlock(false);
            }
        }
        return false;
    }

    public void insert(int i, int i2, CharSequence charSequence) {
        lock(true);
        this.documentVersion.getAndIncrement();
        try {
            insertInternal(i, i2, charSequence);
        } finally {
            unlock(true);
        }
    }

    private void insertInternal(int i, int i2, CharSequence charSequence) {
        checkLineAndColumn(i, i2);
        if (charSequence == null) {
            throw new IllegalArgumentException("text can not be null");
        }
        if (i2 > this.lines.get(i).length()) {
            i2 = this.lines.get(i).length();
        }
        int i3 = i2;
        Cursor cursor = this.cursor;
        if (cursor != null) {
            cursor.beforeInsert(i, i3);
        }
        dispatchBeforeModification();
        ContentLine contentLine = this.lines.get(i);
        InsertTextHelper insertTextHelperForInsertion = InsertTextHelper.forInsertion(charSequence);
        int iForward = InsertTextHelper.TYPE_EOF;
        LinkedList linkedList = new LinkedList();
        LineSeparator lineSeparator = contentLine.getLineSeparator();
        int i4 = i;
        int indexNext = i3;
        while (true) {
            boolean z = false;
            while (true) {
                int iForward2 = z ? iForward : insertTextHelperForInsertion.forward();
                if (iForward2 != InsertTextHelper.TYPE_EOF) {
                    if (iForward2 == InsertTextHelper.TYPE_LINE_CONTENT) {
                        break;
                    }
                    contentLine.setLineSeparator(LineSeparator.fromSeparatorString(charSequence, insertTextHelperForInsertion.getIndex(), insertTextHelperForInsertion.getIndexNext()));
                    iForward = insertTextHelperForInsertion.forward();
                    ContentLine contentLine2 = new ContentLine((((contentLine.length() - indexNext) + insertTextHelperForInsertion.getIndexNext()) - insertTextHelperForInsertion.getIndex()) + 10);
                    contentLine2.insert(0, contentLine, indexNext, contentLine.length());
                    contentLine.delete(indexNext, contentLine.length());
                    linkedList.add(contentLine2);
                    i4++;
                    indexNext = 0;
                    z = true;
                    contentLine = contentLine2;
                } else {
                    contentLine.setLineSeparator(lineSeparator);
                    this.lines.addAll(i + 1, linkedList);
                    insertTextHelperForInsertion.recycle();
                    this.textLength += charSequence.length();
                    dispatchAfterInsert(i, i3, i4, indexNext, charSequence);
                    return;
                }
            }
            contentLine.insert(indexNext, charSequence, insertTextHelperForInsertion.getIndex(), insertTextHelperForInsertion.getIndexNext());
            indexNext += insertTextHelperForInsertion.getIndexNext() - insertTextHelperForInsertion.getIndex();
        }
    }

    public void delete(int i, int i2) {
        lock(true);
        checkIndex(i);
        checkIndex(i2);
        this.documentVersion.getAndIncrement();
        try {
            CharPosition charPosition = getIndexer().getCharPosition(i);
            CharPosition charPosition2 = getIndexer().getCharPosition(i2);
            if (i != i2) {
                deleteInternal(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column);
            }
        } finally {
            unlock(true);
        }
    }

    public void delete(int i, int i2, int i3, int i4) {
        lock(true);
        this.documentVersion.getAndIncrement();
        try {
            deleteInternal(i, i2, i3, i4);
        } finally {
            unlock(true);
        }
    }

    private void deleteInternal(int i, int i2, int i3, int i4) {
        int i5;
        checkLineAndColumn(i3, i4);
        checkLineAndColumn(i, i2);
        if (i == i3 && i2 == i4) {
            return;
        }
        if (i4 > this.lines.get(i3).length() && (i5 = i3 + 1) < getLineCount()) {
            deleteInternal(i, i2, i5, 0);
            return;
        }
        ContentLine contentLine = this.lines.get(i);
        if (i2 > contentLine.length()) {
            deleteInternal(i, contentLine.length(), i3, i4);
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (i == i3) {
            ContentLine contentLine2 = this.lines.get(i);
            int length = contentLine2.length();
            if (i2 < 0 || i4 > length || i2 > i4) {
                throw new StringIndexOutOfBoundsException("invalid bounds");
            }
            Cursor cursor = this.cursor;
            if (cursor != null) {
                cursor.beforeDelete(i, i2, i3, i4);
            }
            dispatchBeforeModification();
            sb.append((CharSequence) contentLine2, i2, i4);
            contentLine2.delete(i2, i4);
            this.textLength -= i4 - i2;
        } else if (i < i3) {
            Cursor cursor2 = this.cursor;
            if (cursor2 != null) {
                cursor2.beforeDelete(i, i2, i3, i4);
            }
            dispatchBeforeModification();
            int i6 = i + 1;
            for (int i7 = i6; i7 <= i3 - 1; i7++) {
                ContentLine contentLine3 = this.lines.get(i7);
                LineRemoveListener lineRemoveListener = this.lineListener;
                if (lineRemoveListener != null) {
                    lineRemoveListener.onRemove(this, contentLine3);
                }
                LineSeparator lineSeparator = this.lines.get(i7).getLineSeparator();
                this.textLength -= contentLine3.length() + lineSeparator.getLength();
                sb.append((CharSequence) contentLine3).append(lineSeparator.getContent());
            }
            LineRemoveListener lineRemoveListener2 = this.lineListener;
            if (lineRemoveListener2 != null) {
                lineRemoveListener2.onRemove(this, this.lines.get(i3));
            }
            if (i3 > i6) {
                this.lines.subList(i6, i3).clear();
            }
            ContentLine contentLine4 = this.lines.get(i);
            ContentLine contentLine5 = this.lines.get(i6);
            this.textLength -= contentLine4.length() - i2;
            sb.insert(0, contentLine4, i2, contentLine4.length()).insert(contentLine4.length() - i2, contentLine4.getLineSeparator().getContent());
            contentLine4.delete(i2, contentLine4.length());
            this.textLength -= i4;
            sb.append((CharSequence) contentLine5, 0, i4);
            contentLine5.delete(0, i4);
            this.textLength -= contentLine4.getLineSeparator().getLength();
            this.lines.remove(i6);
            contentLine4.append(contentLine5);
            contentLine4.setLineSeparator(contentLine5.getLineSeparator());
        } else {
            throw new IllegalArgumentException("start line > end line");
        }
        dispatchAfterDelete(i, i2, i3, i4, sb);
    }

    public void replace(int i, int i2, int i3, int i4, CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException("text can not be null");
        }
        lock(true);
        this.documentVersion.getAndIncrement();
        try {
            dispatchBeforeReplace();
            deleteInternal(i, i2, i3, i4);
            insertInternal(i, i2, charSequence);
        } finally {
            unlock(true);
        }
    }

    public void replace(int i, int i2, CharSequence charSequence) {
        CharPosition charPosition = getIndexer().getCharPosition(i);
        CharPosition charPosition2 = getIndexer().getCharPosition(i2);
        replace(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column, charSequence);
    }

    public long getDocumentVersion() {
        return this.documentVersion.get();
    }

    public TextRange undo() {
        return this.undoManager.undo(this);
    }

    public void redo() {
        this.undoManager.redo(this);
    }

    public boolean isUndoManagerWorking() {
        return this.undoManager.isModifyingContent();
    }

    public boolean canUndo() {
        return this.undoManager.canUndo();
    }

    public boolean canRedo() {
        return this.undoManager.canRedo();
    }

    public boolean isUndoEnabled() {
        return this.undoManager.isUndoEnabled();
    }

    public void setUndoEnabled(boolean z) {
        this.undoManager.setUndoEnabled(z);
    }

    public int getMaxUndoStackSize() {
        return this.undoManager.getMaxUndoStackSize();
    }

    public void setMaxUndoStackSize(int i) {
        this.undoManager.setMaxUndoStackSize(i);
    }

    public boolean beginBatchEdit() {
        this.nestedBatchEdit++;
        return isInBatchEdit();
    }

    public boolean endBatchEdit() {
        int i = this.nestedBatchEdit - 1;
        this.nestedBatchEdit = i;
        if (i == 0) {
            this.undoManager.onExitBatchEdit();
        }
        if (this.nestedBatchEdit < 0) {
            this.nestedBatchEdit = 0;
        }
        return isInBatchEdit();
    }

    public int getNestedBatchEdit() {
        return this.nestedBatchEdit;
    }

    public void resetBatchEdit() {
        this.nestedBatchEdit = 0;
    }

    public boolean isInBatchEdit() {
        return this.nestedBatchEdit > 0;
    }

    public void addContentListener(ContentListener contentListener) {
        if (contentListener == null) {
            throw new IllegalArgumentException("listener can not be null");
        }
        if (contentListener instanceof Indexer) {
            throw new IllegalArgumentException("Permission denied");
        }
        if (this.contentListeners.contains(contentListener)) {
            return;
        }
        this.contentListeners.add(contentListener);
    }

    public void removeContentListener(ContentListener contentListener) {
        if (contentListener instanceof Indexer) {
            throw new IllegalArgumentException("Permission denied");
        }
        this.contentListeners.remove(contentListener);
    }

    public Indexer getIndexer() {
        Cursor cursor = this.cursor;
        if (cursor != null) {
            return cursor.getIndexer();
        }
        return this.indexer;
    }

    public Content subContent(int i, int i2, int i3, int i4) {
        lock(false);
        try {
            return subContentInternal(i, i2, i3, i4);
        } finally {
            unlock(false);
        }
    }

    private Content subContentInternal(int i, int i2, int i3, int i4) {
        Content content = new Content();
        content.setUndoEnabled(false);
        if (i == i3) {
            ContentLine contentLine = this.lines.get(i);
            if (i4 != contentLine.length() + 1 || contentLine.getLineSeparator() != LineSeparator.CRLF) {
                content.insert(0, 0, contentLine.subSequence(i2, i4));
            } else if (i2 < i4) {
                content.insert(0, 0, contentLine.subSequence(i2, contentLine.length()));
                content.lines.get(0).setLineSeparator(LineSeparator.CR);
                content.textLength++;
                content.lines.add(new ContentLine());
            }
        } else if (i < i3) {
            ContentLine contentLine2 = this.lines.get(i);
            if (contentLine2.getLineSeparator() != LineSeparator.CRLF || i2 <= contentLine2.length()) {
                content.insert(0, 0, contentLine2.subSequence(i2, contentLine2.length()));
                content.lines.get(0).setLineSeparator(contentLine2.getLineSeparator());
                content.textLength += contentLine2.getLineSeparator().getLength();
            } else if (i2 == contentLine2.length() + 1) {
                content.lines.get(0).setLineSeparator(LineSeparator.LF);
                content.textLength += LineSeparator.LF.getLength();
            } else {
                throw new IndexOutOfBoundsException();
            }
            for (int i5 = i + 1; i5 < i3; i5++) {
                ContentLine contentLine3 = this.lines.get(i5);
                content.lines.add(new ContentLine(contentLine3));
                content.textLength += contentLine3.length() + contentLine3.getLineSeparator().getLength();
            }
            ContentLine contentLine4 = this.lines.get(i3);
            if (i4 == contentLine4.length() + 1 && contentLine4.getLineSeparator() == LineSeparator.CRLF) {
                ContentLine contentLineInsert = new ContentLine().insert(0, contentLine4, 0, i4 - 1);
                content.lines.add(contentLineInsert);
                contentLineInsert.setLineSeparator(LineSeparator.CR);
                content.textLength += i4 + 1;
            } else {
                content.lines.add(new ContentLine().insert(0, contentLine4, 0, i4));
                content.textLength += i4;
            }
        } else {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        content.setUndoEnabled(true);
        return content;
    }

    private StringBuilder subStringBuilder(int i, int i2, int i3, int i4, int i5) {
        StringBuilder sb = new StringBuilder(i5);
        if (i == i3) {
            ContentLine contentLine = this.lines.get(i);
            if (i4 != contentLine.length() + 1 || contentLine.getLineSeparator() != LineSeparator.CRLF) {
                sb.append((CharSequence) this.lines.get(i), i2, i4);
            } else if (i2 < i4) {
                sb.append((CharSequence) this.lines.get(i), i2, contentLine.length()).append(LineSeparator.CR.getContent());
            }
        } else if (i < i3) {
            ContentLine contentLine2 = this.lines.get(i);
            if (contentLine2.getLineSeparator() != LineSeparator.CRLF || i2 <= contentLine2.length()) {
                sb.append((CharSequence) contentLine2, i2, contentLine2.length());
                sb.append(contentLine2.getLineSeparator().getContent());
            } else if (i2 == contentLine2.length() + 1) {
                sb.append(LineSeparator.LF.getContent());
            } else {
                throw new IndexOutOfBoundsException();
            }
            while (true) {
                i++;
                if (i >= i3) {
                    break;
                }
                ContentLine contentLine3 = this.lines.get(i);
                sb.append((CharSequence) contentLine3).append(contentLine3.getLineSeparator().getContent());
            }
            ContentLine contentLine4 = this.lines.get(i3);
            if (i4 == contentLine4.length() + 1 && contentLine4.getLineSeparator() == LineSeparator.CRLF) {
                sb.append((CharSequence) contentLine4, 0, i4).append(LineSeparator.CR.getContent());
            } else {
                sb.append((CharSequence) contentLine4, 0, i4);
            }
        } else {
            throw new StringIndexOutOfBoundsException("start > end");
        }
        return sb;
    }

    public Directions getLineDirections(int i) {
        lock(false);
        try {
            return this.bidi.getLineDirections(i);
        } finally {
            unlock(false);
        }
    }

    public void setBidiEnabled(boolean z) {
        this.bidi.setEnabled(z);
    }

    public boolean isBidiEnabled() {
        return this.bidi.isEnabled();
    }

    public boolean isRtlAt(int i, int i2) {
        Directions lineDirections = getLineDirections(i);
        for (int i3 = 0; i3 < lineDirections.getRunCount(); i3++) {
            if (i2 >= lineDirections.getRunStart(i3) && i2 < lineDirections.getRunEnd(i3)) {
                return lineDirections.isRunRtl(i3);
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Content)) {
            return false;
        }
        Content content = (Content) obj;
        if (content.length() != length()) {
            return false;
        }
        for (int i = 0; i < getLineCount(); i++) {
            if (!equals(this.lines.get(i), content.lines.get(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.lines, Integer.valueOf(this.textLength));
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        appendToStringBuilder(sb);
        return sb;
    }

    public UndoManager getUndoManager() {
        return this.undoManager;
    }

    public void setUndoManager(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    public void appendToStringBuilder(StringBuilder sb) {
        sb.ensureCapacity(sb.length() + length());
        lock(false);
        try {
            int lineCount = getLineCount();
            for (int i = 0; i < lineCount; i++) {
                ContentLine contentLine = this.lines.get(i);
                contentLine.appendTo(sb);
                sb.append(contentLine.getLineSeparator().getContent());
            }
            unlock(false);
        } catch (Throwable th) {
            unlock(false);
            throw th;
        }
    }

    public Cursor getCursor() {
        if (this.cursor == null) {
            this.cursor = new Cursor(this);
        }
        return this.cursor;
    }

    public boolean isCursorCreated() {
        return this.cursor != null;
    }

    private void dispatchBeforeReplace() {
        this.undoManager.beforeReplace(this);
        Cursor cursor = this.cursor;
        if (cursor != null) {
            cursor.beforeReplace();
        }
        Indexer indexer = this.indexer;
        if (indexer instanceof ContentListener) {
            ((ContentListener) indexer).beforeReplace(this);
        }
        Iterator<ContentListener> it = this.contentListeners.iterator();
        while (it.hasNext()) {
            it.next().beforeReplace(this);
        }
    }

    private void dispatchAfterDelete(int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.undoManager.afterDelete(this, i, i2, i3, i4, charSequence);
        Cursor cursor = this.cursor;
        if (cursor != null) {
            cursor.afterDelete(i, i2, i3, i4, charSequence);
        }
        Indexer indexer = this.indexer;
        if (indexer instanceof ContentListener) {
            ((ContentListener) indexer).afterDelete(this, i, i2, i3, i4, charSequence);
        }
        Iterator<ContentListener> it = this.contentListeners.iterator();
        while (it.hasNext()) {
            it.next().afterDelete(this, i, i2, i3, i4, charSequence);
        }
    }

    private void dispatchBeforeModification() {
        this.undoManager.beforeModification(this);
        Iterator<ContentListener> it = this.contentListeners.iterator();
        while (it.hasNext()) {
            it.next().beforeModification(this);
        }
    }

    private void dispatchAfterInsert(int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.undoManager.afterInsert(this, i, i2, i3, i4, charSequence);
        Cursor cursor = this.cursor;
        if (cursor != null) {
            cursor.afterInsert(i, i2, i3, i4, charSequence);
        }
        Indexer indexer = this.indexer;
        if (indexer instanceof ContentListener) {
            ((ContentListener) indexer).afterInsert(this, i, i2, i3, i4, charSequence);
        }
        Iterator<ContentListener> it = this.contentListeners.iterator();
        while (it.hasNext()) {
            it.next().afterInsert(this, i, i2, i3, i4, charSequence);
        }
    }

    protected void checkIndex(int i) {
        if (i > length() || i < 0) {
            throw new StringIndexOutOfBoundsException("Index " + i + " out of bounds. length:" + length());
        }
    }

    protected void checkLine(int i) {
        if (i >= getLineCount() || i < 0) {
            throw new StringIndexOutOfBoundsException("Line " + i + " out of bounds. line count:" + getLineCount());
        }
    }

    protected void checkLineAndColumn(int i, int i2) {
        checkLine(i);
        ContentLine contentLine = this.lines.get(i);
        int length = contentLine.length() + contentLine.getLineSeparator().getLength();
        if (i2 > length || i2 < 0) {
            throw new StringIndexOutOfBoundsException("Column " + i2 + " out of bounds. line: " + i + " , column count (line separator included):" + length);
        }
    }

    public Content copyText() {
        return copyText(true);
    }

    public Content copyText(boolean z) {
        lock(false);
        try {
            Content content = new Content(null, z);
            content.lines.remove(0);
            for (int i = 0; i < getLineCount(); i++) {
                content.lines.add(new ContentLine(this.lines.get(i)));
            }
            content.textLength = this.textLength;
            return content;
        } finally {
            unlock(false);
        }
    }

    protected int getColumnCountUnsafe(int i) {
        return this.lines.get(i).length();
    }

    protected LineSeparator getLineSeparatorUnsafe(int i) {
        return this.lines.get(i).getLineSeparator();
    }

    public void runReadActionsOnLines(int i, int i2, ContentLineConsumer contentLineConsumer) {
        lock(false);
        while (i <= i2) {
            try {
                contentLineConsumer.accept(i, this.lines.get(i), this.bidi.getLineDirections(i));
                i++;
            } catch (Throwable th) {
                unlock(false);
                throw th;
            }
        }
        unlock(false);
    }

    public void runReadActionsOnLines(int i, int i2, ContentLineConsumer2 contentLineConsumer2) {
        lock(false);
        try {
            ContentLineConsumer2.AbortFlag abortFlag = new ContentLineConsumer2.AbortFlag();
            while (i <= i2 && !abortFlag.set) {
                contentLineConsumer2.accept(i, this.lines.get(i), abortFlag);
                i++;
            }
            unlock(false);
        } catch (Throwable th) {
            unlock(false);
            throw th;
        }
    }
}
