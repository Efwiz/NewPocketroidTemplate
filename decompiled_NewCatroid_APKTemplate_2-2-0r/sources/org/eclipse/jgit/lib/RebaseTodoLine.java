package org.eclipse.jgit.lib;

import java.text.MessageFormat;
import org.eclipse.jgit.errors.IllegalTodoFileModification;
import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes13.dex */
public class RebaseTodoLine {
    Action action;
    String comment;
    final AbbreviatedObjectId commit;
    String shortMessage;

    public enum Action {
        PICK("pick", "p"),
        REWORD("reword", "r"),
        EDIT("edit", "e"),
        SQUASH("squash", "s"),
        FIXUP("fixup", "f"),
        COMMENT("comment", "#");

        private final String shortToken;
        private final String token;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Action[] valuesCustom() {
            Action[] actionArrValuesCustom = values();
            int length = actionArrValuesCustom.length;
            Action[] actionArr = new Action[length];
            System.arraycopy(actionArrValuesCustom, 0, actionArr, 0, length);
            return actionArr;
        }

        Action(String str, String str2) {
            this.token = str;
            this.shortToken = str2;
        }

        public String toToken() {
            return this.token;
        }

        @Override // java.lang.Enum
        public String toString() {
            return "Action[" + this.token + "]";
        }

        public static Action parse(String str) {
            for (Action action : valuesCustom()) {
                if (action.token.equals(str) || action.shortToken.equals(str)) {
                    return action;
                }
            }
            throw new IllegalArgumentException(MessageFormat.format(JGitText.get().unknownOrUnsupportedCommand, str, valuesCustom()));
        }
    }

    public RebaseTodoLine(String str) {
        this.action = Action.COMMENT;
        setComment(str);
        this.commit = null;
        this.shortMessage = null;
    }

    public RebaseTodoLine(Action action, AbbreviatedObjectId abbreviatedObjectId, String str) {
        this.action = action;
        this.commit = abbreviatedObjectId;
        this.shortMessage = str;
        this.comment = null;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) throws IllegalTodoFileModification {
        if (!Action.COMMENT.equals(this.action) && Action.COMMENT.equals(action)) {
            if (this.comment == null) {
                StringBuilder sbAppend = new StringBuilder("# ").append(this.action.token).append(" ");
                AbbreviatedObjectId abbreviatedObjectId = this.commit;
                StringBuilder sbAppend2 = sbAppend.append(abbreviatedObjectId == null ? "null" : abbreviatedObjectId.name()).append(" ");
                String str = this.shortMessage;
                this.comment = sbAppend2.append(str != null ? str : "null").toString();
            }
        } else if (Action.COMMENT.equals(this.action) && !Action.COMMENT.equals(action) && this.commit == null) {
            throw new IllegalTodoFileModification(MessageFormat.format(JGitText.get().cannotChangeActionOnComment, this.action, action));
        }
        this.action = action;
    }

    public void setComment(String str) {
        if (str == null) {
            this.comment = null;
            return;
        }
        if (str.contains("\n") || str.contains("\r")) {
            throw createInvalidCommentException(str);
        }
        if (str.trim().length() == 0 || str.startsWith("#")) {
            this.comment = str;
            return;
        }
        throw createInvalidCommentException(str);
    }

    private static IllegalArgumentException createInvalidCommentException(String str) {
        return new IllegalArgumentException(MessageFormat.format(JGitText.get().argumentIsNotAValidCommentString, str));
    }

    public AbbreviatedObjectId getCommit() {
        return this.commit;
    }

    public String getShortMessage() {
        return this.shortMessage;
    }

    public void setShortMessage(String str) {
        this.shortMessage = str;
    }

    public String getComment() {
        return this.comment;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("Step[").append(this.action).append(", ");
        Object obj = this.commit;
        if (obj == null) {
            obj = "null";
        }
        StringBuilder sbAppend2 = sbAppend.append(obj).append(", ");
        String str = this.shortMessage;
        StringBuilder sbAppend3 = sbAppend2.append(str != null ? str : "null").append(", ");
        String str2 = this.comment;
        if (str2 == null) {
            str2 = "";
        }
        return sbAppend3.append(str2).append("]").toString();
    }
}
