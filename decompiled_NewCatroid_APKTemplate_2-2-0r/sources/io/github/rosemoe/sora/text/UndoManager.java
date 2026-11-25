package io.github.rosemoe.sora.text;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public final class UndoManager implements ContentListener, Parcelable {
    public static final Parcelable.Creator<UndoManager> CREATOR = new Parcelable.Creator<UndoManager>() { // from class: io.github.rosemoe.sora.text.UndoManager.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UndoManager createFromParcel(Parcel parcel) {
            UndoManager undoManager = new UndoManager();
            undoManager.maxStackSize = parcel.readInt();
            undoManager.stackPointer = parcel.readInt();
            undoManager.undoEnabled = parcel.readInt() > 0;
            for (int i = parcel.readInt(); i > 0; i--) {
                undoManager.actionStack.add((ContentAction) parcel.readParcelable(UndoManager.class.getClassLoader()));
            }
            return undoManager;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UndoManager[] newArray(int i) {
            return new UndoManager[i];
        }
    };
    private static long sMergeTimeLimit = 8000;
    private boolean forceNewMultiAction;
    private int maxStackSize;
    private TextRange memorizedCursorRange;
    private Content targetContent;
    private boolean undoEnabled;
    private final List<ContentAction> actionStack = new ArrayList();
    private boolean replaceMark = false;
    private InsertAction insertAction = null;
    private DeleteAction deleteAction = null;
    private int stackPointer = 0;
    private boolean ignoreModification = false;

    public static abstract class ContentAction implements Parcelable {
        public transient TextRange cursor;

        public abstract boolean canMerge(ContentAction contentAction);

        public abstract void merge(ContentAction contentAction);

        public abstract void redo(Content content);

        public abstract void undo(Content content);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    UndoManager() {
    }

    public static long getMergeTimeLimit() {
        return sMergeTimeLimit;
    }

    public static void setMergeTimeLimit(long j) {
        sMergeTimeLimit = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.maxStackSize);
        parcel.writeInt(this.stackPointer);
        parcel.writeInt(this.undoEnabled ? 1 : 0);
        parcel.writeInt(this.actionStack.size());
        Iterator<ContentAction> it = this.actionStack.iterator();
        while (it.hasNext()) {
            parcel.writeParcelable(it.next(), i);
        }
    }

    public boolean isModifyingContent() {
        return this.ignoreModification;
    }

    public TextRange undo(Content content) {
        if (!canUndo() || isModifyingContent()) {
            return null;
        }
        this.ignoreModification = true;
        ContentAction contentAction = this.actionStack.get(this.stackPointer - 1);
        contentAction.undo(content);
        this.stackPointer--;
        this.ignoreModification = false;
        return contentAction.cursor;
    }

    public void redo(Content content) {
        if (!canRedo() || isModifyingContent()) {
            return;
        }
        this.ignoreModification = true;
        this.actionStack.get(this.stackPointer).redo(content);
        this.stackPointer++;
        this.ignoreModification = false;
    }

    void onExitBatchEdit() {
        this.forceNewMultiAction = true;
        if (this.actionStack.isEmpty()) {
            return;
        }
        List<ContentAction> list = this.actionStack;
        if (list.get(list.size() - 1) instanceof MultiAction) {
            List<ContentAction> list2 = this.actionStack;
            MultiAction multiAction = (MultiAction) list2.get(list2.size() - 1);
            if (multiAction._actions.size() == 1) {
                List<ContentAction> list3 = this.actionStack;
                list3.set(list3.size() - 1, (ContentAction) multiAction._actions.get(0));
            }
        }
    }

    public boolean canUndo() {
        return isUndoEnabled() && this.stackPointer > 0;
    }

    public boolean canRedo() {
        return isUndoEnabled() && this.stackPointer < this.actionStack.size();
    }

    public boolean isUndoEnabled() {
        return this.undoEnabled;
    }

    public void setUndoEnabled(boolean z) {
        this.undoEnabled = z;
        if (z) {
            return;
        }
        cleanStack();
    }

    public int getMaxUndoStackSize() {
        return this.maxStackSize;
    }

    public void setMaxUndoStackSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("max size can not be zero or smaller.Did you want to disable undo module by calling setUndoEnabled()?");
        }
        this.maxStackSize = i;
        cleanStack();
    }

    private void cleanStack() {
        if (!this.undoEnabled) {
            this.actionStack.clear();
            this.stackPointer = 0;
        } else {
            while (this.stackPointer > 1 && this.actionStack.size() > this.maxStackSize) {
                this.actionStack.remove(0);
                this.stackPointer--;
            }
        }
    }

    private void cleanBeforePush() {
        while (this.stackPointer < this.actionStack.size()) {
            List<ContentAction> list = this.actionStack;
            list.remove(list.size() - 1);
        }
    }

    private void pushAction(Content content, ContentAction contentAction) {
        if (isUndoEnabled()) {
            cleanBeforePush();
            if (content.isInBatchEdit()) {
                if (this.actionStack.isEmpty()) {
                    MultiAction multiAction = new MultiAction();
                    multiAction.addAction(contentAction);
                    multiAction.cursor = contentAction.cursor;
                    this.actionStack.add(multiAction);
                    this.stackPointer++;
                } else {
                    List<ContentAction> list = this.actionStack;
                    ContentAction contentAction2 = list.get(list.size() - 1);
                    if ((contentAction2 instanceof MultiAction) && !this.forceNewMultiAction) {
                        ((MultiAction) contentAction2).addAction(contentAction);
                    } else {
                        MultiAction multiAction2 = new MultiAction();
                        multiAction2.addAction(contentAction);
                        multiAction2.cursor = contentAction.cursor;
                        this.actionStack.add(multiAction2);
                        this.stackPointer++;
                    }
                }
            } else if (this.actionStack.isEmpty()) {
                this.actionStack.add(contentAction);
                this.stackPointer++;
            } else {
                List<ContentAction> list2 = this.actionStack;
                ContentAction contentAction3 = list2.get(list2.size() - 1);
                if (contentAction3.canMerge(contentAction)) {
                    contentAction3.merge(contentAction);
                } else {
                    this.actionStack.add(contentAction);
                    this.stackPointer++;
                }
            }
            this.forceNewMultiAction = false;
            cleanStack();
        }
    }

    public void exitReplaceMode() {
        DeleteAction deleteAction;
        if (this.replaceMark && (deleteAction = this.deleteAction) != null) {
            pushAction(this.targetContent, deleteAction);
        }
        this.replaceMark = false;
        this.targetContent = null;
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
        if (this.ignoreModification) {
            return;
        }
        this.replaceMark = true;
        this.targetContent = content;
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        if (this.ignoreModification) {
            return;
        }
        InsertAction insertAction = new InsertAction();
        this.insertAction = insertAction;
        insertAction.startLine = i;
        this.insertAction.startColumn = i2;
        this.insertAction.endLine = i3;
        this.insertAction.endColumn = i4;
        this.insertAction.text = charSequence;
        if (this.replaceMark && this.deleteAction != null) {
            ReplaceAction replaceAction = new ReplaceAction();
            replaceAction.delete = this.deleteAction;
            replaceAction.insert = this.insertAction;
            replaceAction.cursor = this.memorizedCursorRange;
            pushAction(content, replaceAction);
        } else {
            this.insertAction.cursor = this.memorizedCursorRange;
            pushAction(content, this.insertAction);
        }
        this.deleteAction = null;
        this.insertAction = null;
        this.replaceMark = false;
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        if (this.ignoreModification) {
            return;
        }
        DeleteAction deleteAction = new DeleteAction();
        this.deleteAction = deleteAction;
        deleteAction.endColumn = i4;
        this.deleteAction.startColumn = i2;
        this.deleteAction.endLine = i3;
        this.deleteAction.startLine = i;
        this.deleteAction.text = charSequence;
        this.deleteAction.cursor = this.memorizedCursorRange;
        if (this.replaceMark) {
            return;
        }
        pushAction(content, this.deleteAction);
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeModification(Content content) {
        if (this.undoEnabled && content.isCursorCreated()) {
            if (!this.replaceMark || this.deleteAction == null) {
                this.memorizedCursorRange = content.getCursor().getRange();
            }
        }
    }

    public static final class InsertAction extends ContentAction {
        public static final Parcelable.Creator<InsertAction> CREATOR = new Parcelable.Creator<InsertAction>() { // from class: io.github.rosemoe.sora.text.UndoManager.InsertAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InsertAction createFromParcel(Parcel parcel) {
                InsertAction insertAction = new InsertAction();
                insertAction.startLine = parcel.readInt();
                insertAction.startColumn = parcel.readInt();
                insertAction.endLine = parcel.readInt();
                insertAction.endColumn = parcel.readInt();
                insertAction.text = parcel.readString();
                return insertAction;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InsertAction[] newArray(int i) {
                return new InsertAction[i];
            }
        };
        public transient long createTime = System.currentTimeMillis();
        public int endColumn;
        public int endLine;
        public int startColumn;
        public int startLine;
        public CharSequence text;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void undo(Content content) {
            content.delete(this.startLine, this.startColumn, this.endLine, this.endColumn);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void redo(Content content) {
            content.insert(this.startLine, this.startColumn, this.text);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public boolean canMerge(ContentAction contentAction) {
            if (!(contentAction instanceof InsertAction)) {
                return false;
            }
            InsertAction insertAction = (InsertAction) contentAction;
            return insertAction.startColumn == this.endColumn && insertAction.startLine == this.endLine && insertAction.text.length() + this.text.length() < 10000 && Math.abs(insertAction.createTime - this.createTime) < UndoManager.sMergeTimeLimit;
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void merge(ContentAction contentAction) {
            StringBuilder sb;
            if (!canMerge(contentAction)) {
                throw new IllegalArgumentException();
            }
            InsertAction insertAction = (InsertAction) contentAction;
            this.endColumn = insertAction.endColumn;
            this.endLine = insertAction.endLine;
            CharSequence charSequence = this.text;
            if (charSequence instanceof StringBuilder) {
                sb = (StringBuilder) charSequence;
            } else {
                sb = new StringBuilder(this.text);
                this.text = sb;
            }
            sb.append(insertAction.text);
        }

        public String toString() {
            return "InsertAction{startLine=" + this.startLine + ", endLine=" + this.endLine + ", startColumn=" + this.startColumn + ", endColumn=" + this.endColumn + ", createTime=" + this.createTime + ", text=" + ((Object) this.text) + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.startLine);
            parcel.writeInt(this.startColumn);
            parcel.writeInt(this.endLine);
            parcel.writeInt(this.endColumn);
            parcel.writeString(this.text.toString());
        }
    }

    public static final class MultiAction extends ContentAction {
        public static final Parcelable.Creator<MultiAction> CREATOR = new Parcelable.Creator<MultiAction>() { // from class: io.github.rosemoe.sora.text.UndoManager.MultiAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MultiAction createFromParcel(Parcel parcel) {
                MultiAction multiAction = new MultiAction();
                for (int i = parcel.readInt(); i > 0; i--) {
                    multiAction._actions.add((ContentAction) parcel.readParcelable(MultiAction.class.getClassLoader()));
                }
                return multiAction;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MultiAction[] newArray(int i) {
                return new MultiAction[i];
            }
        };
        private final List<ContentAction> _actions = new ArrayList();

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public boolean canMerge(ContentAction contentAction) {
            return false;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void addAction(ContentAction contentAction) {
            if (this._actions.isEmpty()) {
                this._actions.add(contentAction);
                return;
            }
            List<ContentAction> list = this._actions;
            ContentAction contentAction2 = list.get(list.size() - 1);
            if (contentAction2.canMerge(contentAction)) {
                contentAction2.merge(contentAction);
            } else {
                this._actions.add(contentAction);
            }
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void undo(Content content) {
            for (int size = this._actions.size() - 1; size >= 0; size--) {
                this._actions.get(size).undo(content);
            }
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void redo(Content content) {
            for (int i = 0; i < this._actions.size(); i++) {
                this._actions.get(i).redo(content);
            }
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void merge(ContentAction contentAction) {
            throw new UnsupportedOperationException();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._actions.size());
            Iterator<ContentAction> it = this._actions.iterator();
            while (it.hasNext()) {
                parcel.writeParcelable(it.next(), i);
            }
        }
    }

    public static final class DeleteAction extends ContentAction {
        public static final Parcelable.Creator<DeleteAction> CREATOR = new Parcelable.Creator<DeleteAction>() { // from class: io.github.rosemoe.sora.text.UndoManager.DeleteAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DeleteAction createFromParcel(Parcel parcel) {
                DeleteAction deleteAction = new DeleteAction();
                deleteAction.startLine = parcel.readInt();
                deleteAction.startColumn = parcel.readInt();
                deleteAction.endLine = parcel.readInt();
                deleteAction.endColumn = parcel.readInt();
                deleteAction.text = parcel.readString();
                return deleteAction;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DeleteAction[] newArray(int i) {
                return new DeleteAction[i];
            }
        };
        public transient long createTime = System.currentTimeMillis();
        public int endColumn;
        public int endLine;
        public int startColumn;
        public int startLine;
        public CharSequence text;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void undo(Content content) {
            content.insert(this.startLine, this.startColumn, this.text);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void redo(Content content) {
            content.delete(this.startLine, this.startColumn, this.endLine, this.endColumn);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public boolean canMerge(ContentAction contentAction) {
            if (!(contentAction instanceof DeleteAction)) {
                return false;
            }
            DeleteAction deleteAction = (DeleteAction) contentAction;
            return deleteAction.endColumn == this.startColumn && deleteAction.endLine == this.startLine && deleteAction.text.length() + this.text.length() < 10000 && Math.abs(deleteAction.createTime - this.createTime) < UndoManager.sMergeTimeLimit;
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void merge(ContentAction contentAction) {
            StringBuilder sb;
            if (!canMerge(contentAction)) {
                throw new IllegalArgumentException();
            }
            DeleteAction deleteAction = (DeleteAction) contentAction;
            this.startColumn = deleteAction.startColumn;
            this.startLine = deleteAction.startLine;
            CharSequence charSequence = this.text;
            if (charSequence instanceof StringBuilder) {
                sb = (StringBuilder) charSequence;
            } else {
                sb = new StringBuilder(this.text);
                this.text = sb;
            }
            sb.insert(0, deleteAction.text);
        }

        public String toString() {
            return "DeleteAction{startLine=" + this.startLine + ", endLine=" + this.endLine + ", startColumn=" + this.startColumn + ", endColumn=" + this.endColumn + ", createTime=" + this.createTime + ", text=" + ((Object) this.text) + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.startLine);
            parcel.writeInt(this.startColumn);
            parcel.writeInt(this.endLine);
            parcel.writeInt(this.endColumn);
            parcel.writeString(this.text.toString());
        }
    }

    public static final class ReplaceAction extends ContentAction {
        public static final Parcelable.Creator<ReplaceAction> CREATOR = new Parcelable.Creator<ReplaceAction>() { // from class: io.github.rosemoe.sora.text.UndoManager.ReplaceAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ReplaceAction createFromParcel(Parcel parcel) {
                ReplaceAction replaceAction = new ReplaceAction();
                replaceAction.insert = (InsertAction) parcel.readParcelable(ReplaceAction.class.getClassLoader());
                replaceAction.delete = (DeleteAction) parcel.readParcelable(ReplaceAction.class.getClassLoader());
                return replaceAction;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ReplaceAction[] newArray(int i) {
                return new ReplaceAction[i];
            }
        };
        public DeleteAction delete;
        public InsertAction insert;

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public boolean canMerge(ContentAction contentAction) {
            return false;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void undo(Content content) {
            this.insert.undo(content);
            this.delete.undo(content);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void redo(Content content) {
            this.delete.redo(content);
            this.insert.redo(content);
        }

        @Override // io.github.rosemoe.sora.text.UndoManager.ContentAction
        public void merge(ContentAction contentAction) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return "ReplaceAction{insert=" + this.insert + ", delete=" + this.delete + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.insert, i);
            parcel.writeParcelable(this.delete, i);
        }
    }
}
