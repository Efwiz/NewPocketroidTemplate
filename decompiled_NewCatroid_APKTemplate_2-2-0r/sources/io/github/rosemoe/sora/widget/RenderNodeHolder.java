package io.github.rosemoe.sora.widget;

import android.graphics.Canvas;
import android.graphics.RenderNode;
import io.github.rosemoe.sora.lang.analysis.StyleUpdateRange;
import io.github.rosemoe.sora.lang.styling.EmptyReader;
import io.github.rosemoe.sora.lang.styling.Spans;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes11.dex */
public class RenderNodeHolder {
    private final CodeEditor editor;
    private final Stack<TextRenderNode> pool = new Stack<>();
    private final ArrayList<TextRenderNode> cache = new ArrayList<>(64);

    public RenderNodeHolder(CodeEditor codeEditor) {
        this.editor = codeEditor;
    }

    private boolean shouldUpdateCache() {
        return !this.editor.isWordwrap() && this.editor.isHardwareAcceleratedDrawAllowed();
    }

    public boolean invalidateInRegion(StyleUpdateRange styleUpdateRange) {
        Iterator<TextRenderNode> it = this.cache.iterator();
        boolean z = false;
        while (it.hasNext()) {
            TextRenderNode next = it.next();
            if (styleUpdateRange.isInRange(next.line)) {
                it.remove();
                next.renderNode.discardDisplayList();
                this.pool.push(next);
                z = true;
            }
        }
        return z;
    }

    public boolean invalidateInRegion(int i, int i2) {
        Iterator<TextRenderNode> it = this.cache.iterator();
        boolean z = false;
        while (it.hasNext()) {
            TextRenderNode next = it.next();
            if (next.line >= i) {
                it.remove();
                next.renderNode.discardDisplayList();
                this.pool.push(next);
                z = true;
            }
        }
        return z;
    }

    public void invalidate() {
        this.cache.forEach(new Consumer() { // from class: io.github.rosemoe.sora.widget.RenderNodeHolder$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((RenderNodeHolder.TextRenderNode) obj).isDirty = true;
            }
        });
    }

    public TextRenderNode getNode(int i) {
        int size = this.cache.size();
        for (int i2 = 0; i2 < size; i2++) {
            TextRenderNode textRenderNode = this.cache.get(i2);
            if (textRenderNode.line == i) {
                Collections.swap(this.cache, 0, i2);
                return textRenderNode;
            }
        }
        TextRenderNode textRenderNode2 = this.pool.isEmpty() ? new TextRenderNode(i) : this.pool.pop();
        textRenderNode2.line = i;
        textRenderNode2.isDirty = true;
        this.cache.add(0, textRenderNode2);
        return textRenderNode2;
    }

    public void keepCurrentInDisplay(int i, int i2) {
        Iterator<TextRenderNode> it = this.cache.iterator();
        while (it.hasNext()) {
            TextRenderNode next = it.next();
            if (next.line < i || next.line > i2) {
                it.remove();
                next.renderNode.discardDisplayList();
            }
        }
    }

    public int drawLineHardwareAccelerated(Canvas canvas, int i, float f, float f2) {
        if (!canvas.isHardwareAccelerated()) {
            throw new UnsupportedOperationException("Only hardware-accelerated canvas can be used");
        }
        Styles styles = this.editor.getStyles();
        TextRenderNode node = getNode(i);
        if (node.needsRecord()) {
            Spans spans = styles == null ? null : styles.spans;
            Spans.Reader emptyReader = spans == null ? EmptyReader.getInstance() : spans.read();
            try {
                emptyReader.moveToLine(i);
            } catch (Exception unused) {
                emptyReader = EmptyReader.getInstance();
            }
            this.editor.getRenderer().updateLineDisplayList(node.renderNode, i, emptyReader);
            try {
                emptyReader.moveToLine(-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            node.isDirty = false;
        }
        canvas.save();
        canvas.translate(f, f2);
        canvas.drawRenderNode(node.renderNode);
        canvas.restore();
        return node.renderNode.getWidth();
    }

    public void afterInsert(final int i, final int i2) {
        this.cache.forEach(new Consumer() { // from class: io.github.rosemoe.sora.widget.RenderNodeHolder$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RenderNodeHolder.lambda$afterInsert$1(i, i2, (RenderNodeHolder.TextRenderNode) obj);
            }
        });
    }

    static /* synthetic */ void lambda$afterInsert$1(int i, int i2, TextRenderNode textRenderNode) {
        if (textRenderNode.line == i) {
            textRenderNode.isDirty = true;
        } else if (textRenderNode.line > i) {
            textRenderNode.line += i2 - i;
        }
    }

    public void afterDelete(final int i, final int i2) {
        final ArrayList arrayList = new ArrayList();
        this.cache.forEach(new Consumer() { // from class: io.github.rosemoe.sora.widget.RenderNodeHolder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RenderNodeHolder.lambda$afterDelete$2(i, i2, arrayList, (RenderNodeHolder.TextRenderNode) obj);
            }
        });
        this.cache.removeAll(arrayList);
        this.pool.addAll(arrayList);
    }

    static /* synthetic */ void lambda$afterDelete$2(int i, int i2, List list, TextRenderNode textRenderNode) {
        if (textRenderNode.line == i) {
            textRenderNode.isDirty = true;
            return;
        }
        if (textRenderNode.line > i && textRenderNode.line <= i2) {
            list.add(textRenderNode);
            textRenderNode.renderNode.discardDisplayList();
        } else if (textRenderNode.line > i2) {
            textRenderNode.line -= i2 - i;
        }
    }

    protected static class TextRenderNode {
        public int line;
        public RenderNode renderNode = new RenderNode("editorRenderNode");
        public boolean isDirty = true;

        public TextRenderNode(int i) {
            this.line = i;
        }

        public boolean needsRecord() {
            return this.isDirty || !this.renderNode.hasDisplayList();
        }
    }
}
