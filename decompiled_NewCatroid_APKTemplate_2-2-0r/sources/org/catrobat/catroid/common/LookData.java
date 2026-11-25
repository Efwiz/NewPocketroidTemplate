package org.catrobat.catroid.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.utils.ImageEditing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/LookData.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class LookData implements Cloneable, Nameable, Serializable {
    private static final String TAG = "LookData";
    private static final transient int THUMBNAIL_HEIGHT = 150;
    private static final transient int THUMBNAIL_WIDTH = 150;
    private static final long serialVersionUID = 1;
    private transient CollisionInformation collisionInformation;
    protected transient File file;

    @XStreamAsAttribute
    public String fileName;
    protected transient Integer height;
    private boolean isWebRequest;

    @XStreamAsAttribute
    private String lookId;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    @XStreamAsAttribute
    public String f512name;
    protected transient Pixmap pixmap;
    transient TextureRegion textureRegion;
    private transient Bitmap thumbnailBitmap;
    private boolean valid;
    protected transient Integer width;

    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
    }

    public void draw(Batch batch, float alpha) {
    }

    public LookData() {
        this.pixmap = null;
        this.textureRegion = null;
        this.collisionInformation = null;
        this.valid = true;
        this.isWebRequest = false;
    }

    public LookData(String name2) {
        this.pixmap = null;
        this.textureRegion = null;
        this.collisionInformation = null;
        this.valid = true;
        this.isWebRequest = false;
        this.f512name = name2;
        this.lookId = UUID.randomUUID().toString();
    }

    public LookData(String name2, File file) {
        this.pixmap = null;
        this.textureRegion = null;
        this.collisionInformation = null;
        this.valid = true;
        this.isWebRequest = false;
        this.f512name = name2;
        this.file = file;
        this.fileName = file.getName();
        this.lookId = UUID.randomUUID().toString();
    }

    public String getLookId() {
        return this.lookId;
    }

    public void setLookId(String lookId) {
        this.lookId = lookId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.lookId, ((LookData) o).lookId);
    }

    public int hashCode() {
        return Objects.hash(this.lookId);
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f512name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f512name = name2;
    }

    public boolean getIsWebRequest() {
        return this.isWebRequest;
    }

    public void setIsWebRequest(Boolean isWebRequest) {
        this.isWebRequest = isWebRequest.booleanValue();
    }

    public String getXstreamFileName() {
        if (this.file != null) {
            throw new IllegalStateException("This should be used only to deserialize the Object. You should use @getFile() instead.");
        }
        return this.fileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public void dispose() {
        Pixmap pixmap = this.pixmap;
        if (pixmap != null) {
            pixmap.dispose();
            this.pixmap = null;
        }
        TextureRegion textureRegion = this.textureRegion;
        if (textureRegion != null) {
            textureRegion.getTexture().dispose();
            this.textureRegion = null;
        }
    }

    public LookData clone() {
        try {
            return new LookData(this.f512name, StorageOperations.duplicateFile(this.file));
        } catch (IOException unused) {
            throw new RuntimeException(TAG + ": Could not copy file: " + this.file.getAbsolutePath());
        }
    }

    public TextureRegion getTextureRegion() {
        if (this.textureRegion == null) {
            this.textureRegion = new TextureRegion(new Texture(getPixmap()));
        }
        return this.textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Pixmap getPixmap() {
        if (this.pixmap == null) {
            try {
                this.pixmap = new Pixmap(Gdx.files.absolute(this.file.getAbsolutePath()));
            } catch (GdxRuntimeException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                if (e.getMessage().startsWith("Couldn't load file:")) {
                    this.pixmap = new Pixmap(1, 1, Pixmap.Format.Alpha);
                }
            } catch (NullPointerException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
            }
        }
        return this.pixmap;
    }

    public void setPixmap(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    public Bitmap getThumbnailBitmap() {
        File file;
        if (this.thumbnailBitmap == null && (file = this.file) != null) {
            this.thumbnailBitmap = ImageEditing.getScaledBitmapFromPath(file.getAbsolutePath(), 150, 150, ImageEditing.ResizeType.STAY_IN_RECTANGLE_WITH_SAME_ASPECT_RATIO, false);
        }
        return this.thumbnailBitmap;
    }

    public void invalidateThumbnailBitmap() {
        this.thumbnailBitmap = null;
    }

    public int[] getMeasure() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(this.file.getAbsolutePath(), options);
        this.width = Integer.valueOf(options.outWidth);
        this.height = Integer.valueOf(options.outHeight);
        return new int[]{this.width.intValue(), this.height.intValue()};
    }

    public void clearCollisionInformation() {
        this.collisionInformation = null;
    }

    public CollisionInformation getCollisionInformation() {
        if (this.collisionInformation == null) {
            this.collisionInformation = new CollisionInformation(this);
        }
        return this.collisionInformation;
    }

    public String getImageMimeType() {
        String absolutePath = this.file.getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(absolutePath, options);
        return options.outMimeType;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void invalidate() {
        this.valid = false;
    }
}
