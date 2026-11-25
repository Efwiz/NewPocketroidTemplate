package org.catrobat.catroid.fast2d.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.catrobat.catroid.fast2d.components.TextureComponent;
import org.catrobat.catroid.fast2d.components.TransformComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/systems/Fast2DRenderSystem.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DRenderSystem extends BaseEntitySystem {
    private final SpriteBatch batch;
    private float camHalfH;
    private float camHalfW;
    private float camX;
    private float camY;
    private OrthographicCamera camera;
    protected ComponentMapper<TextureComponent> mTexture;
    protected ComponentMapper<TransformComponent> mTransform;
    private boolean needSort;

    public Fast2DRenderSystem(SpriteBatch batch) {
        super(Aspect.all((Class<? extends Component>[]) new Class[]{TransformComponent.class, TextureComponent.class}));
        this.needSort = false;
        this.batch = batch;
    }

    @Override // com.artemis.BaseSystem
    protected void initialize() {
        getSubscription().addSubscriptionListener(new EntitySubscription.SubscriptionListener() { // from class: org.catrobat.catroid.fast2d.systems.Fast2DRenderSystem.1
            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void inserted(IntBag entities) {
                Fast2DRenderSystem.this.needSort = true;
            }

            @Override // com.artemis.EntitySubscription.SubscriptionListener
            public void removed(IntBag entities) {
                Fast2DRenderSystem.this.needSort = true;
            }
        });
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void requestSort() {
        this.needSort = true;
    }

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
        IntBag entities = getSubscription().getEntities();
        int[] data = entities.getData();
        int size = entities.size();
        if (this.needSort && size > 1) {
            insertionSort(data, size);
            this.needSort = false;
        }
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            this.camX = orthographicCamera.position.x;
            this.camY = this.camera.position.y;
            this.camHalfW = this.camera.viewportWidth * this.camera.zoom * 0.5f;
            this.camHalfH = this.camera.viewportHeight * this.camera.zoom * 0.5f;
            this.batch.setProjectionMatrix(this.camera.combined);
        }
        this.batch.begin();
        for (int i = 0; i < size; i++) {
            renderEntity(data[i]);
        }
        this.batch.end();
    }

    private void renderEntity(int entityId) {
        TextureComponent textureComponent = (TextureComponent) this.mTexture.get(entityId);
        if (textureComponent.region == null) {
            return;
        }
        TransformComponent transformComponent = (TransformComponent) this.mTransform.get(entityId);
        float f = textureComponent.width * transformComponent.scaleX * 0.5f;
        float f2 = textureComponent.height * transformComponent.scaleY * 0.5f;
        if (transformComponent.x + f < this.camX - this.camHalfW || transformComponent.x - f > this.camX + this.camHalfW || transformComponent.y + f2 < this.camY - this.camHalfH || transformComponent.y - f2 > this.camY + this.camHalfH) {
            return;
        }
        this.batch.setColor(textureComponent.color);
        this.batch.draw(textureComponent.region, transformComponent.x - textureComponent.originX, transformComponent.y - textureComponent.originY, textureComponent.originX, textureComponent.originY, textureComponent.width, textureComponent.height, transformComponent.scaleX, transformComponent.scaleY, transformComponent.rotation);
    }

    private void insertionSort(int[] arr, int size) {
        for (int i = 1; i < size; i++) {
            int i2 = arr[i];
            float f = ((TransformComponent) this.mTransform.get(i2)).z;
            int i3 = i - 1;
            while (i3 >= 0 && ((TransformComponent) this.mTransform.get(arr[i3])).z > f) {
                arr[i3 + 1] = arr[i3];
                i3--;
            }
            arr[i3 + 1] = i2;
        }
    }
}
