package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextArea.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextArea extends TextField {
    int cursorLine;
    int firstLineShowing;
    private String lastText;
    IntArray linesBreak;
    private int linesShowing;
    float moveOffset;
    private float prefRows;

    public TextArea(String str, Skin skin) {
        super(str, skin);
    }

    public TextArea(String str, Skin skin, String str2) {
        super(str, skin, str2);
    }

    public TextArea(String str, TextField.TextFieldStyle textFieldStyle) {
        super(str, textFieldStyle);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void initialize() {
        super.initialize();
        this.writeEnters = true;
        this.linesBreak = new IntArray();
        this.cursorLine = 0;
        this.firstLineShowing = 0;
        this.moveOffset = -1.0f;
        this.linesShowing = 0;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected int letterUnderCursor(float f) {
        if (this.linesBreak.size <= 0) {
            return 0;
        }
        if (this.cursorLine * 2 >= this.linesBreak.size) {
            return this.text.length();
        }
        float[] fArr = this.glyphPositions.items;
        int i = this.linesBreak.items[this.cursorLine * 2];
        float f2 = f + fArr[i];
        int i2 = this.linesBreak.items[(this.cursorLine * 2) + 1];
        while (i < i2 && fArr[i] <= f2) {
            i++;
        }
        return (i <= 0 || fArr[i] - f2 > f2 - fArr[i + (-1)]) ? Math.max(0, i - 1) : i;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    public void setStyle(TextField.TextFieldStyle textFieldStyle) {
        if (textFieldStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = textFieldStyle;
        this.textHeight = textFieldStyle.font.getCapHeight() - textFieldStyle.font.getDescent();
        if (this.text != null) {
            updateDisplayText();
        }
        invalidateHierarchy();
    }

    public void setPrefRows(float f) {
        this.prefRows = f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField, com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        if (this.prefRows <= 0.0f) {
            return super.getPrefHeight();
        }
        float fCeil = (float) Math.ceil(this.style.font.getLineHeight() * this.prefRows);
        return this.style.background != null ? Math.max(fCeil + this.style.background.getBottomHeight() + this.style.background.getTopHeight(), this.style.background.getMinHeight()) : fCeil;
    }

    public int getLines() {
        return (this.linesBreak.size / 2) + (newLineAtEnd() ? 1 : 0);
    }

    public boolean newLineAtEnd() {
        return this.text.length() != 0 && (this.text.charAt(this.text.length() - 1) == '\n' || this.text.charAt(this.text.length() - 1) == '\r');
    }

    public void moveCursorLine(int i) {
        if (i < 0) {
            this.cursorLine = 0;
            this.cursor = 0;
            this.moveOffset = -1.0f;
            return;
        }
        if (i >= getLines()) {
            int lines = getLines() - 1;
            this.cursor = this.text.length();
            if (i > getLines() || lines == this.cursorLine) {
                this.moveOffset = -1.0f;
            }
            this.cursorLine = lines;
            return;
        }
        if (i != this.cursorLine) {
            if (this.moveOffset < 0.0f) {
                this.moveOffset = this.linesBreak.size > this.cursorLine * 2 ? this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.linesBreak.get(this.cursorLine * 2)) : 0.0f;
            }
            this.cursorLine = i;
            this.cursor = i * 2 >= this.linesBreak.size ? this.text.length() : this.linesBreak.get(this.cursorLine * 2);
            while (this.cursor < this.text.length() && this.cursor <= this.linesBreak.get((this.cursorLine * 2) + 1) - 1 && this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.linesBreak.get(this.cursorLine * 2)) < this.moveOffset) {
                this.cursor++;
            }
            showCursor();
        }
    }

    void updateCurrentLine() {
        int i;
        int iCalculateCurrentLineIndex = calculateCurrentLineIndex(this.cursor);
        int i2 = iCalculateCurrentLineIndex / 2;
        if ((iCalculateCurrentLineIndex % 2 == 0 || (i = iCalculateCurrentLineIndex + 1) >= this.linesBreak.size || this.cursor != this.linesBreak.items[iCalculateCurrentLineIndex] || this.linesBreak.items[i] != this.linesBreak.items[iCalculateCurrentLineIndex]) && (i2 < this.linesBreak.size / 2 || this.text.length() == 0 || this.text.charAt(this.text.length() - 1) == '\n' || this.text.charAt(this.text.length() - 1) == '\r')) {
            this.cursorLine = i2;
        }
        updateFirstLineShowing();
    }

    void showCursor() {
        updateCurrentLine();
        updateFirstLineShowing();
    }

    void updateFirstLineShowing() {
        int i = this.cursorLine;
        int i2 = this.firstLineShowing;
        if (i == i2) {
            return;
        }
        int i3 = i >= i2 ? 1 : -1;
        while (true) {
            int i4 = this.firstLineShowing;
            int i5 = this.cursorLine;
            if (i4 <= i5 && (this.linesShowing + i4) - 1 >= i5) {
                return;
            } else {
                this.firstLineShowing = i4 + i3;
            }
        }
    }

    private int calculateCurrentLineIndex(int i) {
        int i2 = 0;
        while (i2 < this.linesBreak.size && i > this.linesBreak.items[i2]) {
            i2++;
        }
        return i2;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    protected void sizeChanged() {
        float topHeight;
        this.lastText = null;
        BitmapFont bitmapFont = this.style.font;
        Drawable drawable = this.style.background;
        float height = getHeight();
        if (drawable == null) {
            topHeight = 0.0f;
        } else {
            topHeight = drawable.getTopHeight() + drawable.getBottomHeight();
        }
        this.linesShowing = (int) Math.floor((height - topHeight) / bitmapFont.getLineHeight());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected float getTextY(BitmapFont bitmapFont, Drawable drawable) {
        float height = getHeight();
        if (drawable != null) {
            height -= drawable.getTopHeight();
        }
        return bitmapFont.usesIntegerPositions() ? (int) height : height;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void drawSelection(Drawable drawable, Batch batch, BitmapFont bitmapFont, float f, float f2) {
        float f3;
        float f4;
        int i = this.firstLineShowing * 2;
        int iMin = Math.min(this.cursor, this.selectionStart);
        int iMax = Math.max(this.cursor, this.selectionStart);
        BitmapFont.BitmapFontData data = bitmapFont.getData();
        float lineHeight = this.style.font.getLineHeight();
        float lineHeight2 = 0.0f;
        while (true) {
            int i2 = i + 1;
            if (i2 >= this.linesBreak.size || i >= (this.firstLineShowing + this.linesShowing) * 2) {
                return;
            }
            int i3 = this.linesBreak.get(i);
            int i4 = this.linesBreak.get(i2);
            if ((iMin >= i3 || iMin >= i4 || iMax >= i3 || iMax >= i4) && (iMin <= i3 || iMin <= i4 || iMax <= i3 || iMax <= i4)) {
                int iMax2 = Math.max(i3, iMin);
                int iMin2 = Math.min(i4, iMax);
                BitmapFont.Glyph glyph = data.getGlyph(this.displayText.charAt(i3));
                if (glyph == null) {
                    f3 = 0.0f;
                    f4 = 0.0f;
                } else if (iMax2 == i3) {
                    f4 = glyph.fixedWidth ? 0.0f : ((-glyph.xoffset) * data.scaleX) - data.padLeft;
                    f3 = 0.0f;
                } else {
                    f3 = glyph.fixedWidth ? 0.0f : ((-glyph.xoffset) * data.scaleX) - data.padLeft;
                    f4 = 0.0f;
                }
                drawable.draw(batch, f + (this.glyphPositions.get(iMax2) - this.glyphPositions.get(i3)) + f3, (f2 - lineHeight) - lineHeight2, (this.glyphPositions.get(iMin2) - this.glyphPositions.get(iMax2)) + f4, bitmapFont.getLineHeight());
            }
            lineHeight2 += bitmapFont.getLineHeight();
            i += 2;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void drawText(Batch batch, BitmapFont bitmapFont, float f, float f2) {
        float lineHeight = (-(this.style.font.getLineHeight() - this.textHeight)) / 2.0f;
        for (int i = this.firstLineShowing * 2; i < (this.firstLineShowing + this.linesShowing) * 2 && i < this.linesBreak.size; i += 2) {
            bitmapFont.draw(batch, this.displayText, f, f2 + lineHeight, this.linesBreak.items[i], this.linesBreak.items[i + 1], 0.0f, 8, false);
            lineHeight -= bitmapFont.getLineHeight();
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void drawCursor(Drawable drawable, Batch batch, BitmapFont bitmapFont, float f, float f2) {
        drawable.draw(batch, f + getCursorX(), f2 + getCursorY(), drawable.getMinWidth(), bitmapFont.getLineHeight());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void calculateOffsets() {
        super.calculateOffsets();
        if (this.text.equals(this.lastText)) {
            return;
        }
        this.lastText = this.text;
        BitmapFont bitmapFont = this.style.font;
        float width = getWidth() - (this.style.background != null ? this.style.background.getLeftWidth() + this.style.background.getRightWidth() : 0.0f);
        this.linesBreak.clear();
        Pool pool = Pools.get(GlyphLayout.class);
        GlyphLayout glyphLayout = (GlyphLayout) pool.obtain();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.text.length(); i3++) {
            char cCharAt = this.text.charAt(i3);
            if (cCharAt == '\r' || cCharAt == '\n') {
                this.linesBreak.add(i);
                this.linesBreak.add(i3);
                i = i3 + 1;
            } else {
                if (!continueCursor(i3, 0)) {
                    i2 = i3;
                }
                glyphLayout.setText(bitmapFont, this.text.subSequence(i, i3 + 1));
                if (glyphLayout.width > width) {
                    if (i >= i2) {
                        i2 = i3 - 1;
                    }
                    this.linesBreak.add(i);
                    i2++;
                    this.linesBreak.add(i2);
                    i = i2;
                }
            }
        }
        pool.free(glyphLayout);
        if (i < this.text.length()) {
            this.linesBreak.add(i);
            this.linesBreak.add(this.text.length());
        }
        showCursor();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected InputListener createInputListener() {
        return new TextAreaListener();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    public void setSelection(int i, int i2) {
        super.setSelection(i, i2);
        updateCurrentLine();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected void moveCursor(boolean z, boolean z2) {
        int i;
        int i2 = z ? 1 : -1;
        int i3 = (this.cursorLine * 2) + i2;
        if (i3 >= 0 && (i = i3 + 1) < this.linesBreak.size && this.linesBreak.items[i3] == this.cursor && this.linesBreak.items[i] == this.cursor) {
            this.cursorLine += i2;
            if (z2) {
                super.moveCursor(z, z2);
            }
            showCursor();
        } else {
            super.moveCursor(z, z2);
        }
        updateCurrentLine();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField
    protected boolean continueCursor(int i, int i2) {
        int iCalculateCurrentLineIndex = calculateCurrentLineIndex(i + i2);
        if (super.continueCursor(i, i2)) {
            if (iCalculateCurrentLineIndex >= 0 && iCalculateCurrentLineIndex < this.linesBreak.size - 2) {
                int i3 = iCalculateCurrentLineIndex + 1;
                if (this.linesBreak.items[i3] != i || this.linesBreak.items[i3] == this.linesBreak.items[iCalculateCurrentLineIndex + 2]) {
                }
            }
            return true;
        }
        return false;
    }

    public int getCursorLine() {
        return this.cursorLine;
    }

    public int getFirstLineShowing() {
        return this.firstLineShowing;
    }

    public int getLinesShowing() {
        return this.linesShowing;
    }

    public float getCursorX() {
        BitmapFont.BitmapFontData data = this.style.font.getData();
        float f = 0.0f;
        if (this.cursor < this.glyphPositions.size && this.cursorLine * 2 < this.linesBreak.size) {
            int i = this.linesBreak.items[this.cursorLine * 2];
            BitmapFont.Glyph glyph = data.getGlyph(this.displayText.charAt(i));
            if (glyph != null && !glyph.fixedWidth) {
                f = ((-glyph.xoffset) * data.scaleX) - data.padLeft;
            }
            f += this.glyphPositions.get(this.cursor) - this.glyphPositions.get(i);
        }
        return f + data.cursorX;
    }

    public float getCursorY() {
        return (-((this.cursorLine - this.firstLineShowing) + 1)) * this.style.font.getLineHeight();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextArea$TextAreaListener.class
      classes8.dex
     */
    public class TextAreaListener extends TextField.TextFieldClickListener {
        public TextAreaListener() {
            super();
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener
        protected void setCursorPosition(float f, float f2) {
            TextArea.this.moveOffset = -1.0f;
            Drawable drawable = TextArea.this.style.background;
            BitmapFont bitmapFont = TextArea.this.style.font;
            float height = TextArea.this.getHeight();
            if (drawable != null) {
                height -= drawable.getTopHeight();
                f -= drawable.getLeftWidth();
            }
            float fMax = Math.max(0.0f, f);
            if (drawable != null) {
                f2 -= drawable.getTopHeight();
            }
            TextArea.this.cursorLine = ((int) Math.floor((height - f2) / bitmapFont.getLineHeight())) + TextArea.this.firstLineShowing;
            TextArea textArea = TextArea.this;
            textArea.cursorLine = Math.max(0, Math.min(textArea.cursorLine, TextArea.this.getLines() - 1));
            super.setCursorPosition(fMax, f2);
            TextArea.this.updateCurrentLine();
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener, com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean keyDown(InputEvent inputEvent, int i) {
            boolean zKeyDown = super.keyDown(inputEvent, i);
            if (!TextArea.this.hasKeyboardFocus()) {
                return zKeyDown;
            }
            boolean z = Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60);
            if (i == 20) {
                if (z) {
                    if (!TextArea.this.hasSelection) {
                        TextArea textArea = TextArea.this;
                        textArea.selectionStart = textArea.cursor;
                        TextArea.this.hasSelection = true;
                    }
                } else {
                    TextArea.this.clearSelection();
                }
                TextArea textArea2 = TextArea.this;
                textArea2.moveCursorLine(textArea2.cursorLine + 1);
            } else {
                if (i == 19) {
                    if (z) {
                        if (!TextArea.this.hasSelection) {
                            TextArea textArea3 = TextArea.this;
                            textArea3.selectionStart = textArea3.cursor;
                            TextArea.this.hasSelection = true;
                        }
                    } else {
                        TextArea.this.clearSelection();
                    }
                    TextArea textArea4 = TextArea.this;
                    textArea4.moveCursorLine(textArea4.cursorLine - 1);
                } else {
                    TextArea.this.moveOffset = -1.0f;
                }
                TextArea.this.showCursor();
                return true;
            }
            scheduleKeyRepeatTask(i);
            TextArea.this.showCursor();
            return true;
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener
        protected boolean checkFocusTraversal(char c) {
            return TextArea.this.focusTraversal && c == '\t';
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener, com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean keyTyped(InputEvent inputEvent, char c) {
            boolean zKeyTyped = super.keyTyped(inputEvent, c);
            TextArea.this.showCursor();
            return zKeyTyped;
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener
        protected void goHome(boolean z) {
            if (z) {
                TextArea.this.cursor = 0;
            } else if (TextArea.this.cursorLine * 2 < TextArea.this.linesBreak.size) {
                TextArea textArea = TextArea.this;
                textArea.cursor = textArea.linesBreak.get(TextArea.this.cursorLine * 2);
            }
        }

        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener
        protected void goEnd(boolean z) {
            if (z || TextArea.this.cursorLine >= TextArea.this.getLines()) {
                TextArea textArea = TextArea.this;
                textArea.cursor = textArea.text.length();
            } else if ((TextArea.this.cursorLine * 2) + 1 < TextArea.this.linesBreak.size) {
                TextArea textArea2 = TextArea.this;
                textArea2.cursor = textArea2.linesBreak.get((TextArea.this.cursorLine * 2) + 1);
            }
        }
    }
}
