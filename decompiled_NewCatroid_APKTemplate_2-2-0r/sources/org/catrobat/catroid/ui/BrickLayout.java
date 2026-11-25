package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.view.InputDeviceCompat;
import java.util.Iterator;
import java.util.LinkedList;
import org.catrobat.catroid.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BrickLayout.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BrickLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected boolean debugDraw;
    private final int elementsToAllocatePerLine;
    private int horizontalSpacing;
    private int layoutDirection;
    protected LinkedList<LineData> lines;
    private final int linesToAllocate;
    private final int minTextFieldWidthDp;
    private int orientation;
    protected boolean usePng;
    protected boolean userBrick;
    private int verticalSpacing;

    public BrickLayout(Context context) {
        super(context);
        this.minTextFieldWidthDp = 100;
        this.linesToAllocate = 10;
        this.elementsToAllocatePerLine = 10;
        this.layoutDirection = 1;
        this.horizontalSpacing = 0;
        this.verticalSpacing = 0;
        this.orientation = 0;
        this.debugDraw = true;
        this.userBrick = false;
        this.usePng = false;
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, null);
    }

    public BrickLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.minTextFieldWidthDp = 100;
        this.linesToAllocate = 10;
        this.elementsToAllocatePerLine = 10;
        this.layoutDirection = 1;
        this.horizontalSpacing = 0;
        this.verticalSpacing = 0;
        this.orientation = 0;
        this.debugDraw = true;
        this.userBrick = false;
        this.usePng = false;
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, attributeSet);
    }

    public BrickLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.minTextFieldWidthDp = 100;
        this.linesToAllocate = 10;
        this.elementsToAllocatePerLine = 10;
        this.layoutDirection = 1;
        this.horizontalSpacing = 0;
        this.verticalSpacing = 0;
        this.orientation = 0;
        this.debugDraw = true;
        this.userBrick = false;
        this.usePng = false;
        setLayoutDirection(context);
        allocateLineData();
        readStyleParameters(context, attributeSet);
    }

    protected void allocateLineData() {
        this.lines = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            allocateNewLine();
        }
    }

    private void setLayoutDirection(Context context) {
        if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
            this.layoutDirection = -1;
        }
    }

    protected LineData allocateNewLine() {
        LineData lineData = new LineData();
        for (int i = 0; i < 10; i++) {
            lineData.elements.add(new ElementData(null, 0, 0, 0, 0));
        }
        this.lines.add(lineData);
        return lineData;
    }

    public int getPaddingLeft(int widthMeasureSpec) {
        if (!this.usePng) {
            return super.getPaddingLeft() + ((int) (((double) View.MeasureSpec.getSize(widthMeasureSpec)) * 0.14d));
        }
        return super.getPaddingLeft();
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0105  */
    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        int iPreLayoutMeasureWidth;
        boolean z2;
        int i5;
        int size = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight()) - getPaddingLeft(widthMeasureSpec);
        int size2 = (View.MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()) - getPaddingBottom();
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        for (LineData lineData : this.lines) {
            lineData.allowableTextFieldWidth = 0;
            lineData.height = 0;
            lineData.numberOfTextFields = 0;
            lineData.totalTextFieldWidth = 0;
            for (ElementData elementData : lineData.elements) {
                elementData.height = 0;
                elementData.width = 0;
                elementData.posY = 0;
                elementData.posX = 0;
                elementData.view = null;
            }
        }
        LineData first = this.lines.getFirst();
        int childCount = getChildCount();
        int i6 = 0;
        int horizontalSpacing = 0;
        while (true) {
            i = 8;
            if (i6 >= childCount) {
                break;
            }
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                horizontalSpacing += this.horizontalSpacing + getHorizontalSpacing((LayoutParams) childAt.getLayoutParams()) + preLayoutMeasureWidth(childAt, size, size2, mode, mode2);
            }
            i6++;
        }
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i9 < childCount) {
            View childAt2 = getChildAt(i9);
            if (childAt2.getVisibility() == i) {
                childCount = childCount;
                i = i;
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                int horizontalSpacing2 = getHorizontalSpacing(layoutParams);
                if (this.userBrick) {
                    iPreLayoutMeasureWidth = childAt2.getMeasuredWidth();
                } else {
                    iPreLayoutMeasureWidth = preLayoutMeasureWidth(childAt2, size, size2, mode, mode2);
                }
                int i13 = iPreLayoutMeasureWidth + horizontalSpacing2;
                int i14 = i10 + i13;
                int i15 = i14 + this.horizontalSpacing;
                if (!this.userBrick ? !(!layoutParams.newLine || horizontalSpacing - i11 <= size) : i15 >= size) {
                    z2 = true;
                } else if (layoutParams.forceNewLine) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                boolean z3 = i9 > 0 ? getChildAt(i9 - 1) instanceof Spinner : false;
                if (z2 || (childAt2 instanceof Spinner) || z3) {
                    first.allowableTextFieldWidth = ((int) Math.floor((size - (i14 - ((this.horizontalSpacing + i13) + first.totalTextFieldWidth))) / first.numberOfTextFields)) - horizontalSpacing2;
                    first = getNextLine(first);
                    int i16 = this.horizontalSpacing;
                    i11 += i14 - (i13 + i16);
                    i15 = i16 + i13;
                    i14 = i13;
                    i5 = 0;
                } else {
                    i5 = i12;
                }
                getElement(first, i5).view = childAt2;
                i12 = i5 + 1;
                if (layoutParams.editText) {
                    first.totalTextFieldWidth += i13;
                    first.numberOfTextFields++;
                }
                i7 = i14;
                i10 = i15;
                i8 = horizontalSpacing2;
            }
            i9++;
            i = i;
            childCount = childCount;
        }
        first.allowableTextFieldWidth = ((int) Math.floor((size - (i7 - first.totalTextFieldWidth)) / first.numberOfTextFields)) - i8;
        for (LineData lineData2 : this.lines) {
            for (ElementData elementData2 : lineData2.elements) {
                if (elementData2.view != null && ((LayoutParams) elementData2.view.getLayoutParams()).editText && lineData2.allowableTextFieldWidth > 0) {
                    ((TextView) elementData2.view).setMaxWidth(lineData2.allowableTextFieldWidth);
                }
            }
        }
        LineData first2 = this.lines.getFirst();
        Iterator<LineData> it = this.lines.iterator();
        boolean z4 = true;
        int iMax = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int iMax2 = 0;
        int i20 = 0;
        while (it.hasNext()) {
            boolean z5 = !z4;
            for (ElementData elementData3 : it.next().elements) {
                it = it;
                View view = elementData3.view;
                int i21 = i17;
                if (view != null) {
                    i2 = i20;
                    if (view.getVisibility() != 8) {
                        if (view instanceof Spinner) {
                            view.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, mode2 == 1073741824 ? Integer.MIN_VALUE : mode2));
                        } else {
                            view.measure(View.MeasureSpec.makeMeasureSpec(size, mode == 1073741824 ? Integer.MIN_VALUE : mode), View.MeasureSpec.makeMeasureSpec(size2, mode2 == 1073741824 ? Integer.MIN_VALUE : mode2));
                        }
                        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                        int horizontalSpacing3 = getHorizontalSpacing(layoutParams2);
                        int verticalSpacing = getVerticalSpacing(layoutParams2);
                        int i22 = size;
                        int measuredWidth = view.getMeasuredWidth();
                        int measuredHeight = view.getMeasuredHeight();
                        int i23 = i18 + measuredWidth;
                        int i24 = i23 + horizontalSpacing3;
                        if (z5) {
                            i19 += iMax2;
                            first2 = getNextLine(first2);
                            iMax2 = measuredHeight + verticalSpacing;
                            i3 = horizontalSpacing3 + measuredWidth;
                            i4 = measuredHeight;
                            i23 = measuredWidth;
                            z = false;
                        } else {
                            i3 = i24;
                            z = z5;
                            i4 = i2;
                        }
                        iMax2 = Math.max(iMax2, measuredHeight + verticalSpacing);
                        int iMax3 = Math.max(i4, measuredHeight);
                        first2.height = iMax3;
                        int paddingLeft = (getPaddingLeft(widthMeasureSpec) + i23) - measuredWidth;
                        LineData lineData3 = first2;
                        int paddingTop = getPaddingTop() + i19;
                        elementData3.posX = paddingLeft;
                        elementData3.posY = paddingTop;
                        elementData3.width = measuredWidth;
                        elementData3.height = measuredHeight;
                        elementData3.editText = layoutParams2.editText;
                        iMax = Math.max(iMax, i23);
                        i17 = i19 + iMax3;
                        i18 = i3;
                        first2 = lineData3;
                        size = i22;
                        i20 = iMax3;
                        z5 = z;
                    }
                } else {
                    i2 = i20;
                }
                i17 = i21;
                i20 = i2;
                size = size;
            }
            z4 = false;
        }
        int paddingTop2 = i17 + getPaddingTop() + getPaddingBottom();
        int suggestedMinimumHeight = paddingTop2 < getSuggestedMinimumHeight() ? (getSuggestedMinimumHeight() - paddingTop2) / 2 : 0;
        int iMax4 = Math.max(paddingTop2, getSuggestedMinimumHeight());
        for (LineData lineData4 : this.lines) {
            for (ElementData elementData4 : lineData4.elements) {
                if (elementData4.view != null) {
                    int iRound = (elementData4.editText && (elementData4.view instanceof TextView)) ? Math.round(((TextView) elementData4.view).getPaddingBottom() * 0.5f) : 0;
                    if (elementData4.height < lineData4.height) {
                        iRound = Math.round((lineData4.height - elementData4.height) * 0.5f);
                    }
                    elementData4.posY += iRound + suggestedMinimumHeight;
                    ((LayoutParams) elementData4.view.getLayoutParams()).setPosition(elementData4.posX, elementData4.posY);
                }
            }
        }
        setMeasuredDimension(resolveSize(iMax, widthMeasureSpec), resolveSize(iMax4, heightMeasureSpec));
        applyLayoutDirection();
    }

    private void applyLayoutDirection() {
        setScaleX(this.layoutDirection);
        Iterator<LineData> it = this.lines.iterator();
        while (it.hasNext()) {
            Iterator<ElementData> it2 = it.next().elements.iterator();
            while (it2.hasNext()) {
                View view = it2.next().view;
                if (view != null && view.getVisibility() != 8) {
                    view.setScaleX(this.layoutDirection);
                }
            }
        }
    }

    private int preLayoutMeasureWidth(View child, int sizeWidth, int sizeHeight, int modeWidth, int modeHeight) {
        boolean z = child instanceof Spinner;
        if (z) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(sizeWidth, 1073741824);
            if (modeHeight == 1073741824) {
                modeHeight = Integer.MIN_VALUE;
            }
            child.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight));
        } else {
            if (modeWidth == 1073741824) {
                modeWidth = Integer.MIN_VALUE;
            }
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth);
            if (modeHeight == 1073741824) {
                modeHeight = Integer.MIN_VALUE;
            }
            child.measure(iMakeMeasureSpec2, View.MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight));
        }
        Resources resources = getResources();
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        int measuredWidth = child.getMeasuredWidth();
        if (layoutParams.editText) {
            measuredWidth = (int) TypedValue.applyDimension(1, 100.0f, resources.getDisplayMetrics());
        }
        return z ? sizeWidth : measuredWidth;
    }

    protected LineData getNextLine(LineData currentLine) {
        int iIndexOf = this.lines.indexOf(currentLine) + 1;
        if (iIndexOf < this.lines.size()) {
            return this.lines.get(iIndexOf);
        }
        return allocateNewLine();
    }

    protected ElementData getElement(LineData currentLine, int elementInLineIndex) {
        if (elementInLineIndex < currentLine.elements.size()) {
            return currentLine.elements.get(elementInLineIndex);
        }
        ElementData elementData = new ElementData(null, 0, 0, 0, 0);
        currentLine.elements.add(elementData);
        return elementData;
    }

    protected int getHorizontalSpacing(LayoutParams layoutParams) {
        if (layoutParams.horizontalSpacingSpecified()) {
            return layoutParams.horizontalSpacing;
        }
        return this.horizontalSpacing;
    }

    protected int getVerticalSpacing(LayoutParams layoutParams) {
        if (layoutParams.verticalSpacingSpecified()) {
            return layoutParams.verticalSpacing;
        }
        return this.verticalSpacing;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            childAt.layout(layoutParams.positionX, layoutParams.positionY, layoutParams.positionX + childAt.getMeasuredWidth(), layoutParams.positionY + childAt.getMeasuredHeight());
        }
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean zDrawChild = super.drawChild(canvas, child, drawingTime);
        drawDebugInfo(canvas, child);
        return zDrawChild;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParameters) {
        return new LayoutParams(layoutParameters);
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BrickLayout);
        try {
            this.horizontalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0);
            this.verticalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
            this.orientation = typedArrayObtainStyledAttributes.getInteger(3, 0);
            this.debugDraw = typedArrayObtainStyledAttributes.getBoolean(1, false);
            this.userBrick = typedArrayObtainStyledAttributes.getBoolean(5, false);
            this.usePng = typedArrayObtainStyledAttributes.getBoolean(4, false);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void drawDebugInfo(Canvas canvas, View child) {
        if (this.debugDraw) {
            Paint paintCreatePaint = createPaint(InputDeviceCompat.SOURCE_ANY);
            Paint paintCreatePaint2 = createPaint(-16711936);
            Paint paintCreatePaint3 = createPaint(-65536);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            if (layoutParams.horizontalSpacing > 0) {
                float right = child.getRight();
                float top = child.getTop() + (child.getHeight() / 2.0f);
                canvas.drawLine(right, top, right + layoutParams.horizontalSpacing, top, paintCreatePaint);
                canvas.drawLine((layoutParams.horizontalSpacing + right) - 4.0f, top - 4.0f, right + layoutParams.horizontalSpacing, top, paintCreatePaint);
                canvas.drawLine((layoutParams.horizontalSpacing + right) - 4.0f, top + 4.0f, right + layoutParams.horizontalSpacing, top, paintCreatePaint);
            } else if (this.horizontalSpacing > 0) {
                float right2 = child.getRight();
                float top2 = child.getTop() + (child.getHeight() / 2.0f);
                canvas.drawLine(right2, top2, right2 + this.horizontalSpacing, top2, paintCreatePaint2);
                int i = this.horizontalSpacing;
                canvas.drawLine((i + right2) - 4.0f, top2 - 4.0f, right2 + i, top2, paintCreatePaint2);
                int i2 = this.horizontalSpacing;
                canvas.drawLine((i2 + right2) - 4.0f, top2 + 4.0f, right2 + i2, top2, paintCreatePaint2);
            }
            if (layoutParams.verticalSpacing > 0) {
                float left = child.getLeft() + (child.getWidth() / 2.0f);
                float bottom = child.getBottom();
                canvas.drawLine(left, bottom, left, bottom + layoutParams.verticalSpacing, paintCreatePaint);
                canvas.drawLine(left - 4.0f, (layoutParams.verticalSpacing + bottom) - 4.0f, left, bottom + layoutParams.verticalSpacing, paintCreatePaint);
                canvas.drawLine(left + 4.0f, (layoutParams.verticalSpacing + bottom) - 4.0f, left, bottom + layoutParams.verticalSpacing, paintCreatePaint);
            } else if (this.verticalSpacing > 0) {
                float left2 = child.getLeft() + (child.getWidth() / 2.0f);
                float bottom2 = child.getBottom();
                canvas.drawLine(left2, bottom2, left2, bottom2 + this.verticalSpacing, paintCreatePaint2);
                int i3 = this.verticalSpacing;
                canvas.drawLine(left2 - 4.0f, (i3 + bottom2) - 4.0f, left2, bottom2 + i3, paintCreatePaint2);
                int i4 = this.verticalSpacing;
                canvas.drawLine(left2 + 4.0f, (i4 + bottom2) - 4.0f, left2, bottom2 + i4, paintCreatePaint2);
            }
            if (layoutParams.newLine) {
                if (this.orientation == 0) {
                    float left3 = child.getLeft();
                    float top3 = child.getTop() + (child.getHeight() / 2.0f);
                    canvas.drawLine(left3, top3 - 6.0f, left3, top3 + 6.0f, paintCreatePaint3);
                } else {
                    float left4 = child.getLeft() + (child.getWidth() / 2.0f);
                    float top4 = child.getTop();
                    canvas.drawLine(left4 - 6.0f, top4, left4 + 6.0f, top4, paintCreatePaint3);
                }
            }
        }
    }

    protected Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(2.0f);
        return paint;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BrickLayout$LineData.class
      classes14.dex
     */
    protected class LineData {
        public int allowableTextFieldWidth;
        public LinkedList<ElementData> elements = new LinkedList<>();
        public int height;
        public int numberOfTextFields;
        public int totalTextFieldWidth;

        public LineData() {
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BrickLayout$ElementData.class
      classes14.dex
     */
    protected class ElementData {
        public boolean editText;
        public int height;
        public int posX;
        public int posY;
        public View view;
        public int width;

        public ElementData(View view, int posX, int posY, int childWidth, int childHeight) {
            this.posX = posX;
            this.posY = posY;
            this.height = childHeight;
            this.width = childWidth;
            this.view = view;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BrickLayout$LayoutParams.class
      classes14.dex
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        private static final int NO_SPACING = -1;
        private boolean editText;
        private boolean forceNewLine;
        private int horizontalSpacing;
        private boolean newLine;
        private int positionX;
        private int positionY;
        private int verticalSpacing;

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BrickLayout$LayoutParams$InputType.class
          classes14.dex
         */
        public enum InputType {
            NUMBER,
            TEXT
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.horizontalSpacing = -1;
            this.verticalSpacing = -1;
            this.newLine = false;
            this.forceNewLine = false;
            this.editText = false;
            readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.horizontalSpacing = -1;
            this.verticalSpacing = -1;
            this.newLine = false;
            this.forceNewLine = false;
            this.editText = false;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.horizontalSpacing = -1;
            this.verticalSpacing = -1;
            this.newLine = false;
            this.forceNewLine = false;
            this.editText = false;
        }

        public boolean horizontalSpacingSpecified() {
            return this.horizontalSpacing != -1;
        }

        public boolean verticalSpacingSpecified() {
            return this.verticalSpacing != -1;
        }

        public void setPosition(int x, int y) {
            this.positionX = x;
            this.positionY = y;
        }

        public void setHorizontalSpacing(int horizontalSpacing) {
            this.horizontalSpacing = horizontalSpacing;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setEditText(boolean editText) {
            this.editText = editText;
        }

        public void setNewLine(boolean newLine) {
            this.newLine = newLine;
        }

        public boolean getNewLine() {
            return this.newLine;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BrickLayout_Layout);
            try {
                this.horizontalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, -1);
                this.verticalSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, -1);
                this.newLine = typedArrayObtainStyledAttributes.getBoolean(6, false);
                this.forceNewLine = typedArrayObtainStyledAttributes.getBoolean(3, false);
                this.editText = typedArrayObtainStyledAttributes.getBoolean(2, false);
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
    }
}
