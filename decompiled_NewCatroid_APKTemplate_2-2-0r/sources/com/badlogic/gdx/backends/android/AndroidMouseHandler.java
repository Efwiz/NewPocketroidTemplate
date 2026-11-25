package com.badlogic.gdx.backends.android;

import android.view.MotionEvent;
import com.badlogic.gdx.Gdx;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes8.dex */
public class AndroidMouseHandler {
    private int deltaX = 0;
    private int deltaY = 0;

    public boolean onGenericMotion(MotionEvent motionEvent, DefaultAndroidInput defaultAndroidInput) {
        if ((motionEvent.getSource() & 2) == 0) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        long jNanoTime = System.nanoTime();
        synchronized (defaultAndroidInput) {
            try {
                if (action == 7) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (x != this.deltaX || y != this.deltaY) {
                        postTouchEvent(defaultAndroidInput, 4, x, y, 0, 0, jNanoTime);
                        this.deltaX = x;
                        this.deltaY = y;
                    }
                } else if (action == 8) {
                    postTouchEvent(defaultAndroidInput, 3, 0, 0, (int) (-Math.signum(motionEvent.getAxisValue(10))), (int) (-Math.signum(motionEvent.getAxisValue(9))), jNanoTime);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Gdx.f82app.getGraphics().requestRendering();
        return true;
    }

    private void logAction(int i) {
        String str;
        if (i == 9) {
            str = "HOVER_ENTER";
        } else if (i == 7) {
            str = "HOVER_MOVE";
        } else if (i == 10) {
            str = "HOVER_EXIT";
        } else if (i == 8) {
            str = "SCROLL";
        } else {
            str = "UNKNOWN (" + i + Constant.AFTER_QUTO;
        }
        Gdx.f82app.log("AndroidMouseHandler", "action " + str);
    }

    private void postTouchEvent(DefaultAndroidInput defaultAndroidInput, int i, int i2, int i3, int i4, int i5, long j) {
        DefaultAndroidInput.TouchEvent touchEventObtain = defaultAndroidInput.usedTouchEvents.obtain();
        touchEventObtain.timeStamp = j;
        touchEventObtain.x = i2;
        touchEventObtain.y = i3;
        touchEventObtain.type = i;
        touchEventObtain.scrollAmountX = i4;
        touchEventObtain.scrollAmountY = i5;
        defaultAndroidInput.touchEvents.add(touchEventObtain);
    }
}
