package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ArrayReflection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/Animation.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Animation<T> {
    private float animationDuration;
    private float frameDuration;
    T[] keyFrames;
    private int lastFrameNumber;
    private float lastStateTime;
    private PlayMode playMode;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/Animation$PlayMode.class
      classes8.dex
     */
    public enum PlayMode {
        NORMAL,
        REVERSED,
        LOOP,
        LOOP_REVERSED,
        LOOP_PINGPONG,
        LOOP_RANDOM
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation(float f, Array<? extends T> array) {
        this.playMode = PlayMode.NORMAL;
        this.frameDuration = f;
        Object[] objArr = (Object[]) ArrayReflection.newInstance(array.items.getClass().getComponentType(), array.size);
        int i = array.size;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = array.get(i2);
        }
        setKeyFrames(objArr);
    }

    public Animation(float f, Array<? extends T> array, PlayMode playMode) {
        this(f, array);
        setPlayMode(playMode);
    }

    public Animation(float f, T... tArr) {
        this.playMode = PlayMode.NORMAL;
        this.frameDuration = f;
        setKeyFrames(tArr);
    }

    public T getKeyFrame(float f, boolean z) {
        PlayMode playMode = this.playMode;
        if (z && (playMode == PlayMode.NORMAL || this.playMode == PlayMode.REVERSED)) {
            if (this.playMode == PlayMode.NORMAL) {
                this.playMode = PlayMode.LOOP;
            } else {
                this.playMode = PlayMode.LOOP_REVERSED;
            }
        } else if (!z && this.playMode != PlayMode.NORMAL && this.playMode != PlayMode.REVERSED) {
            if (this.playMode == PlayMode.LOOP_REVERSED) {
                this.playMode = PlayMode.REVERSED;
            } else {
                this.playMode = PlayMode.LOOP;
            }
        }
        T keyFrame = getKeyFrame(f);
        this.playMode = playMode;
        return keyFrame;
    }

    public T getKeyFrame(float f) {
        return this.keyFrames[getKeyFrameIndex(f)];
    }

    public int getKeyFrameIndex(float f) {
        if (this.keyFrames.length == 1) {
            return 0;
        }
        int iMin = (int) (f / this.frameDuration);
        switch (AnonymousClass1.$SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[this.playMode.ordinal()]) {
            case 1:
                iMin = Math.min(this.keyFrames.length - 1, iMin);
                break;
            case 2:
                iMin %= this.keyFrames.length;
                break;
            case 3:
                T[] tArr = this.keyFrames;
                iMin %= (tArr.length * 2) - 2;
                if (iMin >= tArr.length) {
                    iMin = (tArr.length - 2) - (iMin - tArr.length);
                }
                break;
            case 4:
                if (((int) (this.lastStateTime / this.frameDuration)) != iMin) {
                    iMin = MathUtils.random(this.keyFrames.length - 1);
                } else {
                    iMin = this.lastFrameNumber;
                }
                break;
            case 5:
                iMin = Math.max((this.keyFrames.length - iMin) - 1, 0);
                break;
            case 6:
                T[] tArr2 = this.keyFrames;
                iMin = (tArr2.length - (iMin % tArr2.length)) - 1;
                break;
        }
        this.lastFrameNumber = iMin;
        this.lastStateTime = f;
        return iMin;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/Animation$1.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.graphics.g2d.Animation$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode;

        static {
            int[] iArr = new int[PlayMode.values().length];
            $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode = iArr;
            try {
                iArr[PlayMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[PlayMode.LOOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[PlayMode.LOOP_PINGPONG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[PlayMode.LOOP_RANDOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[PlayMode.REVERSED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[PlayMode.LOOP_REVERSED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public T[] getKeyFrames() {
        return this.keyFrames;
    }

    protected void setKeyFrames(T... tArr) {
        this.keyFrames = tArr;
        this.animationDuration = tArr.length * this.frameDuration;
    }

    public PlayMode getPlayMode() {
        return this.playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public boolean isAnimationFinished(float f) {
        return this.keyFrames.length - 1 < ((int) (f / this.frameDuration));
    }

    public void setFrameDuration(float f) {
        this.frameDuration = f;
        this.animationDuration = this.keyFrames.length * f;
    }

    public float getFrameDuration() {
        return this.frameDuration;
    }

    public float getAnimationDuration() {
        return this.animationDuration;
    }
}
