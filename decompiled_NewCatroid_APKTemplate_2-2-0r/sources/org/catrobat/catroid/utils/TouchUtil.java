package org.catrobat.catroid.utils;

import android.graphics.PointF;
import android.util.SparseIntArray;
import java.util.ArrayList;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/TouchUtil.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class TouchUtil {
    private static SparseIntArray currentlyTouchingPointersToTouchIndex = new SparseIntArray();
    private static ArrayList<PointF> touches = new ArrayList<>();
    private static ArrayList<Boolean> isTouching = new ArrayList<>();

    private TouchUtil() {
    }

    public static void reset() {
        currentlyTouchingPointersToTouchIndex.clear();
        touches.clear();
        isTouching.clear();
    }

    public static void updatePosition(float x, float y, int pointer) {
        if (currentlyTouchingPointersToTouchIndex.indexOfKey(pointer) < 0) {
            touchDown(x, y, pointer);
        } else {
            touches.set(currentlyTouchingPointersToTouchIndex.get(pointer), new PointF(x, y));
        }
    }

    public static void touchDown(float x, float y, int pointer) {
        if (currentlyTouchingPointersToTouchIndex.indexOfKey(pointer) >= 0) {
            return;
        }
        currentlyTouchingPointersToTouchIndex.put(pointer, touches.size());
        touches.add(new PointF(x, y));
        isTouching.add(true);
        fireTouchEvent();
    }

    public static void touchUp(int pointer) {
        if (currentlyTouchingPointersToTouchIndex.indexOfKey(pointer) < 0) {
            return;
        }
        isTouching.set(currentlyTouchingPointersToTouchIndex.get(pointer), false);
        currentlyTouchingPointersToTouchIndex.delete(pointer);
    }

    public static double isTouching() {
        for (int i = 0; i < isTouching.size(); i++) {
            if (isTouching.get(i).booleanValue()) {
                return 1.0d;
            }
        }
        return 0.0d;
    }

    public static boolean isFingerTouching(int index) {
        if (index < 1 || index > isTouching.size()) {
            return false;
        }
        return isTouching.get(index - 1).booleanValue();
    }

    public static int getLastTouchIndex() {
        return touches.size();
    }

    public static float getX(int index) {
        if (index < 1 || index > isTouching.size()) {
            return 0.0f;
        }
        return touches.get(index - 1).x;
    }

    public static float getY(int index) {
        if (index < 1 || index > isTouching.size()) {
            return 0.0f;
        }
        return touches.get(index - 1).y;
    }

    public static void setDummyTouchForTest(float x, float y) {
        touches.add(new PointF(x, y));
        isTouching.add(false);
    }

    private static void fireTouchEvent() {
        ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new EventId(2), false));
    }

    public static ArrayList<PointF> getCurrentTouchingPoints() {
        ArrayList<PointF> arrayList = new ArrayList<>();
        for (int i = 0; i < currentlyTouchingPointersToTouchIndex.size(); i++) {
            arrayList.add(touches.get(currentlyTouchingPointersToTouchIndex.valueAt(i)));
        }
        return arrayList;
    }

    public static int getNumberOfCurrentTouches() {
        return currentlyTouchingPointersToTouchIndex.size();
    }

    public static int getIndexOfCurrentTouch(int index) {
        if (index < 1 || index > currentlyTouchingPointersToTouchIndex.size()) {
            return 0;
        }
        return currentlyTouchingPointersToTouchIndex.valueAt(index - 1) + 1;
    }
}
