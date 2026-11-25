package org.catrobat.paintroid.tools.implementation;

import kotlin.Metadata;

/* JADX INFO: compiled from: BaseToolWithRectangleShape.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0017\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\rX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\rX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u0016\u0010\u001b\u001a\u00020\u00078\u0006X\u0087T¢\u0006\b\n\u0000\u0012\u0004\b\u001c\u0010\u001d\"\u0016\u0010\u001e\u001a\u00020\t8\u0006X\u0087T¢\u0006\b\n\u0000\u0012\u0004\b\u001f\u0010\u001d\"\u000e\u0010 \u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010#\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"BUNDLE_BOX_HEIGHT", "", "BUNDLE_BOX_ROTATION", "BUNDLE_BOX_WIDTH", "CLICK_TIMEOUT_MILLIS", "", "COMPLETE_ANGLE", "", "CONSTANT_1", "", "CONSTANT_2", "CONSTANT_3", "DEFAULT_ANTIALIASING_ON", "", "DEFAULT_BOX_RESIZE_MARGIN", "DEFAULT_MAXIMUM_BOX_RESOLUTION", "DEFAULT_RECTANGLE_MARGIN", "DEFAULT_RECTANGLE_SHRINKING", "DEFAULT_RESIZE_POINTS_VISIBLE", "DEFAULT_RESPECT_MAXIMUM_BORDER_RATIO", "DEFAULT_RESPECT_MAXIMUM_BOX_RESOLUTION", "DEFAULT_ROTATION_ENABLED", "DEFAULT_ROTATION_SYMBOL_DISTANCE", "DEFAULT_ROTATION_SYMBOL_WIDTH", "DEFAULT_TOOL_STROKE_WIDTH", "HIGHLIGHT_RECTANGLE_SHRINKING", "MAXIMAL_TOOL_STROKE_WIDTH", "MAXIMUM_BORDER_RATIO", "getMAXIMUM_BORDER_RATIO$annotations", "()V", "MINIMAL_BOX_SIZE", "getMINIMAL_BOX_SIZE$annotations", "MINIMAL_TOOL_STROKE_WIDTH", "RIGHT_ANGLE", "SIDES", "STRAIGHT_ANGLE", "Paintroid_signedRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class BaseToolWithRectangleShapeKt {
    private static final String BUNDLE_BOX_HEIGHT = "BOX_HEIGHT";
    private static final String BUNDLE_BOX_ROTATION = "BOX_ROTATION";
    private static final String BUNDLE_BOX_WIDTH = "BOX_WIDTH";
    public static final long CLICK_TIMEOUT_MILLIS = 250;
    private static final float COMPLETE_ANGLE = 360.0f;
    private static final int CONSTANT_1 = 10;
    private static final int CONSTANT_2 = 8;
    public static final int CONSTANT_3 = 3;
    public static final boolean DEFAULT_ANTIALIASING_ON = true;
    public static final int DEFAULT_BOX_RESIZE_MARGIN = 20;
    private static final float DEFAULT_MAXIMUM_BOX_RESOLUTION = 0.0f;
    private static final float DEFAULT_RECTANGLE_MARGIN = 100.0f;
    private static final int DEFAULT_RECTANGLE_SHRINKING = 0;
    private static final boolean DEFAULT_RESIZE_POINTS_VISIBLE = true;
    private static final boolean DEFAULT_RESPECT_MAXIMUM_BORDER_RATIO = true;
    private static final boolean DEFAULT_RESPECT_MAXIMUM_BOX_RESOLUTION = false;
    private static final boolean DEFAULT_ROTATION_ENABLED = false;
    private static final int DEFAULT_ROTATION_SYMBOL_DISTANCE = 20;
    private static final int DEFAULT_ROTATION_SYMBOL_WIDTH = 30;
    private static final float DEFAULT_TOOL_STROKE_WIDTH = 3.0f;
    private static final int HIGHLIGHT_RECTANGLE_SHRINKING = 5;
    private static final float MAXIMAL_TOOL_STROKE_WIDTH = 8.0f;
    public static final float MAXIMUM_BORDER_RATIO = 2.0f;
    public static final int MINIMAL_BOX_SIZE = 3;
    private static final float MINIMAL_TOOL_STROKE_WIDTH = 1.0f;
    private static final float RIGHT_ANGLE = 90.0f;
    private static final int SIDES = 4;
    private static final float STRAIGHT_ANGLE = 180.0f;

    public static /* synthetic */ void getMAXIMUM_BORDER_RATIO$annotations() {
    }

    public static /* synthetic */ void getMINIMAL_BOX_SIZE$annotations() {
    }
}
