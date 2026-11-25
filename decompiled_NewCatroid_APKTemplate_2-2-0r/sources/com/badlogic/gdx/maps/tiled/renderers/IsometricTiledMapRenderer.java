package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/renderers/IsometricTiledMapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IsometricTiledMapRenderer extends BatchTiledMapRenderer {
    private Vector2 bottomLeft;
    private Vector2 bottomRight;
    private Matrix4 invIsotransform;
    private Matrix4 isoTransform;
    private Vector3 screenPos;
    private Vector2 topLeft;
    private Vector2 topRight;

    public IsometricTiledMapRenderer(TiledMap tiledMap) {
        super(tiledMap);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        init();
    }

    public IsometricTiledMapRenderer(TiledMap tiledMap, Batch batch) {
        super(tiledMap, batch);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        init();
    }

    public IsometricTiledMapRenderer(TiledMap tiledMap, float f) {
        super(tiledMap, f);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        init();
    }

    public IsometricTiledMapRenderer(TiledMap tiledMap, float f, Batch batch) {
        super(tiledMap, f, batch);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        init();
    }

    private void init() {
        Matrix4 matrix4 = new Matrix4();
        this.isoTransform = matrix4;
        matrix4.idt();
        this.isoTransform.scale((float) (Math.sqrt(2.0d) / 2.0d), (float) (Math.sqrt(2.0d) / 4.0d), 1.0f);
        this.isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);
        Matrix4 matrix5 = new Matrix4(this.isoTransform);
        this.invIsotransform = matrix5;
        matrix5.inv();
    }

    private Vector3 translateScreenToIso(Vector2 vector2) {
        this.screenPos.set(vector2.x, vector2.y, 0.0f);
        this.screenPos.mul(this.invIsotransform);
        return this.screenPos;
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderTileLayer(TiledMapTileLayer tiledMapTileLayer) {
        char c;
        TiledMapTile tile;
        TiledMapTileLayer tiledMapTileLayer2 = tiledMapTileLayer;
        float tileLayerColor = getTileLayerColor(tiledMapTileLayer2, this.batch.getColor());
        float tileWidth = tiledMapTileLayer.getTileWidth() * this.unitScale;
        float tileHeight = tiledMapTileLayer.getTileHeight() * this.unitScale;
        float renderOffsetX = (tiledMapTileLayer.getRenderOffsetX() * this.unitScale) - (this.viewBounds.x * (tiledMapTileLayer.getParallaxX() - 1.0f));
        float parallaxY = ((-tiledMapTileLayer.getRenderOffsetY()) * this.unitScale) - (this.viewBounds.y * (tiledMapTileLayer.getParallaxY() - 1.0f));
        float f = tileWidth * 0.5f;
        float f2 = tileHeight * 0.5f;
        this.topRight.set((this.viewBounds.x + this.viewBounds.width) - renderOffsetX, this.viewBounds.y - parallaxY);
        this.bottomLeft.set(this.viewBounds.x - renderOffsetX, (this.viewBounds.y + this.viewBounds.height) - parallaxY);
        this.topLeft.set(this.viewBounds.x - renderOffsetX, this.viewBounds.y - parallaxY);
        this.bottomRight.set((this.viewBounds.x + this.viewBounds.width) - renderOffsetX, (this.viewBounds.y + this.viewBounds.height) - parallaxY);
        char c2 = 2;
        int i = ((int) (translateScreenToIso(this.topLeft).y / tileWidth)) - 2;
        int i2 = ((int) (translateScreenToIso(this.bottomRight).y / tileWidth)) + 2;
        int i3 = ((int) (translateScreenToIso(this.bottomLeft).x / tileWidth)) - 2;
        int i4 = ((int) (translateScreenToIso(this.topRight).x / tileWidth)) + 2;
        while (i2 >= i) {
            int i5 = i3;
            while (i5 <= i4) {
                float f3 = i5;
                float f4 = i2;
                float f5 = (f3 * f) + (f4 * f);
                float f6 = (f4 * f2) - (f3 * f2);
                TiledMapTileLayer.Cell cell = tiledMapTileLayer2.getCell(i5, i2);
                if (cell == null || (tile = cell.getTile()) == null) {
                    c = c2;
                } else {
                    boolean flipHorizontally = cell.getFlipHorizontally();
                    boolean flipVertically = cell.getFlipVertically();
                    int rotation = cell.getRotation();
                    TextureRegion textureRegion = tile.getTextureRegion();
                    float offsetX = f5 + (tile.getOffsetX() * this.unitScale) + renderOffsetX;
                    float offsetY = f6 + (tile.getOffsetY() * this.unitScale) + parallaxY;
                    float regionWidth = (textureRegion.getRegionWidth() * this.unitScale) + offsetX;
                    float regionHeight = (textureRegion.getRegionHeight() * this.unitScale) + offsetY;
                    float u = textureRegion.getU();
                    float v2 = textureRegion.getV2();
                    float u2 = textureRegion.getU2();
                    float v = textureRegion.getV();
                    this.vertices[0] = offsetX;
                    this.vertices[1] = offsetY;
                    this.vertices[2] = tileLayerColor;
                    this.vertices[3] = u;
                    this.vertices[4] = v2;
                    this.vertices[5] = offsetX;
                    this.vertices[6] = regionHeight;
                    this.vertices[7] = tileLayerColor;
                    this.vertices[8] = u;
                    this.vertices[9] = v;
                    this.vertices[10] = regionWidth;
                    this.vertices[11] = regionHeight;
                    this.vertices[12] = tileLayerColor;
                    this.vertices[13] = u2;
                    this.vertices[14] = v;
                    this.vertices[15] = regionWidth;
                    this.vertices[16] = offsetY;
                    this.vertices[17] = tileLayerColor;
                    this.vertices[18] = u2;
                    this.vertices[19] = v2;
                    if (flipHorizontally) {
                        float f7 = this.vertices[3];
                        this.vertices[3] = this.vertices[13];
                        this.vertices[13] = f7;
                        float f8 = this.vertices[8];
                        this.vertices[8] = this.vertices[18];
                        this.vertices[18] = f8;
                    }
                    if (flipVertically) {
                        float f9 = this.vertices[4];
                        this.vertices[4] = this.vertices[14];
                        this.vertices[14] = f9;
                        float f10 = this.vertices[9];
                        this.vertices[9] = this.vertices[19];
                        this.vertices[19] = f10;
                    }
                    if (rotation == 0) {
                        c = 2;
                    } else if (rotation != 1) {
                        c = 2;
                        if (rotation == 2) {
                            float f11 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = f11;
                            float f12 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = f12;
                            float f13 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = f13;
                            float f14 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = f14;
                        } else if (rotation == 3) {
                            float f15 = this.vertices[4];
                            this.vertices[4] = this.vertices[19];
                            this.vertices[19] = this.vertices[14];
                            this.vertices[14] = this.vertices[9];
                            this.vertices[9] = f15;
                            float f16 = this.vertices[3];
                            this.vertices[3] = this.vertices[18];
                            this.vertices[18] = this.vertices[13];
                            this.vertices[13] = this.vertices[8];
                            this.vertices[8] = f16;
                        }
                    } else {
                        c = 2;
                        float f17 = this.vertices[4];
                        this.vertices[4] = this.vertices[9];
                        this.vertices[9] = this.vertices[14];
                        this.vertices[14] = this.vertices[19];
                        this.vertices[19] = f17;
                        float f18 = this.vertices[3];
                        this.vertices[3] = this.vertices[8];
                        this.vertices[8] = this.vertices[13];
                        this.vertices[13] = this.vertices[18];
                        this.vertices[18] = f18;
                    }
                    this.batch.draw(textureRegion.getTexture(), this.vertices, 0, 20);
                }
                i5++;
                c2 = c;
                i4 = i4;
                f2 = f2;
                renderOffsetX = renderOffsetX;
                tiledMapTileLayer2 = tiledMapTileLayer;
            }
            i2--;
            tiledMapTileLayer2 = tiledMapTileLayer;
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer, com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderImageLayer(TiledMapImageLayer tiledMapImageLayer) {
        float f;
        float f2;
        float f3;
        int i;
        float imageLayerColor = getImageLayerColor(tiledMapImageLayer, this.batch.getColor());
        float[] fArr = this.vertices;
        TextureRegion textureRegion = tiledMapImageLayer.getTextureRegion();
        if (textureRegion == null) {
            return;
        }
        int iIntValue = ((Integer) getMap().getProperties().get("tileheight", Integer.class)).intValue();
        float fIntValue = ((Integer) getMap().getProperties().get("height", Integer.class)).intValue() * iIntValue * this.unitScale;
        float f4 = iIntValue * 0.5f * this.unitScale;
        float x = tiledMapImageLayer.getX();
        float y = tiledMapImageLayer.getY();
        float parallaxX = (x * this.unitScale) - (this.viewBounds.x * (tiledMapImageLayer.getParallaxX() - 1.0f));
        float parallaxY = (((y * this.unitScale) - (this.viewBounds.y * (tiledMapImageLayer.getParallaxY() - 1.0f))) - (fIntValue * 0.5f)) + f4;
        float regionWidth = (textureRegion.getRegionWidth() * this.unitScale) + parallaxX;
        float regionHeight = (textureRegion.getRegionHeight() * this.unitScale) + parallaxY;
        this.imageBounds.set(parallaxX, parallaxY, regionWidth - parallaxX, regionHeight - parallaxY);
        int i2 = 0;
        if (!tiledMapImageLayer.isRepeatX() && !tiledMapImageLayer.isRepeatY()) {
            if (this.viewBounds.contains(this.imageBounds) || this.viewBounds.overlaps(this.imageBounds)) {
                float u = textureRegion.getU();
                float v2 = textureRegion.getV2();
                float u2 = textureRegion.getU2();
                float v = textureRegion.getV();
                fArr[0] = parallaxX;
                fArr[1] = parallaxY;
                fArr[2] = imageLayerColor;
                fArr[3] = u;
                fArr[4] = v2;
                fArr[5] = parallaxX;
                fArr[6] = regionHeight;
                fArr[7] = imageLayerColor;
                fArr[8] = u;
                fArr[9] = v;
                fArr[10] = regionWidth;
                fArr[11] = regionHeight;
                fArr[12] = imageLayerColor;
                fArr[13] = u2;
                fArr[14] = v;
                fArr[15] = regionWidth;
                fArr[16] = parallaxY;
                fArr[17] = imageLayerColor;
                fArr[18] = u2;
                fArr[19] = v2;
                this.batch.draw(textureRegion.getTexture(), fArr, 0, 20);
                return;
            }
            return;
        }
        int iCeil = tiledMapImageLayer.isRepeatX() ? (int) Math.ceil((this.viewBounds.width / this.imageBounds.width) + 4.0f) : 0;
        int iCeil2 = tiledMapImageLayer.isRepeatY() ? (int) Math.ceil((this.viewBounds.height / this.imageBounds.height) + 4.0f) : 0;
        float f5 = this.viewBounds.x;
        float f6 = this.viewBounds.y;
        float f7 = f5 - (f5 % this.imageBounds.width);
        float f8 = f6 - (f6 % this.imageBounds.height);
        int i3 = 0;
        while (i3 <= iCeil) {
            int i4 = i2;
            while (i4 <= iCeil2) {
                if (tiledMapImageLayer.isRepeatX()) {
                    f2 = ((i3 - 2) * this.imageBounds.width) + f7 + (parallaxX % this.imageBounds.width);
                    f = this.imageBounds.width + f2;
                } else {
                    f = regionWidth;
                    f2 = parallaxX;
                }
                if (tiledMapImageLayer.isRepeatY()) {
                    f3 = ((i4 - 2) * this.imageBounds.height) + f8 + (parallaxY % this.imageBounds.height);
                    regionHeight = this.imageBounds.height + f3;
                } else {
                    f3 = parallaxY;
                }
                float f9 = parallaxX;
                float f10 = parallaxY;
                this.repeatedImageBounds.set(f2, f3, f - f2, regionHeight - f3);
                if (this.viewBounds.contains(this.repeatedImageBounds) || this.viewBounds.overlaps(this.repeatedImageBounds)) {
                    float u3 = textureRegion.getU();
                    float v3 = textureRegion.getV2();
                    float u4 = textureRegion.getU2();
                    float v4 = textureRegion.getV();
                    fArr[0] = f2;
                    fArr[1] = f3;
                    fArr[2] = imageLayerColor;
                    fArr[3] = u3;
                    fArr[4] = v3;
                    fArr[5] = f2;
                    fArr[6] = regionHeight;
                    fArr[7] = imageLayerColor;
                    fArr[8] = u3;
                    fArr[9] = v4;
                    fArr[10] = f;
                    fArr[11] = regionHeight;
                    fArr[12] = imageLayerColor;
                    fArr[13] = u4;
                    fArr[14] = v4;
                    fArr[15] = f;
                    fArr[16] = f3;
                    fArr[17] = imageLayerColor;
                    fArr[18] = u4;
                    fArr[19] = v3;
                    i = 0;
                    this.batch.draw(textureRegion.getTexture(), fArr, 0, 20);
                } else {
                    i = 0;
                }
                i4++;
                i2 = i;
                regionWidth = regionWidth;
                regionHeight = regionHeight;
                iCeil = iCeil;
                parallaxX = f9;
                parallaxY = f10;
            }
            i3++;
            i2 = i2;
            regionWidth = regionWidth;
            regionHeight = regionHeight;
            iCeil = iCeil;
        }
    }
}
