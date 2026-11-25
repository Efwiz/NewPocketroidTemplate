package net.mgsx.gltf.scene3d.animation;

import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.scene.Scene;

/* JADX INFO: loaded from: classes12.dex */
public class AnimationsPlayer {
    private Array<AnimationController> controllers = new Array<>();
    private Scene scene;

    public AnimationsPlayer(Scene scene) {
        this.scene = scene;
    }

    public void addAnimations(Array<AnimationController.AnimationDesc> array) {
        Array.ArrayIterator<AnimationController.AnimationDesc> it = array.iterator();
        while (it.hasNext()) {
            addAnimation(it.next());
        }
    }

    public void addAnimation(AnimationController.AnimationDesc animationDesc) {
        AnimationControllerHack animationControllerHack = new AnimationControllerHack(this.scene.modelInstance);
        animationControllerHack.calculateTransforms = false;
        animationControllerHack.setAnimationDesc(animationDesc);
        this.controllers.add(animationControllerHack);
    }

    public void removeAnimation(Animation animation) {
        for (int i = this.controllers.size - 1; i >= 0; i--) {
            if (this.controllers.get(i).current != null && this.controllers.get(i).current.animation == animation) {
                this.controllers.removeIndex(i);
            }
        }
    }

    public void clearAnimations() {
        this.controllers.clear();
        if (this.scene.animationController != null) {
            this.scene.animationController.setAnimation((String) null);
        }
    }

    public void playAll() {
        playAll(false);
    }

    public void loopAll() {
        playAll(true);
    }

    public void playAll(boolean z) {
        clearAnimations();
        int i = this.scene.modelInstance.animations.size;
        for (int i2 = 0; i2 < i; i2++) {
            AnimationControllerHack animationControllerHack = new AnimationControllerHack(this.scene.modelInstance);
            animationControllerHack.calculateTransforms = false;
            animationControllerHack.setAnimation(this.scene.modelInstance.animations.get(i2), z ? -1 : 1);
            this.controllers.add(animationControllerHack);
        }
    }

    public void stopAll() {
        clearAnimations();
    }

    public void update(float f) {
        if (this.controllers.size > 0) {
            Array.ArrayIterator<AnimationController> it = this.controllers.iterator();
            while (it.hasNext()) {
                it.next().update(f);
            }
            this.scene.modelInstance.calculateTransforms();
            return;
        }
        if (this.scene.animationController != null) {
            this.scene.animationController.update(f);
        }
    }
}
