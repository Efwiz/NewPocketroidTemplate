package com.badlogic.gdx.backends.android;

import android.content.Context;
import android.view.MotionEvent;
import com.badlogic.gdx.Gdx;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public class AndroidTouchHandler {
    private int toGdxButton(int i) {
        if (i == 0 || i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        if (i == 8) {
            return 3;
        }
        return i == 16 ? 4 : -1;
    }

    /* JADX WARN: Failed to calculate best type for var: r0v10 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v10 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v10 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v10 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryToForceImmutableTypes(FixTypesVisitor.java:946)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v10 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v10 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v11 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v11 ??, new type: boolean[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v12 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v12 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v12 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v12 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryToForceImmutableTypes(FixTypesVisitor.java:946)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v12 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v12 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v9 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:159)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:136)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:241)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:224)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v9 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryToForceImmutableTypes(FixTypesVisitor.java:946)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to calculate best type for var: r0v9 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v9 ??, new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to set immutable type for var: r0v11 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v11 ??, new type: boolean[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:102)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Failed to set immutable type for var: r0v11 boolean[]
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v11 boolean[], new type: boolean[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:102)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryToForceImmutableTypes(FixTypesVisitor.java:946)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    /* JADX WARN: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r0v9 int[], new type: int[]
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.applyResolvedVars(TypeSearch.java:100)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:76)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.runMultiVariableSearch(FixTypesVisitor.java:119)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException
     */
    public void onTouch(MotionEvent motionEvent, DefaultAndroidInput defaultAndroidInput) {
        int i;
        int i2;
        int i3;
        int i4;
        int action = motionEvent.getAction() & 255;
        int action2 = (motionEvent.getAction() & 65280) >> 8;
        int pointerId = motionEvent.getPointerId(action2);
        long jNanoTime = System.nanoTime();
        synchronized (defaultAndroidInput) {
            int i5 = 20;
            try {
                switch (action) {
                    case 0:
                    case 5:
                        int freePointerIndex = defaultAndroidInput.getFreePointerIndex();
                        if (freePointerIndex < 20) {
                            defaultAndroidInput.realId[freePointerIndex] = pointerId;
                            int x = (int) motionEvent.getX(action2);
                            int y = (int) motionEvent.getY(action2);
                            int gdxButton = toGdxButton(motionEvent.getButtonState());
                            if (gdxButton != -1) {
                                i = gdxButton;
                                postTouchEvent(defaultAndroidInput, 0, x, y, freePointerIndex, i, jNanoTime);
                            } else {
                                i = gdxButton;
                            }
                            defaultAndroidInput.touchX[freePointerIndex] = x;
                            defaultAndroidInput.touchY[freePointerIndex] = y;
                            defaultAndroidInput.deltaX[freePointerIndex] = 0;
                            defaultAndroidInput.deltaY[freePointerIndex] = 0;
                            int i6 = i;
                            defaultAndroidInput.touched[freePointerIndex] = i6 != -1;
                            defaultAndroidInput.button[freePointerIndex] = i6;
                            defaultAndroidInput.pressure[freePointerIndex] = motionEvent.getPressure(action2);
                        }
                        break;
                    case 1:
                    case 3:
                    case 4:
                    case 6:
                        int iLookUpPointerIndex = defaultAndroidInput.lookUpPointerIndex(pointerId);
                        if (iLookUpPointerIndex != -1 && iLookUpPointerIndex < 20) {
                            defaultAndroidInput.realId[iLookUpPointerIndex] = -1;
                            int x2 = (int) motionEvent.getX(action2);
                            int y2 = (int) motionEvent.getY(action2);
                            int i7 = defaultAndroidInput.button[iLookUpPointerIndex];
                            if (i7 == -1) {
                                i2 = x2;
                            } else if (action == 3) {
                                i2 = x2;
                                postTouchEvent(defaultAndroidInput, 5, x2, y2, iLookUpPointerIndex, i7, jNanoTime);
                            } else {
                                i2 = x2;
                                postTouchEvent(defaultAndroidInput, 1, i2, y2, iLookUpPointerIndex, i7, jNanoTime);
                            }
                            defaultAndroidInput.touchX[iLookUpPointerIndex] = i2;
                            defaultAndroidInput.touchY[iLookUpPointerIndex] = y2;
                            defaultAndroidInput.deltaX[iLookUpPointerIndex] = r7;
                            defaultAndroidInput.deltaY[iLookUpPointerIndex] = r7;
                            defaultAndroidInput.touched[iLookUpPointerIndex] = r7;
                            defaultAndroidInput.button[iLookUpPointerIndex] = r7;
                            defaultAndroidInput.pressure[iLookUpPointerIndex] = 0.0f;
                            if (action == 3) {
                                Arrays.fill(defaultAndroidInput.realId, -1);
                                Arrays.fill(defaultAndroidInput.touched, false);
                            }
                        }
                        break;
                    case 2:
                        int pointerCount = motionEvent.getPointerCount();
                        int i8 = 0;
                        while (i8 < pointerCount) {
                            int pointerId2 = motionEvent.getPointerId(i8);
                            int x3 = (int) motionEvent.getX(i8);
                            int y3 = (int) motionEvent.getY(i8);
                            int iLookUpPointerIndex2 = defaultAndroidInput.lookUpPointerIndex(pointerId2);
                            if (iLookUpPointerIndex2 == -1) {
                                i8 = i8;
                            } else if (iLookUpPointerIndex2 < i5) {
                                int i9 = defaultAndroidInput.button[iLookUpPointerIndex2];
                                if (i9 != -1) {
                                    i3 = y3;
                                    i4 = x3;
                                    postTouchEvent(defaultAndroidInput, 2, x3, y3, iLookUpPointerIndex2, i9, jNanoTime);
                                } else {
                                    i3 = y3;
                                    i4 = x3;
                                    postTouchEvent(defaultAndroidInput, 4, i4, i3, iLookUpPointerIndex2, 0, jNanoTime);
                                }
                                defaultAndroidInput.deltaX[iLookUpPointerIndex2] = i4 - defaultAndroidInput.touchX[iLookUpPointerIndex2];
                                defaultAndroidInput.deltaY[iLookUpPointerIndex2] = i3 - defaultAndroidInput.touchY[iLookUpPointerIndex2];
                                defaultAndroidInput.touchX[iLookUpPointerIndex2] = i4;
                                defaultAndroidInput.touchY[iLookUpPointerIndex2] = i3;
                                defaultAndroidInput.pressure[iLookUpPointerIndex2] = motionEvent.getPressure(i8);
                            }
                            i8++;
                            i5 = 20;
                            break;
                        }
                        break;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Gdx.f82app.getGraphics().requestRendering();
    }

    private void logAction(int i, int i2) {
        String str;
        if (i == 0) {
            str = "DOWN";
        } else if (i == 5) {
            str = "POINTER DOWN";
        } else if (i == 1) {
            str = "UP";
        } else if (i == 6) {
            str = "POINTER UP";
        } else if (i == 4) {
            str = "OUTSIDE";
        } else if (i == 3) {
            str = "CANCEL";
        } else if (i == 2) {
            str = "MOVE";
        } else {
            str = "UNKNOWN (" + i + Constant.AFTER_QUTO;
        }
        Gdx.f82app.log("AndroidMultiTouchHandler", "action " + str + ", Android pointer id: " + i2);
    }

    private void postTouchEvent(DefaultAndroidInput defaultAndroidInput, int i, int i2, int i3, int i4, int i5, long j) {
        DefaultAndroidInput.TouchEvent touchEventObtain = defaultAndroidInput.usedTouchEvents.obtain();
        touchEventObtain.timeStamp = j;
        touchEventObtain.pointer = i4;
        touchEventObtain.x = i2;
        touchEventObtain.y = i3;
        touchEventObtain.type = i;
        touchEventObtain.button = i5;
        defaultAndroidInput.touchEvents.add(touchEventObtain);
    }

    public boolean supportsMultitouch(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch");
    }
}
