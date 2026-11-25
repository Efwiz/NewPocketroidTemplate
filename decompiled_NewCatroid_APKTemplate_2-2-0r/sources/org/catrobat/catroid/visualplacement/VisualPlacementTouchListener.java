package org.catrobat.catroid.visualplacement;

import android.view.MotionEvent;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementTouchListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VisualPlacementTouchListener {
    private static final long JITTER_DELAY_THRESHOLD = 30;
    private static final float JITTER_DISTANCE_THRESHOLD = 100.0f;
    private static final long TAP_DELAY_THRESHOLD = 100;
    private Mode mode;
    private float previousX;
    private float previousY;
    private List<TouchEventData> recentTouchEventsData = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementTouchListener$Mode.class
      classes14.dex
     */
    public enum Mode {
        MOVE,
        TAP
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementTouchListener$TouchEventData.class
      classes14.dex
     */
    private final class TouchEventData {
        private final long timeStamp;
        private final float xCoordinate;
        private final float yCoordinate;

        private TouchEventData(long time, float x, float y) {
            this.timeStamp = time;
            this.xCoordinate = x;
            this.yCoordinate = y;
        }
    }

    private void setMode(Mode mode) {
        this.mode = mode;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0073  */
    /* JADX WARN: Code duplicated, block: B:17:0x0079  */
    /* JADX WARN: Code duplicated, block: B:18:0x0098  */
    public boolean onTouch(ImageView imageView, MotionEvent event, CoordinateInterface coordinateInterface) {
        float f;
        float f2;
        float f3;
        float f4;
        double d;
        if (event.getPointerId(0) != 0) {
            return false;
        }
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        long eventTime = event.getEventTime();
        int action = event.getAction();
        if (action == 0) {
            setMode(Mode.TAP);
            this.previousX = rawX;
            this.previousY = rawY;
            this.recentTouchEventsData.add(new TouchEventData(eventTime, rawX, rawY));
        } else if (action == 1) {
            if (this.mode == Mode.TAP) {
                imageView.setX(event.getX() - (imageView.getWidth() / 2.0f));
                imageView.setY(event.getY() - (imageView.getHeight() / 2.0f));
            } else {
                removeObsoleteTouchEventsData(eventTime);
                f = rawX - this.previousX;
                f2 = rawY - this.previousY;
                if (!this.recentTouchEventsData.isEmpty() && this.recentTouchEventsData.size() > 1) {
                    TouchEventData touchEventData = this.recentTouchEventsData.get(0);
                    f3 = rawX - touchEventData.xCoordinate;
                    f4 = rawY - touchEventData.yCoordinate;
                    d = (f3 * f3) + (f4 * f4);
                    if (d < 100.0d && d != 0.0d) {
                        f -= f3;
                        f2 -= f4;
                    }
                }
                imageView.setX(imageView.getX() + f);
                imageView.setY(imageView.getY() + f2);
            }
        } else if (action != 2) {
            if (action == 3) {
                if (this.mode == Mode.TAP) {
                    imageView.setX(event.getX() - (imageView.getWidth() / 2.0f));
                    imageView.setY(event.getY() - (imageView.getHeight() / 2.0f));
                } else {
                    removeObsoleteTouchEventsData(eventTime);
                    f = rawX - this.previousX;
                    f2 = rawY - this.previousY;
                    if (!this.recentTouchEventsData.isEmpty()) {
                        TouchEventData touchEventData2 = this.recentTouchEventsData.get(0);
                        f3 = rawX - touchEventData2.xCoordinate;
                        f4 = rawY - touchEventData2.yCoordinate;
                        d = (f3 * f3) + (f4 * f4);
                        if (d < 100.0d) {
                            f -= f3;
                            f2 -= f4;
                        }
                    }
                    imageView.setX(imageView.getX() + f);
                    imageView.setY(imageView.getY() + f2);
                }
            }
        } else if (eventTime - event.getDownTime() > TAP_DELAY_THRESHOLD) {
            setMode(Mode.MOVE);
            float f5 = rawX - this.previousX;
            float f6 = rawY - this.previousY;
            imageView.setX(imageView.getX() + f5);
            imageView.setY(imageView.getY() + f6);
            this.recentTouchEventsData.add(new TouchEventData(eventTime, rawX, rawY));
            this.previousX = rawX;
            this.previousY = rawY;
            removeObsoleteTouchEventsData(eventTime);
        }
        coordinateInterface.setXCoordinate(imageView.getX());
        coordinateInterface.setYCoordinate(-imageView.getY());
        return true;
    }

    private void removeObsoleteTouchEventsData(long timeStamp) {
        ArrayList arrayList = new ArrayList();
        for (TouchEventData touchEventData : this.recentTouchEventsData) {
            if (touchEventData != null) {
                if (timeStamp - touchEventData.timeStamp <= JITTER_DELAY_THRESHOLD) {
                    break;
                } else {
                    arrayList.add(touchEventData);
                }
            }
        }
        this.recentTouchEventsData.removeAll(arrayList);
    }
}
