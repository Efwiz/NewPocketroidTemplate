package io.github.rosemoe.sora.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import io.github.rosemoe.sora.I18nConfig;
import io.github.rosemoe.sora.R;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.PublishSearchResultEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.TextUtils;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.LongArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes11.dex */
public class EditorSearcher {
    protected String currentPattern;
    protected Thread currentThread;
    private boolean cyclicJumping = true;
    private final CodeEditor editor;
    protected LongArrayList lastResults;
    protected SearchOptions searchOptions;

    EditorSearcher(CodeEditor codeEditor) {
        this.editor = codeEditor;
        codeEditor.subscribeEvent(ContentChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.EditorSearcher$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$new$0((ContentChangeEvent) event, unsubscribe);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ContentChangeEvent contentChangeEvent, Unsubscribe unsubscribe) {
        if (hasQuery()) {
            executeMatch();
        }
    }

    public void setCyclicJumping(boolean z) {
        this.cyclicJumping = z;
    }

    public boolean isCyclicJumping() {
        return this.cyclicJumping;
    }

    public void search(String str, SearchOptions searchOptions) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("pattern length must be > 0");
        }
        if (searchOptions.type == 3) {
            Pattern.compile(str);
        }
        this.currentPattern = str;
        this.searchOptions = searchOptions;
        executeMatch();
        this.editor.postInvalidate();
    }

    private void executeMatch() {
        Thread thread = this.currentThread;
        if (thread != null && thread.isAlive()) {
            this.currentThread.interrupt();
        }
        Thread thread2 = new Thread(new SearchRunnable(this.editor.getText(), this.searchOptions, this.currentPattern));
        this.currentThread = thread2;
        thread2.start();
    }

    public void stopSearch() {
        Thread thread = this.currentThread;
        if (thread != null && thread.isAlive()) {
            this.currentThread.interrupt();
        }
        this.currentThread = null;
        this.lastResults = null;
        this.currentPattern = null;
        this.searchOptions = null;
        this.editor.dispatchEvent(new PublishSearchResultEvent(this.editor));
    }

    public boolean hasQuery() {
        return this.currentPattern != null;
    }

    private void checkState() {
        if (!hasQuery()) {
            throw new IllegalStateException("pattern not set");
        }
    }

    public int getCurrentMatchedPositionIndex() {
        LongArrayList longArrayList;
        long jPack;
        int iLowerBound;
        checkState();
        Cursor cursor = this.editor.getCursor();
        if (!cursor.isSelected()) {
            return -1;
        }
        int left = cursor.getLeft();
        int right = cursor.getRight();
        if (!isResultValid() || (longArrayList = this.lastResults) == null || (iLowerBound = longArrayList.lowerBound((jPack = IntPair.pack(left, right)))) >= longArrayList.size() || longArrayList.get(iLowerBound) != jPack) {
            return -1;
        }
        return iLowerBound;
    }

    public int getMatchedPositionCount() {
        LongArrayList longArrayList;
        checkState();
        if (isResultValid() && (longArrayList = this.lastResults) != null) {
            return longArrayList.size();
        }
        return 0;
    }

    public boolean gotoNext() {
        LongArrayList longArrayList;
        checkState();
        if (!isResultValid() || (longArrayList = this.lastResults) == null) {
            return false;
        }
        int iLowerBoundByFirst = longArrayList.lowerBoundByFirst(this.editor.getCursor().getRight());
        if (iLowerBoundByFirst == longArrayList.size() && this.cyclicJumping) {
            iLowerBoundByFirst = 0;
        }
        if (iLowerBoundByFirst < longArrayList.size()) {
            long j = longArrayList.get(iLowerBoundByFirst);
            CharPosition charPosition = this.editor.getText().getIndexer().getCharPosition(IntPair.getFirst(j));
            CharPosition charPosition2 = this.editor.getText().getIndexer().getCharPosition(IntPair.getSecond(j));
            this.editor.setSelectionRegion(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column, 6);
            return true;
        }
        return false;
    }

    public boolean gotoPrevious() {
        LongArrayList longArrayList;
        checkState();
        if (isResultValid() && (longArrayList = this.lastResults) != null && longArrayList.size() != 0) {
            int iLowerBoundByFirst = longArrayList.lowerBoundByFirst(this.editor.getCursor().getLeft());
            if (iLowerBoundByFirst == longArrayList.size() || IntPair.getFirst(longArrayList.get(iLowerBoundByFirst)) >= iLowerBoundByFirst) {
                iLowerBoundByFirst--;
            }
            if (iLowerBoundByFirst < 0 && this.cyclicJumping) {
                iLowerBoundByFirst = longArrayList.size() - 1;
            }
            if (iLowerBoundByFirst >= 0 && iLowerBoundByFirst < longArrayList.size()) {
                long j = longArrayList.get(iLowerBoundByFirst);
                int second = IntPair.getSecond(j);
                CharPosition charPosition = this.editor.getText().getIndexer().getCharPosition(IntPair.getFirst(j));
                CharPosition charPosition2 = this.editor.getText().getIndexer().getCharPosition(second);
                this.editor.setSelectionRegion(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column, 6);
                return true;
            }
        }
        return false;
    }

    public boolean isMatchedPositionSelected() {
        return getCurrentMatchedPositionIndex() > -1;
    }

    public void replaceThis(String str) {
        if (this.editor.isEditable()) {
            if (isMatchedPositionSelected()) {
                if (str.isEmpty()) {
                    this.editor.deleteText();
                    return;
                } else {
                    this.editor.commitText(str);
                    return;
                }
            }
            gotoNext();
        }
    }

    public void replaceAll(String str) {
        replaceAll(str, null);
    }

    public void replaceAll(final String str, final Runnable runnable) {
        if (this.editor.isEditable()) {
            checkState();
            if (!isResultValid()) {
                Toast.makeText(this.editor.getContext(), I18nConfig.getResourceId(R.string.sora_editor_editor_search_busy), 0).show();
                return;
            }
            Context context = this.editor.getContext();
            final ProgressDialog progressDialogShow = ProgressDialog.show(context, I18nConfig.getString(context, R.string.sora_editor_replaceAll), I18nConfig.getString(context, R.string.sora_editor_editor_search_replacing), true, false);
            final LongArrayList longArrayList = this.lastResults;
            new Thread(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorSearcher$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$replaceAll$3(str, longArrayList, progressDialogShow, runnable);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceAll$3(String str, LongArrayList longArrayList, final ProgressDialog progressDialog, final Runnable runnable) {
        try {
            final StringBuilder stringBuilder = this.editor.getText().toStringBuilder();
            int length = str.length();
            int i = 0;
            for (int i2 = 0; i2 < longArrayList.size(); i2++) {
                long j = longArrayList.get(i2);
                int first = IntPair.getFirst(j);
                int second = IntPair.getSecond(j);
                stringBuilder.replace(first + i, second + i, str);
                i += length - (second - first);
            }
            this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorSearcher$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$replaceAll$1(stringBuilder, progressDialog, runnable);
                }
            });
        } catch (Exception e) {
            this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorSearcher$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$replaceAll$2(e, progressDialog);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceAll$1(StringBuilder sb, ProgressDialog progressDialog, Runnable runnable) {
        CharPosition charPositionLeft = this.editor.getCursor().left();
        this.editor.getText().replace(0, 0, this.editor.getLineCount() - 1, this.editor.getText().getColumnCount(this.editor.getLineCount() - 1), sb);
        this.editor.setSelectionAround(charPositionLeft.line, charPositionLeft.column);
        progressDialog.dismiss();
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceAll$2(Exception exc, ProgressDialog progressDialog) {
        Toast.makeText(this.editor.getContext(), "Replace failed:" + exc, 0).show();
        progressDialog.dismiss();
    }

    protected boolean isResultValid() {
        Thread thread = this.currentThread;
        return thread == null || !thread.isAlive();
    }

    public static class SearchOptions {
        public static final int TYPE_NORMAL = 1;
        public static final int TYPE_REGULAR_EXPRESSION = 3;
        public static final int TYPE_WHOLE_WORD = 2;
        public final boolean caseInsensitive;
        public final int type;

        public SearchOptions(boolean z, boolean z2) {
            this(z2 ? 3 : 1, z);
        }

        public SearchOptions(int i, boolean z) {
            if (i < 1 || i > 3) {
                throw new IllegalArgumentException("invalid type");
            }
            this.type = i;
            this.caseInsensitive = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SearchRunnable implements Runnable {
        private Thread localThread;
        private final SearchOptions options;
        private final String pattern;
        private final StringBuilder text;

        public SearchRunnable(Content content, SearchOptions searchOptions, String str) {
            this.text = content.toStringBuilder();
            this.options = searchOptions;
            this.pattern = str;
        }

        private boolean checkNotCancelled() {
            return EditorSearcher.this.currentThread == this.localThread && !Thread.interrupted();
        }

        @Override // java.lang.Runnable
        public void run() {
            this.localThread = Thread.currentThread();
            final LongArrayList longArrayList = new LongArrayList();
            int length = this.text.length();
            boolean z = EditorSearcher.this.searchOptions.caseInsensitive;
            String str = this.pattern;
            int i = this.options.type;
            int iIndexOf = 0;
            if (i == 1) {
                int length2 = str.length();
                while (iIndexOf != -1 && iIndexOf < length && checkNotCancelled()) {
                    iIndexOf = TextUtils.indexOf(this.text, str, z, iIndexOf);
                    if (iIndexOf != -1) {
                        int i2 = iIndexOf + length2;
                        longArrayList.add(IntPair.pack(iIndexOf, i2));
                        iIndexOf = i2;
                    }
                }
            } else {
                if (i == 2) {
                    str = "\\b" + Pattern.quote(str) + "\\b";
                } else if (i == 3) {
                }
                Matcher matcher = Pattern.compile(str, (z ? 2 : 0) | 8).matcher(this.text.toString());
                while (iIndexOf < length && matcher.find(iIndexOf) && checkNotCancelled()) {
                    iIndexOf = matcher.end();
                    if (matcher.start() == iIndexOf) {
                        iIndexOf++;
                    } else {
                        longArrayList.add(IntPair.pack(matcher.start(), iIndexOf));
                    }
                }
            }
            if (checkNotCancelled()) {
                EditorSearcher.this.editor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorSearcher$SearchRunnable$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$run$0(longArrayList);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(LongArrayList longArrayList) {
            if (EditorSearcher.this.currentThread == this.localThread) {
                EditorSearcher.this.lastResults = longArrayList;
                EditorSearcher.this.editor.invalidate();
                EditorSearcher.this.editor.dispatchEvent(new PublishSearchResultEvent(EditorSearcher.this.editor));
                EditorSearcher.this.currentThread = null;
            }
        }
    }
}
