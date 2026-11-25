package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Timer;
import io.netty.util.internal.StringUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextField extends Widget implements Disableable {
    protected static final char BACKSPACE = '\b';
    protected static final char BULLET = 149;
    protected static final char CARRIAGE_RETURN = '\r';
    protected static final char DELETE = 127;
    protected static final char NEWLINE = '\n';
    protected static final char TAB = '\t';
    final Timer.Task blinkTask;
    float blinkTime;
    Clipboard clipboard;
    protected int cursor;
    boolean cursorOn;
    boolean disabled;
    protected CharSequence displayText;
    TextFieldFilter filter;
    boolean focusTraversal;
    boolean focused;
    protected float fontOffset;
    protected final FloatArray glyphPositions;
    protected boolean hasSelection;
    InputListener inputListener;
    final KeyRepeatTask keyRepeatTask;
    OnscreenKeyboard keyboard;
    long lastChangeTime;
    protected final GlyphLayout layout;
    TextFieldListener listener;
    private int maxLength;
    private String messageText;
    boolean onlyFontChars;
    private StringBuilder passwordBuffer;
    private char passwordCharacter;
    boolean passwordMode;
    boolean programmaticChangeEvents;
    float renderOffset;
    protected int selectionStart;
    private float selectionWidth;
    private float selectionX;
    TextFieldStyle style;
    protected String text;
    private int textHAlign;
    protected float textHeight;
    protected float textOffset;
    String undoText;
    protected int visibleTextEnd;
    protected int visibleTextStart;
    protected boolean writeEnters;
    private static final Vector2 tmp1 = new Vector2();
    private static final Vector2 tmp2 = new Vector2();
    private static final Vector2 tmp3 = new Vector2();
    public static float keyRepeatInitialTime = 0.4f;
    public static float keyRepeatTime = 0.1f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$OnscreenKeyboard.class
      classes8.dex
     */
    public interface OnscreenKeyboard {
        void show(boolean z);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$TextFieldListener.class
      classes8.dex
     */
    public interface TextFieldListener {
        void keyTyped(TextField textField, char c);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefWidth() {
        return 150.0f;
    }

    public TextField(String str, Skin skin) {
        this(str, (TextFieldStyle) skin.get(TextFieldStyle.class));
    }

    public TextField(String str, Skin skin, String str2) {
        this(str, (TextFieldStyle) skin.get(str2, TextFieldStyle.class));
    }

    public TextField(String str, TextFieldStyle textFieldStyle) {
        this.layout = new GlyphLayout();
        this.glyphPositions = new FloatArray();
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.textHAlign = 8;
        this.undoText = "";
        this.passwordCharacter = BULLET;
        this.blinkTime = 0.32f;
        this.blinkTask = new Timer.Task() { // from class: com.badlogic.gdx.scenes.scene2d.ui.TextField.1
            @Override // com.badlogic.gdx.utils.Timer.Task, java.lang.Runnable
            public void run() {
                if (TextField.this.getStage() == null) {
                    cancel();
                    return;
                }
                TextField textField = TextField.this;
                textField.cursorOn = !textField.cursorOn;
                Gdx.graphics.requestRendering();
            }
        };
        this.keyRepeatTask = new KeyRepeatTask();
        setStyle(textFieldStyle);
        this.clipboard = Gdx.f82app.getClipboard();
        initialize();
        setText(str);
        setSize(getPrefWidth(), getPrefHeight());
    }

    protected void initialize() {
        InputListener inputListenerCreateInputListener = createInputListener();
        this.inputListener = inputListenerCreateInputListener;
        addListener(inputListenerCreateInputListener);
    }

    protected InputListener createInputListener() {
        return new TextFieldClickListener();
    }

    protected int letterUnderCursor(float f) {
        float leftWidth = f - (((this.textOffset + this.fontOffset) - this.style.font.getData().cursorX) - this.glyphPositions.get(this.visibleTextStart));
        if (getBackgroundDrawable() != null) {
            leftWidth -= this.style.background.getLeftWidth();
        }
        int i = this.glyphPositions.size;
        float[] fArr = this.glyphPositions.items;
        for (int i2 = 1; i2 < i; i2++) {
            float f2 = fArr[i2];
            if (f2 > leftWidth) {
                int i3 = i2 - 1;
                return f2 - leftWidth <= leftWidth - fArr[i3] ? i2 : i3;
            }
        }
        return i - 1;
    }

    protected boolean isWordCharacter(char c) {
        return Character.isLetterOrDigit(c);
    }

    protected int[] wordUnderCursor(int i) {
        String str = this.text;
        int length = str.length();
        int length2 = 0;
        if (i >= str.length()) {
            length = 0;
            length2 = str.length();
        } else {
            for (int i2 = i; i2 < length; i2++) {
                if (!isWordCharacter(str.charAt(i2))) {
                    length = i2;
                    break;
                }
            }
            do {
                i--;
                if (i > -1) {
                }
            } while (isWordCharacter(str.charAt(i)));
            length2 = i + 1;
        }
        return new int[]{length2, length};
    }

    int[] wordUnderCursor(float f) {
        return wordUnderCursor(letterUnderCursor(f));
    }

    boolean withinMaxLength(int i) {
        int i2 = this.maxLength;
        return i2 <= 0 || i < i2;
    }

    public void setMaxLength(int i) {
        this.maxLength = i;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setOnlyFontChars(boolean z) {
        this.onlyFontChars = z;
    }

    public void setStyle(TextFieldStyle textFieldStyle) {
        if (textFieldStyle == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = textFieldStyle;
        this.textHeight = textFieldStyle.font.getCapHeight() - (textFieldStyle.font.getDescent() * 2.0f);
        if (this.text != null) {
            updateDisplayText();
        }
        invalidateHierarchy();
    }

    public TextFieldStyle getStyle() {
        return this.style;
    }

    protected void calculateOffsets() {
        float width = getWidth();
        Drawable backgroundDrawable = getBackgroundDrawable();
        if (backgroundDrawable != null) {
            width -= backgroundDrawable.getLeftWidth() + backgroundDrawable.getRightWidth();
        }
        int i = this.glyphPositions.size;
        float[] fArr = this.glyphPositions.items;
        int i2 = i - 1;
        int iClamp = MathUtils.clamp(this.cursor, 0, i2);
        this.cursor = iClamp;
        float f = fArr[Math.max(0, iClamp - 1)];
        float f2 = this.renderOffset;
        float f3 = f + f2;
        float f4 = 0.0f;
        if (f3 <= 0.0f) {
            this.renderOffset = f2 - f3;
        } else {
            float f5 = fArr[Math.min(i2, this.cursor + 1)] - width;
            if ((-this.renderOffset) < f5) {
                this.renderOffset = -f5;
            }
        }
        float f6 = fArr[i2];
        int i3 = i - 2;
        float f7 = 0.0f;
        while (i3 >= 0) {
            float f8 = fArr[i3];
            if (f6 - f8 > width) {
                break;
            }
            i3--;
            f7 = f8;
        }
        if ((-this.renderOffset) > f7) {
            this.renderOffset = -f7;
        }
        this.visibleTextStart = 0;
        for (int i4 = 0; i4 < i; i4++) {
            float f9 = fArr[i4];
            if (f9 >= (-this.renderOffset)) {
                this.visibleTextStart = i4;
                f4 = f9;
                break;
            }
        }
        int i5 = this.visibleTextStart + 1;
        float f10 = width - this.renderOffset;
        int iMin = Math.min(this.displayText.length(), i);
        while (i5 <= iMin && fArr[i5] <= f10) {
            i5++;
        }
        int iMax = Math.max(0, i5 - 1);
        this.visibleTextEnd = iMax;
        int i6 = this.textHAlign;
        if ((i6 & 8) == 0) {
            float f11 = ((width - fArr[iMax]) - this.fontOffset) + f4;
            this.textOffset = f11;
            if ((i6 & 1) != 0) {
                this.textOffset = Math.round(f11 * 0.5f);
            }
        } else {
            this.textOffset = f4 + this.renderOffset;
        }
        if (this.hasSelection) {
            int iMin2 = Math.min(this.cursor, this.selectionStart);
            int iMax2 = Math.max(this.cursor, this.selectionStart);
            float fMax = Math.max(fArr[iMin2] - fArr[this.visibleTextStart], -this.textOffset);
            float fMin = Math.min(fArr[iMax2] - fArr[this.visibleTextStart], width - this.textOffset);
            this.selectionX = fMax;
            this.selectionWidth = (fMin - fMax) - this.style.font.getData().cursorX;
        }
    }

    protected Drawable getBackgroundDrawable() {
        if (!this.disabled || this.style.disabledBackground == null) {
            return (this.style.focusedBackground == null || !hasKeyboardFocus()) ? this.style.background : this.style.focusedBackground;
        }
        return this.style.disabledBackground;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float f) {
        Color color;
        float leftWidth;
        float rightWidth;
        boolean zHasKeyboardFocus = hasKeyboardFocus();
        if (zHasKeyboardFocus != this.focused || (zHasKeyboardFocus && !this.blinkTask.isScheduled())) {
            this.focused = zHasKeyboardFocus;
            this.blinkTask.cancel();
            this.cursorOn = zHasKeyboardFocus;
            if (zHasKeyboardFocus) {
                Timer.Task task = this.blinkTask;
                float f2 = this.blinkTime;
                Timer.schedule(task, f2, f2);
            } else {
                this.keyRepeatTask.cancel();
            }
        } else if (!zHasKeyboardFocus) {
            this.cursorOn = false;
        }
        BitmapFont bitmapFont = this.style.font;
        if (!this.disabled || this.style.disabledFontColor == null) {
            color = (!zHasKeyboardFocus || this.style.focusedFontColor == null) ? this.style.fontColor : this.style.focusedFontColor;
        } else {
            color = this.style.disabledFontColor;
        }
        Color color2 = color;
        Drawable drawable = this.style.selection;
        Drawable drawable2 = this.style.cursor;
        Drawable backgroundDrawable = getBackgroundDrawable();
        Color color3 = getColor();
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        batch.setColor(color3.r, color3.g, color3.b, color3.a * f);
        if (backgroundDrawable != null) {
            backgroundDrawable.draw(batch, x, y, width, height);
            leftWidth = backgroundDrawable.getLeftWidth();
            rightWidth = backgroundDrawable.getRightWidth();
        } else {
            leftWidth = 0.0f;
            rightWidth = 0.0f;
        }
        float textY = getTextY(bitmapFont, backgroundDrawable);
        calculateOffsets();
        if (zHasKeyboardFocus && this.hasSelection && drawable != null) {
            drawSelection(drawable, batch, bitmapFont, x + leftWidth, y + textY);
        }
        float f3 = bitmapFont.isFlipped() ? -this.textHeight : 0.0f;
        if (this.displayText.length() == 0) {
            if ((!zHasKeyboardFocus || this.disabled) && this.messageText != null) {
                BitmapFont bitmapFont2 = this.style.messageFont != null ? this.style.messageFont : bitmapFont;
                if (this.style.messageFontColor != null) {
                    bitmapFont2.setColor(this.style.messageFontColor.r, this.style.messageFontColor.g, this.style.messageFontColor.b, this.style.messageFontColor.a * color3.a * f);
                } else {
                    bitmapFont2.setColor(0.7f, 0.7f, 0.7f, color3.a * f);
                }
                drawMessageText(batch, bitmapFont2, x + leftWidth, y + textY + f3, (width - leftWidth) - rightWidth);
            }
        } else {
            BitmapFont.BitmapFontData data = bitmapFont.getData();
            boolean z = data.markupEnabled;
            data.markupEnabled = false;
            bitmapFont.setColor(color2.r, color2.g, color2.b, color2.a * color3.a * f);
            drawText(batch, bitmapFont, x + leftWidth, y + textY + f3);
            data.markupEnabled = z;
        }
        if (this.disabled || !this.cursorOn || drawable2 == null) {
            return;
        }
        drawCursor(drawable2, batch, bitmapFont, x + leftWidth, y + textY);
    }

    protected float getTextY(BitmapFont bitmapFont, Drawable drawable) {
        float topHeight;
        float height = getHeight();
        float descent = (this.textHeight / 2.0f) + bitmapFont.getDescent();
        if (drawable != null) {
            float bottomHeight = drawable.getBottomHeight();
            topHeight = descent + (((height - drawable.getTopHeight()) - bottomHeight) / 2.0f) + bottomHeight;
        } else {
            topHeight = descent + (height / 2.0f);
        }
        return bitmapFont.usesIntegerPositions() ? (int) topHeight : topHeight;
    }

    protected void drawSelection(Drawable drawable, Batch batch, BitmapFont bitmapFont, float f, float f2) {
        drawable.draw(batch, f + this.textOffset + this.selectionX + this.fontOffset, (f2 - this.textHeight) - bitmapFont.getDescent(), this.selectionWidth, this.textHeight);
    }

    protected void drawText(Batch batch, BitmapFont bitmapFont, float f, float f2) {
        bitmapFont.draw(batch, this.displayText, f + this.textOffset, f2, this.visibleTextStart, this.visibleTextEnd, 0.0f, 8, false);
    }

    protected void drawMessageText(Batch batch, BitmapFont bitmapFont, float f, float f2, float f3) {
        String str = this.messageText;
        bitmapFont.draw(batch, str, f, f2, 0, str.length(), f3, this.textHAlign, false, "...");
    }

    protected void drawCursor(Drawable drawable, Batch batch, BitmapFont bitmapFont, float f, float f2) {
        drawable.draw(batch, (((f + this.textOffset) + this.glyphPositions.get(this.cursor)) - this.glyphPositions.get(this.visibleTextStart)) + this.fontOffset + bitmapFont.getData().cursorX, (f2 - this.textHeight) - bitmapFont.getDescent(), drawable.getMinWidth(), this.textHeight);
    }

    void updateDisplayText() {
        BitmapFont bitmapFont = this.style.font;
        BitmapFont.BitmapFontData data = bitmapFont.getData();
        String str = this.text;
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            char c = StringUtil.SPACE;
            if (i >= length) {
                break;
            }
            char cCharAt = str.charAt(i);
            if (data.hasGlyph(cCharAt)) {
                c = cCharAt;
            }
            sb.append(c);
            i++;
        }
        String string = sb.toString();
        if (this.passwordMode && data.hasGlyph(this.passwordCharacter)) {
            if (this.passwordBuffer == null) {
                this.passwordBuffer = new StringBuilder(string.length());
            }
            if (this.passwordBuffer.length() > length) {
                this.passwordBuffer.setLength(length);
            } else {
                for (int length2 = this.passwordBuffer.length(); length2 < length; length2++) {
                    this.passwordBuffer.append(this.passwordCharacter);
                }
            }
            this.displayText = this.passwordBuffer;
        } else {
            this.displayText = string;
        }
        boolean z = data.markupEnabled;
        data.markupEnabled = false;
        this.layout.setText(bitmapFont, this.displayText.toString().replace('\r', StringUtil.SPACE).replace('\n', StringUtil.SPACE));
        data.markupEnabled = z;
        this.glyphPositions.clear();
        float f = 0.0f;
        if (this.layout.runs.size > 0) {
            FloatArray floatArray = this.layout.runs.first().xAdvances;
            this.fontOffset = floatArray.first();
            int i2 = floatArray.size;
            for (int i3 = 1; i3 < i2; i3++) {
                this.glyphPositions.add(f);
                f += floatArray.get(i3);
            }
        } else {
            this.fontOffset = 0.0f;
        }
        this.glyphPositions.add(f);
        int iMin = Math.min(this.visibleTextStart, this.glyphPositions.size - 1);
        this.visibleTextStart = iMin;
        this.visibleTextEnd = MathUtils.clamp(this.visibleTextEnd, iMin, this.glyphPositions.size - 1);
        if (this.selectionStart > string.length()) {
            this.selectionStart = length;
        }
    }

    public void copy() {
        if (!this.hasSelection || this.passwordMode) {
            return;
        }
        this.clipboard.setContents(this.text.substring(Math.min(this.cursor, this.selectionStart), Math.max(this.cursor, this.selectionStart)));
    }

    public void cut() {
        cut(this.programmaticChangeEvents);
    }

    void cut(boolean z) {
        if (!this.hasSelection || this.passwordMode) {
            return;
        }
        copy();
        this.cursor = delete(z);
        updateDisplayText();
    }

    void paste(String str, boolean z) {
        TextFieldFilter textFieldFilter;
        if (str == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        int length = this.text.length();
        if (this.hasSelection) {
            length -= Math.abs(this.cursor - this.selectionStart);
        }
        BitmapFont.BitmapFontData data = this.style.font.getData();
        int length2 = str.length();
        for (int i = 0; i < length2 && withinMaxLength(sb.length() + length); i++) {
            char cCharAt = str.charAt(i);
            if ((this.writeEnters && (cCharAt == '\n' || cCharAt == '\r')) || (cCharAt != '\r' && cCharAt != '\n' && ((!this.onlyFontChars || data.hasGlyph(cCharAt)) && ((textFieldFilter = this.filter) == null || textFieldFilter.acceptChar(this, cCharAt))))) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        if (this.hasSelection) {
            this.cursor = delete(z);
        }
        if (z) {
            String str2 = this.text;
            changeText(str2, insert(this.cursor, string, str2));
        } else {
            this.text = insert(this.cursor, string, this.text);
        }
        updateDisplayText();
        this.cursor += string.length();
    }

    String insert(int i, CharSequence charSequence, String str) {
        return str.length() == 0 ? charSequence.toString() : str.substring(0, i) + ((Object) charSequence) + str.substring(i, str.length());
    }

    int delete(boolean z) {
        int i = this.selectionStart;
        int i2 = this.cursor;
        int iMin = Math.min(i, i2);
        int iMax = Math.max(i, i2);
        String strSubstring = "";
        StringBuilder sbAppend = new StringBuilder().append(iMin > 0 ? this.text.substring(0, iMin) : "");
        if (iMax < this.text.length()) {
            String str = this.text;
            strSubstring = str.substring(iMax, str.length());
        }
        String string = sbAppend.append(strSubstring).toString();
        if (z) {
            changeText(this.text, string);
        } else {
            this.text = string;
        }
        clearSelection();
        return iMin;
    }

    public void next(boolean z) {
        Stage stage = getStage();
        if (stage == null) {
            return;
        }
        Vector2 vector2LocalToStageCoordinates = getParent().localToStageCoordinates(tmp2.set(getX(), getY()));
        Vector2 vector2 = tmp1;
        TextField textField = this;
        while (true) {
            TextField textFieldFindNextTextField = textField.findNextTextField(stage.getActors(), null, vector2, vector2LocalToStageCoordinates, z);
            if (textFieldFindNextTextField == null) {
                if (z) {
                    vector2LocalToStageCoordinates.set(-3.4028235E38f, -3.4028235E38f);
                } else {
                    vector2LocalToStageCoordinates.set(Float.MAX_VALUE, Float.MAX_VALUE);
                }
                textFieldFindNextTextField = textField.findNextTextField(stage.getActors(), null, vector2, vector2LocalToStageCoordinates, z);
            }
            textField = textFieldFindNextTextField;
            if (textField == null) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                return;
            } else {
                if (stage.setKeyboardFocus(textField)) {
                    textField.selectAll();
                    return;
                }
                vector2LocalToStageCoordinates.set(vector2);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0059  */
    /* JADX WARN: Code duplicated, block: B:34:0x0072  */
    /* JADX WARN: Code duplicated, block: B:49:0x0094  */
    /* JADX WARN: Code duplicated, block: B:60:0x00ae  */
    private TextField findNextTextField(Array<Actor> array, TextField textField, Vector2 vector2, Vector2 vector3, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i = array.size;
        TextField textFieldFindNextTextField = textField;
        for (int i2 = 0; i2 < i; i2++) {
            Actor actor = array.get(i2);
            if (actor instanceof TextField) {
                if (actor != this) {
                    TextField textField2 = (TextField) actor;
                    if (!textField2.isDisabled() && textField2.focusTraversal && textField2.ascendantsVisible()) {
                        Vector2 vector2LocalToStageCoordinates = actor.getParent().localToStageCoordinates(tmp3.set(actor.getX(), actor.getY()));
                        if (vector2LocalToStageCoordinates.y == vector3.y) {
                            z2 = false;
                        } else if ((vector2LocalToStageCoordinates.y < vector3.y) ^ z) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (vector2LocalToStageCoordinates.y != vector3.y) {
                            z3 = false;
                        } else if ((vector2LocalToStageCoordinates.x > vector3.x) ^ z) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z2 || z3) {
                            if (textFieldFindNextTextField != null) {
                                if (vector2LocalToStageCoordinates.y != vector2.y) {
                                    z4 = (vector2LocalToStageCoordinates.y > vector2.y) ^ z;
                                }
                            }
                            if (!z4) {
                                if (vector2LocalToStageCoordinates.y == vector2.y) {
                                    z5 = ((vector2LocalToStageCoordinates.x > vector2.x ? 1 : (vector2LocalToStageCoordinates.x == vector2.x ? 0 : -1)) < 0) ^ z;
                                }
                                z4 = z5;
                            }
                            if (z4) {
                                vector2.set(vector2LocalToStageCoordinates);
                                textFieldFindNextTextField = textField2;
                            }
                        }
                    }
                }
            } else if (actor instanceof Group) {
                textFieldFindNextTextField = findNextTextField(((Group) actor).getChildren(), textFieldFindNextTextField, vector2, vector3, z);
            }
        }
        return textFieldFindNextTextField;
    }

    public InputListener getDefaultInputListener() {
        return this.inputListener;
    }

    public void setTextFieldListener(TextFieldListener textFieldListener) {
        this.listener = textFieldListener;
    }

    public void setTextFieldFilter(TextFieldFilter textFieldFilter) {
        this.filter = textFieldFilter;
    }

    public TextFieldFilter getTextFieldFilter() {
        return this.filter;
    }

    public void setFocusTraversal(boolean z) {
        this.focusTraversal = z;
    }

    public boolean getFocusTraversal() {
        return this.focusTraversal;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String str) {
        this.messageText = str;
    }

    public void appendText(String str) {
        if (str == null) {
            str = "";
        }
        clearSelection();
        this.cursor = this.text.length();
        paste(str, this.programmaticChangeEvents);
    }

    public void setText(String str) {
        if (str == null) {
            str = "";
        }
        if (str.equals(this.text)) {
            return;
        }
        clearSelection();
        String str2 = this.text;
        this.text = "";
        paste(str, false);
        if (this.programmaticChangeEvents) {
            changeText(str2, this.text);
        }
        this.cursor = 0;
    }

    public String getText() {
        return this.text;
    }

    boolean changeText(String str, String str2) {
        if (str2.equals(str)) {
            return false;
        }
        this.text = str2;
        ChangeListener.ChangeEvent changeEvent = (ChangeListener.ChangeEvent) Pools.obtain(ChangeListener.ChangeEvent.class);
        boolean zFire = fire(changeEvent);
        if (zFire) {
            this.text = str;
        }
        Pools.free(changeEvent);
        return !zFire;
    }

    public void setProgrammaticChangeEvents(boolean z) {
        this.programmaticChangeEvents = z;
    }

    public boolean getProgrammaticChangeEvents() {
        return this.programmaticChangeEvents;
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public String getSelection() {
        return this.hasSelection ? this.text.substring(Math.min(this.selectionStart, this.cursor), Math.max(this.selectionStart, this.cursor)) : "";
    }

    public void setSelection(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("selectionStart must be >= 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("selectionEnd must be >= 0");
        }
        int iMin = Math.min(this.text.length(), i);
        int iMin2 = Math.min(this.text.length(), i2);
        if (iMin2 == iMin) {
            clearSelection();
            return;
        }
        if (iMin2 < iMin) {
            iMin2 = iMin;
            iMin = iMin2;
        }
        this.hasSelection = true;
        this.selectionStart = iMin;
        this.cursor = iMin2;
    }

    public void selectAll() {
        setSelection(0, this.text.length());
    }

    public void clearSelection() {
        this.hasSelection = false;
    }

    public void setCursorPosition(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("cursorPosition must be >= 0");
        }
        clearSelection();
        this.cursor = Math.min(i, this.text.length());
    }

    public int getCursorPosition() {
        return this.cursor;
    }

    public OnscreenKeyboard getOnscreenKeyboard() {
        return this.keyboard;
    }

    public void setOnscreenKeyboard(OnscreenKeyboard onscreenKeyboard) {
        this.keyboard = onscreenKeyboard;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.utils.Layout
    public float getPrefHeight() {
        float fMax;
        float fMax2 = 0.0f;
        if (this.style.background != null) {
            fMax2 = Math.max(0.0f, this.style.background.getBottomHeight() + this.style.background.getTopHeight());
            fMax = Math.max(0.0f, this.style.background.getMinHeight());
        } else {
            fMax = 0.0f;
        }
        if (this.style.focusedBackground != null) {
            fMax2 = Math.max(fMax2, this.style.focusedBackground.getBottomHeight() + this.style.focusedBackground.getTopHeight());
            fMax = Math.max(fMax, this.style.focusedBackground.getMinHeight());
        }
        if (this.style.disabledBackground != null) {
            fMax2 = Math.max(fMax2, this.style.disabledBackground.getBottomHeight() + this.style.disabledBackground.getTopHeight());
            fMax = Math.max(fMax, this.style.disabledBackground.getMinHeight());
        }
        return Math.max(fMax2 + this.textHeight, fMax);
    }

    public void setAlignment(int i) {
        this.textHAlign = i;
    }

    public int getAlignment() {
        return this.textHAlign;
    }

    public void setPasswordMode(boolean z) {
        this.passwordMode = z;
        updateDisplayText();
    }

    public boolean isPasswordMode() {
        return this.passwordMode;
    }

    public void setPasswordCharacter(char c) {
        this.passwordCharacter = c;
        if (this.passwordMode) {
            updateDisplayText();
        }
    }

    public void setBlinkTime(float f) {
        this.blinkTime = f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public void setDisabled(boolean z) {
        this.disabled = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.utils.Disableable
    public boolean isDisabled() {
        return this.disabled;
    }

    protected void moveCursor(boolean z, boolean z2) {
        int length = z ? this.text.length() : 0;
        int i = z ? 0 : -1;
        do {
            int i2 = this.cursor;
            if (z) {
                int i3 = i2 + 1;
                this.cursor = i3;
                if (i3 >= length) {
                    return;
                }
            } else {
                int i4 = i2 - 1;
                this.cursor = i4;
                if (i4 <= length) {
                    return;
                }
            }
            if (!z2) {
                return;
            }
        } while (continueCursor(this.cursor, i));
    }

    protected boolean continueCursor(int i, int i2) {
        return isWordCharacter(this.text.charAt(i + i2));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$KeyRepeatTask.class
      classes8.dex
     */
    class KeyRepeatTask extends Timer.Task {
        int keycode;

        KeyRepeatTask() {
        }

        @Override // com.badlogic.gdx.utils.Timer.Task, java.lang.Runnable
        public void run() {
            if (TextField.this.getStage() == null) {
                cancel();
            } else {
                TextField.this.inputListener.keyDown(null, this.keycode);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$TextFieldFilter.class
      classes8.dex
     */
    public interface TextFieldFilter {
        boolean acceptChar(TextField textField, char c);

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$TextFieldFilter$DigitsOnlyFilter.class
          classes8.dex
         */
        public static class DigitsOnlyFilter implements TextFieldFilter {
            @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter
            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$DefaultOnscreenKeyboard.class
      classes8.dex
     */
    public static class DefaultOnscreenKeyboard implements OnscreenKeyboard {
        @Override // com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard
        public void show(boolean z) {
            Gdx.input.setOnscreenKeyboardVisible(z);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$TextFieldClickListener.class
      classes8.dex
     */
    public class TextFieldClickListener extends ClickListener {
        public TextFieldClickListener() {
        }

        @Override // com.badlogic.gdx.scenes.scene2d.utils.ClickListener
        public void clicked(InputEvent inputEvent, float f, float f2) {
            int tapCount = getTapCount() % 4;
            if (tapCount == 0) {
                TextField.this.clearSelection();
            }
            if (tapCount == 2) {
                int[] iArrWordUnderCursor = TextField.this.wordUnderCursor(f);
                TextField.this.setSelection(iArrWordUnderCursor[0], iArrWordUnderCursor[1]);
            }
            if (tapCount == 3) {
                TextField.this.selectAll();
            }
        }

        @Override // com.badlogic.gdx.scenes.scene2d.utils.ClickListener, com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean touchDown(InputEvent inputEvent, float f, float f2, int i, int i2) {
            if (!super.touchDown(inputEvent, f, f2, i, i2)) {
                return false;
            }
            if (i == 0 && i2 != 0) {
                return false;
            }
            if (TextField.this.disabled) {
                return true;
            }
            setCursorPosition(f, f2);
            TextField textField = TextField.this;
            textField.selectionStart = textField.cursor;
            Stage stage = TextField.this.getStage();
            if (stage != null) {
                stage.setKeyboardFocus(TextField.this);
            }
            TextField.this.keyboard.show(true);
            TextField.this.hasSelection = true;
            return true;
        }

        @Override // com.badlogic.gdx.scenes.scene2d.utils.ClickListener, com.badlogic.gdx.scenes.scene2d.InputListener
        public void touchDragged(InputEvent inputEvent, float f, float f2, int i) {
            super.touchDragged(inputEvent, f, f2, i);
            setCursorPosition(f, f2);
        }

        @Override // com.badlogic.gdx.scenes.scene2d.utils.ClickListener, com.badlogic.gdx.scenes.scene2d.InputListener
        public void touchUp(InputEvent inputEvent, float f, float f2, int i, int i2) {
            if (TextField.this.selectionStart == TextField.this.cursor) {
                TextField.this.hasSelection = false;
            }
            super.touchUp(inputEvent, f, f2, i, i2);
        }

        protected void setCursorPosition(float f, float f2) {
            TextField textField = TextField.this;
            textField.cursor = textField.letterUnderCursor(f);
            TextField textField2 = TextField.this;
            textField2.cursorOn = textField2.focused;
            TextField.this.blinkTask.cancel();
            if (TextField.this.focused) {
                Timer.schedule(TextField.this.blinkTask, TextField.this.blinkTime, TextField.this.blinkTime);
            }
        }

        protected void goHome(boolean z) {
            TextField.this.cursor = 0;
        }

        protected void goEnd(boolean z) {
            TextField textField = TextField.this;
            textField.cursor = textField.text.length();
        }

        @Override // com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean keyDown(InputEvent inputEvent, int i) {
            boolean z;
            boolean z2;
            if (TextField.this.disabled) {
                return false;
            }
            TextField textField = TextField.this;
            textField.cursorOn = textField.focused;
            TextField.this.blinkTask.cancel();
            if (TextField.this.focused) {
                Timer.schedule(TextField.this.blinkTask, TextField.this.blinkTime, TextField.this.blinkTime);
            }
            if (!TextField.this.hasKeyboardFocus()) {
                return false;
            }
            boolean zCtrl = UIUtils.ctrl();
            boolean z3 = true;
            boolean z4 = zCtrl && !TextField.this.passwordMode;
            if (zCtrl) {
                if (i != 29) {
                    if (i != 31) {
                        if (i == 50) {
                            TextField textField2 = TextField.this;
                            textField2.paste(textField2.clipboard.getContents(), true);
                            z = true;
                        } else {
                            if (i == 52) {
                                TextField.this.cut(true);
                                return true;
                            }
                            if (i == 54) {
                                String str = TextField.this.text;
                                TextField textField3 = TextField.this;
                                textField3.setText(textField3.undoText);
                                TextField.this.undoText = str;
                                TextField.this.updateDisplayText();
                                return true;
                            }
                            if (i != 124) {
                                z = false;
                            }
                        }
                        z2 = z;
                    }
                    TextField.this.copy();
                    return true;
                }
                TextField.this.selectAll();
                return true;
            }
            z = false;
            z2 = true;
            if (UIUtils.shift()) {
                if (i == 112) {
                    TextField.this.cut(true);
                } else if (i == 124) {
                    TextField textField4 = TextField.this;
                    textField4.paste(textField4.clipboard.getContents(), true);
                }
                int i2 = TextField.this.cursor;
                if (i == 3) {
                    goHome(z4);
                } else if (i != 123) {
                    if (i != 21) {
                        if (i == 22) {
                            TextField.this.moveCursor(true, z4);
                        }
                        z3 = z;
                    } else {
                        TextField.this.moveCursor(false, z4);
                    }
                    z = true;
                } else {
                    goEnd(z4);
                }
                if (!TextField.this.hasSelection) {
                    TextField.this.selectionStart = i2;
                    TextField.this.hasSelection = true;
                }
                z2 = true;
                z3 = z;
            } else {
                if (i == 3) {
                    goHome(z4);
                    TextField.this.clearSelection();
                } else if (i != 123) {
                    if (i == 21) {
                        TextField.this.moveCursor(false, z4);
                        TextField.this.clearSelection();
                    } else if (i != 22) {
                        z3 = z;
                    } else {
                        TextField.this.moveCursor(true, z4);
                        TextField.this.clearSelection();
                    }
                    z2 = true;
                } else {
                    goEnd(z4);
                    TextField.this.clearSelection();
                }
                z2 = true;
                z3 = z;
            }
            TextField textField5 = TextField.this;
            textField5.cursor = MathUtils.clamp(textField5.cursor, 0, TextField.this.text.length());
            if (z3) {
                scheduleKeyRepeatTask(i);
            }
            return z2;
        }

        protected void scheduleKeyRepeatTask(int i) {
            if (TextField.this.keyRepeatTask.isScheduled() && TextField.this.keyRepeatTask.keycode == i) {
                return;
            }
            TextField.this.keyRepeatTask.keycode = i;
            TextField.this.keyRepeatTask.cancel();
            Timer.schedule(TextField.this.keyRepeatTask, TextField.keyRepeatInitialTime, TextField.keyRepeatTime);
        }

        @Override // com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean keyUp(InputEvent inputEvent, int i) {
            if (TextField.this.disabled) {
                return false;
            }
            TextField.this.keyRepeatTask.cancel();
            return true;
        }

        protected boolean checkFocusTraversal(char c) {
            return TextField.this.focusTraversal && (c == '\t' || ((c == '\r' || c == '\n') && (UIUtils.isAndroid || UIUtils.isIos)));
        }

        @Override // com.badlogic.gdx.scenes.scene2d.InputListener
        public boolean keyTyped(InputEvent inputEvent, char c) {
            boolean z;
            if (TextField.this.disabled) {
                return false;
            }
            if (c != '\r') {
                switch (c) {
                    case '\b':
                    case '\t':
                    case '\n':
                        break;
                    default:
                        if (c < ' ') {
                            return false;
                        }
                        break;
                }
            }
            if (!TextField.this.hasKeyboardFocus()) {
                return false;
            }
            if (UIUtils.isMac && Gdx.input.isKeyPressed(63)) {
                return true;
            }
            if (checkFocusTraversal(c)) {
                TextField.this.next(UIUtils.shift());
            } else {
                boolean z2 = c == '\r' || c == '\n';
                boolean z3 = c == 127;
                boolean z4 = c == '\b';
                TextField textField = TextField.this;
                if (z2) {
                    z = textField.writeEnters;
                } else {
                    z = !textField.onlyFontChars || TextField.this.style.font.getData().hasGlyph(c);
                }
                boolean z5 = z4 || z3;
                if (z || z5) {
                    String str = TextField.this.text;
                    int i = TextField.this.cursor;
                    if (z5) {
                        if (TextField.this.hasSelection) {
                            TextField textField2 = TextField.this;
                            textField2.cursor = textField2.delete(false);
                        } else {
                            if (z4 && TextField.this.cursor > 0) {
                                TextField textField3 = TextField.this;
                                StringBuilder sbAppend = new StringBuilder().append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                                String str2 = TextField.this.text;
                                TextField textField4 = TextField.this;
                                int i2 = textField4.cursor;
                                textField4.cursor = i2 - 1;
                                textField3.text = sbAppend.append(str2.substring(i2)).toString();
                                TextField.this.renderOffset = 0.0f;
                            }
                            if (z3 && TextField.this.cursor < TextField.this.text.length()) {
                                TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor) + TextField.this.text.substring(TextField.this.cursor + 1);
                            }
                        }
                    }
                    if (z && !z5) {
                        if (!z2 && TextField.this.filter != null && !TextField.this.filter.acceptChar(TextField.this, c)) {
                            return true;
                        }
                        TextField textField5 = TextField.this;
                        if (!textField5.withinMaxLength(textField5.text.length() - (TextField.this.hasSelection ? Math.abs(TextField.this.cursor - TextField.this.selectionStart) : 0))) {
                            return true;
                        }
                        if (TextField.this.hasSelection) {
                            TextField textField6 = TextField.this;
                            textField6.cursor = textField6.delete(false);
                        }
                        String strValueOf = z2 ? "\n" : String.valueOf(c);
                        TextField textField7 = TextField.this;
                        int i3 = textField7.cursor;
                        textField7.cursor = i3 + 1;
                        textField7.text = textField7.insert(i3, strValueOf, TextField.this.text);
                    }
                    String str3 = TextField.this.undoText;
                    TextField textField8 = TextField.this;
                    if (textField8.changeText(str, textField8.text)) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (jCurrentTimeMillis - 750 > TextField.this.lastChangeTime) {
                            TextField.this.undoText = str;
                        }
                        TextField.this.lastChangeTime = jCurrentTimeMillis;
                        TextField.this.updateDisplayText();
                    } else if (!TextField.this.text.equals(str)) {
                        TextField.this.cursor = i;
                    }
                }
            }
            if (TextField.this.listener != null) {
                TextField.this.listener.keyTyped(TextField.this, c);
            }
            return true;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextField$TextFieldStyle.class
      classes8.dex
     */
    public static class TextFieldStyle {
        public Drawable background;
        public Drawable cursor;
        public Drawable disabledBackground;
        public Color disabledFontColor;
        public Drawable focusedBackground;
        public Color focusedFontColor;
        public BitmapFont font;
        public Color fontColor;
        public BitmapFont messageFont;
        public Color messageFontColor;
        public Drawable selection;

        public TextFieldStyle() {
        }

        public TextFieldStyle(BitmapFont bitmapFont, Color color, Drawable drawable, Drawable drawable2, Drawable drawable3) {
            this.font = bitmapFont;
            this.fontColor = color;
            this.cursor = drawable;
            this.selection = drawable2;
            this.background = drawable3;
        }

        public TextFieldStyle(TextFieldStyle textFieldStyle) {
            this.font = textFieldStyle.font;
            if (textFieldStyle.fontColor != null) {
                this.fontColor = new Color(textFieldStyle.fontColor);
            }
            if (textFieldStyle.focusedFontColor != null) {
                this.focusedFontColor = new Color(textFieldStyle.focusedFontColor);
            }
            if (textFieldStyle.disabledFontColor != null) {
                this.disabledFontColor = new Color(textFieldStyle.disabledFontColor);
            }
            this.background = textFieldStyle.background;
            this.focusedBackground = textFieldStyle.focusedBackground;
            this.disabledBackground = textFieldStyle.disabledBackground;
            this.cursor = textFieldStyle.cursor;
            this.selection = textFieldStyle.selection;
            this.messageFont = textFieldStyle.messageFont;
            if (textFieldStyle.messageFontColor != null) {
                this.messageFontColor = new Color(textFieldStyle.messageFontColor);
            }
        }
    }
}
