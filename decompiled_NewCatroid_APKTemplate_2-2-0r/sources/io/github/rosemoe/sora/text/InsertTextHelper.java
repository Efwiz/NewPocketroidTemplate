package io.github.rosemoe.sora.text;

/* JADX INFO: loaded from: classes11.dex */
class InsertTextHelper {
    static int TYPE_EOF = 2;
    static int TYPE_LINE_CONTENT = 0;
    static int TYPE_NEWLINE = 1;
    private static final InsertTextHelper[] sCached = new InsertTextHelper[8];
    private int index;
    private int indexNext;
    private int length;
    private CharSequence text;

    InsertTextHelper() {
    }

    private static synchronized InsertTextHelper obtain() {
        int i = 0;
        while (true) {
            InsertTextHelper[] insertTextHelperArr = sCached;
            if (i < insertTextHelperArr.length) {
                InsertTextHelper insertTextHelper = insertTextHelperArr[i];
                if (insertTextHelper != null) {
                    insertTextHelperArr[i] = null;
                    return insertTextHelper;
                }
                i++;
            } else {
                return new InsertTextHelper();
            }
        }
    }

    public static InsertTextHelper forInsertion(CharSequence charSequence) {
        InsertTextHelper insertTextHelperObtain = obtain();
        insertTextHelperObtain.init(charSequence);
        return insertTextHelperObtain;
    }

    public void recycle() {
        synchronized (InsertTextHelper.class) {
            int i = 0;
            while (true) {
                InsertTextHelper[] insertTextHelperArr = sCached;
                if (i >= insertTextHelperArr.length) {
                    break;
                }
                if (insertTextHelperArr[i] == null) {
                    insertTextHelperArr[i] = this;
                    reset();
                    break;
                }
                i++;
            }
        }
    }

    private void init(CharSequence charSequence) {
        this.text = charSequence;
        this.index = -1;
        this.indexNext = 0;
        this.length = charSequence.length();
    }

    public int getIndex() {
        return this.index;
    }

    public int getIndexNext() {
        return this.indexNext;
    }

    public int forward() {
        char cCharAt;
        int i = this.indexNext;
        this.index = i;
        if (i == this.length) {
            return TYPE_EOF;
        }
        char cCharAt2 = this.text.charAt(i);
        if (cCharAt2 == '\n') {
            this.indexNext = this.index + 1;
            return TYPE_NEWLINE;
        }
        if (cCharAt2 == '\r') {
            int i2 = this.index;
            if (i2 + 1 < this.length && this.text.charAt(i2 + 1) == '\n') {
                this.indexNext = this.index + 2;
            } else {
                this.indexNext = this.index + 1;
            }
            return TYPE_NEWLINE;
        }
        this.indexNext = this.index + 1;
        while (true) {
            int i3 = this.indexNext;
            if (i3 >= this.length || (cCharAt = this.text.charAt(i3)) == '\n' || cCharAt == '\r') {
                break;
            }
            this.indexNext++;
        }
        return TYPE_LINE_CONTENT;
    }

    public void reset() {
        this.text = null;
        this.index = 0;
        this.length = 0;
    }
}
