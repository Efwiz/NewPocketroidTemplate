package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/renderers/OrthoCachedTiledMapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class OrthoCachedTiledMapRenderer implements TiledMapRenderer, Disposable {
    protected static final int NUM_VERTICES = 20;
    private static final float tolerance = 1.0E-5f;
    protected boolean blending;
    protected final Rectangle cacheBounds;
    protected boolean cached;
    protected boolean canCacheMoreE;
    protected boolean canCacheMoreN;
    protected boolean canCacheMoreS;
    protected boolean canCacheMoreW;
    protected int count;
    protected Rectangle imageBounds;
    protected final TiledMap map;
    protected float maxTileHeight;
    protected float maxTileWidth;
    protected float overCache;
    protected final SpriteCache spriteCache;
    protected float unitScale;
    protected final float[] vertices;
    protected final Rectangle viewBounds;

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderObject(MapObject mapObject) {
    }

    public OrthoCachedTiledMapRenderer(TiledMap tiledMap) {
        this(tiledMap, 1.0f, 2000);
    }

    public OrthoCachedTiledMapRenderer(TiledMap tiledMap, float f) {
        this(tiledMap, f, 2000);
    }

    public OrthoCachedTiledMapRenderer(TiledMap tiledMap, float f, int i) {
        this.vertices = new float[20];
        this.viewBounds = new Rectangle();
        this.cacheBounds = new Rectangle();
        this.overCache = 0.5f;
        this.imageBounds = new Rectangle();
        this.map = tiledMap;
        this.unitScale = f;
        this.spriteCache = new SpriteCache(i, true);
    }

    @Override // com.badlogic.gdx.maps.MapRenderer
    public void setView(OrthographicCamera orthographicCamera) {
        this.spriteCache.setProjectionMatrix(orthographicCamera.combined);
        float f = (orthographicCamera.viewportWidth * orthographicCamera.zoom) + (this.maxTileWidth * 2.0f * this.unitScale);
        float f2 = (orthographicCamera.viewportHeight * orthographicCamera.zoom) + (this.maxTileHeight * 2.0f * this.unitScale);
        this.viewBounds.set(orthographicCamera.position.x - (f / 2.0f), orthographicCamera.position.y - (f2 / 2.0f), f, f2);
        if ((!this.canCacheMoreW || this.viewBounds.x >= this.cacheBounds.x - tolerance) && ((!this.canCacheMoreS || this.viewBounds.y >= this.cacheBounds.y - tolerance) && ((!this.canCacheMoreE || this.viewBounds.x + this.viewBounds.width <= this.cacheBounds.x + this.cacheBounds.width + tolerance) && (!this.canCacheMoreN || this.viewBounds.y + this.viewBounds.height <= this.cacheBounds.y + this.cacheBounds.height + tolerance)))) {
            return;
        }
        this.cached = false;
    }

    @Override // com.badlogic.gdx.maps.MapRenderer
    public void setView(Matrix4 matrix4, float f, float f2, float f3, float f4) {
        this.spriteCache.setProjectionMatrix(matrix4);
        float f5 = this.maxTileWidth;
        float f6 = this.unitScale;
        float f7 = f - (f5 * f6);
        float f8 = this.maxTileHeight;
        this.viewBounds.set(f7, f2 - (f8 * f6), f3 + (f5 * 2.0f * f6), f4 + (f8 * 2.0f * f6));
        if ((!this.canCacheMoreW || this.viewBounds.x >= this.cacheBounds.x - tolerance) && ((!this.canCacheMoreS || this.viewBounds.y >= this.cacheBounds.y - tolerance) && ((!this.canCacheMoreE || this.viewBounds.x + this.viewBounds.width <= this.cacheBounds.x + this.cacheBounds.width + tolerance) && (!this.canCacheMoreN || this.viewBounds.y + this.viewBounds.height <= this.cacheBounds.y + this.cacheBounds.height + tolerance)))) {
            return;
        }
        this.cached = false;
    }

    @Override // com.badlogic.gdx.maps.MapRenderer
    public void render() {
        if (!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float f = this.viewBounds.width * this.overCache;
            float f2 = this.viewBounds.height * this.overCache;
            this.cacheBounds.x = this.viewBounds.x - f;
            this.cacheBounds.y = this.viewBounds.y - f2;
            this.cacheBounds.width = this.viewBounds.width + (f * 2.0f);
            this.cacheBounds.height = this.viewBounds.height + (f2 * 2.0f);
            for (MapLayer mapLayer : this.map.getLayers()) {
                this.spriteCache.beginCache();
                if (mapLayer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) mapLayer);
                } else if (mapLayer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) mapLayer);
                }
                this.spriteCache.endCache();
            }
        }
        if (this.blending) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        this.spriteCache.begin();
        MapLayers layers = this.map.getLayers();
        int count = layers.getCount();
        for (int i = 0; i < count; i++) {
            MapLayer mapLayer2 = layers.get(i);
            if (mapLayer2.isVisible()) {
                this.spriteCache.draw(i);
                renderObjects(mapLayer2);
            }
        }
        this.spriteCache.end();
        if (this.blending) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    @Override // com.badlogic.gdx.maps.MapRenderer
    public void render(int[] iArr) {
        if (!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float f = this.viewBounds.width * this.overCache;
            float f2 = this.viewBounds.height * this.overCache;
            this.cacheBounds.x = this.viewBounds.x - f;
            this.cacheBounds.y = this.viewBounds.y - f2;
            this.cacheBounds.width = this.viewBounds.width + (f * 2.0f);
            this.cacheBounds.height = this.viewBounds.height + (f2 * 2.0f);
            for (MapLayer mapLayer : this.map.getLayers()) {
                this.spriteCache.beginCache();
                if (mapLayer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) mapLayer);
                } else if (mapLayer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) mapLayer);
                }
                this.spriteCache.endCache();
            }
        }
        if (this.blending) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        this.spriteCache.begin();
        MapLayers layers = this.map.getLayers();
        for (int i : iArr) {
            MapLayer mapLayer2 = layers.get(i);
            if (mapLayer2.isVisible()) {
                this.spriteCache.draw(i);
                renderObjects(mapLayer2);
            }
        }
        this.spriteCache.end();
        if (this.blending) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderObjects(MapLayer mapLayer) {
        Iterator<MapObject> it = mapLayer.getObjects().iterator();
        while (it.hasNext()) {
            renderObject(it.next());
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderTileLayer(TiledMapTileLayer tiledMapTileLayer) {
        TiledMapTile tile;
        int i;
        boolean z;
        float floatBits = Color.toFloatBits(tiledMapTileLayer.getCombinedTintColor().r, tiledMapTileLayer.getCombinedTintColor().g, tiledMapTileLayer.getCombinedTintColor().b, tiledMapTileLayer.getOpacity() * tiledMapTileLayer.getCombinedTintColor().a);
        int width = tiledMapTileLayer.getWidth();
        int height = tiledMapTileLayer.getHeight();
        float tileWidth = tiledMapTileLayer.getTileWidth() * this.unitScale;
        float tileHeight = tiledMapTileLayer.getTileHeight() * this.unitScale;
        float renderOffsetX = (tiledMapTileLayer.getRenderOffsetX() * this.unitScale) - (this.viewBounds.x * (tiledMapTileLayer.getParallaxX() - 1.0f));
        float parallaxY = ((-tiledMapTileLayer.getRenderOffsetY()) * this.unitScale) - (this.viewBounds.y * (tiledMapTileLayer.getParallaxY() - 1.0f));
        boolean z2 = false;
        int iMax = Math.max(0, (int) ((this.cacheBounds.x - renderOffsetX) / tileWidth));
        int iMin = Math.min(width, (int) ((((this.cacheBounds.x + this.cacheBounds.width) + tileWidth) - renderOffsetX) / tileWidth));
        int iMax2 = Math.max(0, (int) ((this.cacheBounds.y - parallaxY) / tileHeight));
        int iMin2 = Math.min(height, (int) ((((this.cacheBounds.y + this.cacheBounds.height) + tileHeight) - parallaxY) / tileHeight));
        int i2 = 1;
        this.canCacheMoreN = iMin2 < height;
        this.canCacheMoreE = iMin < width;
        this.canCacheMoreW = iMax > 0;
        this.canCacheMoreS = iMax2 > 0;
        float[] fArr = this.vertices;
        while (iMin2 >= iMax2) {
            int i3 = iMax;
            while (i3 < iMin) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(i3, iMin2);
                if (cell == null || (tile = cell.getTile()) == null) {
                    z = z2;
                    i = i2;
                } else {
                    this.count += i2;
                    boolean flipHorizontally = cell.getFlipHorizontally();
                    boolean flipVertically = cell.getFlipVertically();
                    int rotation = cell.getRotation();
                    TextureRegion textureRegion = tile.getTextureRegion();
                    Texture texture = textureRegion.getTexture();
                    float offsetX = (i3 * tileWidth) + (tile.getOffsetX() * this.unitScale) + renderOffsetX;
                    float offsetY = (iMin2 * tileHeight) + (tile.getOffsetY() * this.unitScale) + parallaxY;
                    float regionWidth = (textureRegion.getRegionWidth() * this.unitScale) + offsetX;
                    float regionHeight = (textureRegion.getRegionHeight() * this.unitScale) + offsetY;
                    float width2 = 0.5f / texture.getWidth();
                    float height2 = 0.5f / texture.getHeight();
                    float u = textureRegion.getU() + width2;
                    float v2 = textureRegion.getV2() - height2;
                    float u2 = textureRegion.getU2() - width2;
                    float v = textureRegion.getV() + height2;
                    fArr[0] = offsetX;
                    fArr[1] = offsetY;
                    fArr[2] = floatBits;
                    fArr[3] = u;
                    fArr[4] = v2;
                    fArr[5] = offsetX;
                    fArr[6] = regionHeight;
                    fArr[7] = floatBits;
                    fArr[8] = u;
                    fArr[9] = v;
                    fArr[10] = regionWidth;
                    fArr[11] = regionHeight;
                    fArr[12] = floatBits;
                    fArr[13] = u2;
                    fArr[14] = v;
                    fArr[15] = regionWidth;
                    fArr[16] = offsetY;
                    fArr[17] = floatBits;
                    fArr[18] = u2;
                    fArr[19] = v2;
                    if (flipHorizontally) {
                        fArr[3] = u2;
                        fArr[13] = u;
                        fArr[8] = u2;
                        fArr[18] = u;
                    }
                    if (flipVertically) {
                        fArr[4] = v;
                        fArr[14] = v2;
                        fArr[9] = v2;
                        fArr[19] = v;
                    }
                    i = 1;
                    if (rotation != 0) {
                        if (rotation == 1) {
                            float f = fArr[4];
                            fArr[4] = fArr[9];
                            fArr[9] = fArr[14];
                            fArr[14] = fArr[19];
                            fArr[19] = f;
                            float f2 = fArr[3];
                            fArr[3] = fArr[8];
                            fArr[8] = fArr[13];
                            fArr[13] = fArr[18];
                            fArr[18] = f2;
                        } else if (rotation == 2) {
                            float f3 = fArr[3];
                            fArr[3] = fArr[13];
                            fArr[13] = f3;
                            float f4 = fArr[8];
                            fArr[8] = fArr[18];
                            fArr[18] = f4;
                            float f5 = fArr[4];
                            fArr[4] = fArr[14];
                            fArr[14] = f5;
                            float f6 = fArr[9];
                            fArr[9] = fArr[19];
                            fArr[19] = f6;
                        } else if (rotation == 3) {
                            float f7 = fArr[4];
                            fArr[4] = fArr[19];
                            fArr[19] = fArr[14];
                            fArr[14] = fArr[9];
                            fArr[9] = f7;
                            float f8 = fArr[3];
                            fArr[3] = fArr[18];
                            fArr[18] = fArr[13];
                            fArr[13] = fArr[8];
                            fArr[8] = f8;
                        }
                    }
                    z = false;
                    this.spriteCache.add(texture, fArr, 0, 20);
                }
                i3++;
                z2 = z;
                i2 = i;
                renderOffsetX = renderOffsetX;
                iMax2 = iMax2;
                iMax = iMax;
                tileHeight = tileHeight;
                tileWidth = tileWidth;
                parallaxY = parallaxY;
                iMin = iMin;
            }
            iMin2--;
            renderOffsetX = renderOffsetX;
            parallaxY = parallaxY;
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderImageLayer(TiledMapImageLayer tiledMapImageLayer) {
        float f;
        float f2;
        float f3;
        float f4;
        Color combinedTintColor = tiledMapImageLayer.getCombinedTintColor();
        boolean zSupportsTransparency = tiledMapImageLayer.supportsTransparency();
        float f5 = zSupportsTransparency ? 1.0f : combinedTintColor.a;
        float floatBits = Color.toFloatBits(combinedTintColor.r * f5, combinedTintColor.g * f5, combinedTintColor.b * f5, tiledMapImageLayer.getOpacity() * (zSupportsTransparency ? combinedTintColor.a : 1.0f));
        float[] fArr = this.vertices;
        TextureRegion textureRegion = tiledMapImageLayer.getTextureRegion();
        if (textureRegion == null) {
            return;
        }
        float x = tiledMapImageLayer.getX();
        float y = tiledMapImageLayer.getY();
        float parallaxX = (x * this.unitScale) - (this.viewBounds.x * (tiledMapImageLayer.getParallaxX() - 1.0f));
        float parallaxY = (y * this.unitScale) - (this.viewBounds.y * (tiledMapImageLayer.getParallaxY() - 1.0f));
        float regionWidth = (textureRegion.getRegionWidth() * this.unitScale) + parallaxX;
        float regionHeight = (textureRegion.getRegionHeight() * this.unitScale) + parallaxY;
        this.imageBounds.set(parallaxX, parallaxY, regionWidth - parallaxX, regionHeight - parallaxY);
        int i = 0;
        if (!tiledMapImageLayer.isRepeatX() && !tiledMapImageLayer.isRepeatY()) {
            float u = textureRegion.getU();
            float v2 = textureRegion.getV2();
            float u2 = textureRegion.getU2();
            float v = textureRegion.getV();
            fArr[0] = parallaxX;
            fArr[1] = parallaxY;
            fArr[2] = floatBits;
            fArr[3] = u;
            fArr[4] = v2;
            fArr[5] = parallaxX;
            fArr[6] = regionHeight;
            fArr[7] = floatBits;
            fArr[8] = u;
            fArr[9] = v;
            fArr[10] = regionWidth;
            fArr[11] = regionHeight;
            fArr[12] = floatBits;
            fArr[13] = u2;
            fArr[14] = v;
            fArr[15] = regionWidth;
            fArr[16] = parallaxY;
            fArr[17] = floatBits;
            fArr[18] = u2;
            fArr[19] = v2;
            this.spriteCache.add(textureRegion.getTexture(), fArr, 0, 20);
            return;
        }
        int iCeil = tiledMapImageLayer.isRepeatX() ? (int) Math.ceil((this.cacheBounds.width / this.imageBounds.width) + 4.0f) : 0;
        int iCeil2 = tiledMapImageLayer.isRepeatY() ? (int) Math.ceil((this.cacheBounds.height / this.imageBounds.height) + 4.0f) : 0;
        float f6 = this.cacheBounds.x;
        float f7 = this.cacheBounds.y;
        float f8 = f6 - (f6 % this.imageBounds.width);
        float f9 = f7 - (f7 % this.imageBounds.height);
        for (int i2 = 0; i2 <= iCeil; i2++) {
            int i3 = i;
            while (i3 <= iCeil2) {
                if (tiledMapImageLayer.isRepeatX()) {
                    f2 = ((i2 - 2) * this.imageBounds.width) + f8 + (parallaxX % this.imageBounds.width);
                    f = this.imageBounds.width + f2;
                } else {
                    f = regionWidth;
                    f2 = parallaxX;
                }
                if (tiledMapImageLayer.isRepeatY()) {
                    f3 = ((i3 - 2) * this.imageBounds.height) + f9 + (parallaxY % this.imageBounds.height);
                    f4 = this.imageBounds.height + f3;
                } else {
                    f3 = parallaxY;
                    f4 = regionHeight;
                }
                float u3 = textureRegion.getU();
                float v3 = textureRegion.getV2();
                float u4 = textureRegion.getU2();
                float v4 = textureRegion.getV();
                fArr[0] = f2;
                fArr[1] = f3;
                fArr[2] = floatBits;
                fArr[3] = u3;
                fArr[4] = v3;
                fArr[5] = f2;
                fArr[6] = f4;
                fArr[7] = floatBits;
                fArr[8] = u3;
                fArr[9] = v4;
                fArr[10] = f;
                fArr[11] = f4;
                fArr[12] = floatBits;
                fArr[13] = u4;
                fArr[14] = v4;
                fArr[15] = f;
                fArr[16] = f3;
                fArr[17] = floatBits;
                fArr[18] = u4;
                fArr[19] = v3;
                this.spriteCache.add(textureRegion.getTexture(), fArr, 0, 20);
                i3++;
                i = 0;
                regionWidth = regionWidth;
                parallaxX = parallaxX;
            }
        }
    }

    public void invalidateCache() {
        this.cached = false;
    }

    public boolean isCached() {
        return this.cached;
    }

    public void setOverCache(float f) {
        this.overCache = f;
    }

    public void setMaxTileSize(float f, float f2) {
        this.maxTileWidth = f;
        this.maxTileHeight = f2;
    }

    public void setBlending(boolean z) {
        this.blending = z;
    }

    public SpriteCache getSpriteCache() {
        return this.spriteCache;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.spriteCache.dispose();
    }
}
