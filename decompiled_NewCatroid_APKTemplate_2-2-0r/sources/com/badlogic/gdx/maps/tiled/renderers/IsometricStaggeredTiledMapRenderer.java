package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/renderers/IsometricStaggeredTiledMapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IsometricStaggeredTiledMapRenderer extends BatchTiledMapRenderer {
    public IsometricStaggeredTiledMapRenderer(TiledMap tiledMap) {
        super(tiledMap);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap tiledMap, Batch batch) {
        super(tiledMap, batch);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap tiledMap, float f) {
        super(tiledMap, f);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap tiledMap, float f, Batch batch) {
        super(tiledMap, f, batch);
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderTileLayer(TiledMapTileLayer tiledMapTileLayer) {
        int i;
        boolean z;
        TiledMapTile tile;
        TiledMapTileLayer tiledMapTileLayer2 = tiledMapTileLayer;
        float tileLayerColor = getTileLayerColor(tiledMapTileLayer2, this.batch.getColor());
        int width = tiledMapTileLayer.getWidth();
        int height = tiledMapTileLayer.getHeight();
        float renderOffsetX = (tiledMapTileLayer.getRenderOffsetX() * this.unitScale) - (this.viewBounds.x * (tiledMapTileLayer.getParallaxX() - 1.0f));
        float parallaxY = ((-tiledMapTileLayer.getRenderOffsetY()) * this.unitScale) - (this.viewBounds.y * (tiledMapTileLayer.getParallaxY() - 1.0f));
        float tileWidth = tiledMapTileLayer.getTileWidth() * this.unitScale;
        float tileHeight = tiledMapTileLayer.getTileHeight() * this.unitScale;
        float f = tileWidth * 0.5f;
        float f2 = 0.5f * tileHeight;
        boolean z2 = false;
        int iMax = Math.max(0, (int) (((this.viewBounds.x - f) - renderOffsetX) / tileWidth));
        int iMin = Math.min(width, (int) (((((this.viewBounds.x + this.viewBounds.width) + tileWidth) + f) - renderOffsetX) / tileWidth));
        int iMax2 = Math.max(0, (int) (((this.viewBounds.y - tileHeight) - parallaxY) / tileHeight));
        int iMin2 = Math.min(height, (int) ((((this.viewBounds.y + this.viewBounds.height) + tileHeight) - parallaxY) / f2));
        int i2 = 1;
        int i3 = iMin2 - 1;
        while (i3 >= iMax2) {
            float f3 = i3 % 2 == i2 ? f : 0.0f;
            int i4 = iMin - 1;
            while (i4 >= iMax) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer2.getCell(i4, i3);
                if (cell == null || (tile = cell.getTile()) == null) {
                    i = i2;
                    z = z2;
                } else {
                    boolean flipHorizontally = cell.getFlipHorizontally();
                    boolean flipVertically = cell.getFlipVertically();
                    int rotation = cell.getRotation();
                    TextureRegion textureRegion = tile.getTextureRegion();
                    float offsetX = ((i4 * tileWidth) - f3) + (tile.getOffsetX() * this.unitScale) + renderOffsetX;
                    float offsetY = (i3 * f2) + (tile.getOffsetY() * this.unitScale) + parallaxY;
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
                        float f4 = this.vertices[3];
                        this.vertices[3] = this.vertices[13];
                        this.vertices[13] = f4;
                        float f5 = this.vertices[8];
                        this.vertices[8] = this.vertices[18];
                        this.vertices[18] = f5;
                    }
                    if (flipVertically) {
                        float f6 = this.vertices[4];
                        this.vertices[4] = this.vertices[14];
                        this.vertices[14] = f6;
                        float f7 = this.vertices[9];
                        this.vertices[9] = this.vertices[19];
                        this.vertices[19] = f7;
                    }
                    i = 1;
                    if (rotation != 0) {
                        if (rotation == 1) {
                            float f8 = this.vertices[4];
                            this.vertices[4] = this.vertices[9];
                            this.vertices[9] = this.vertices[14];
                            this.vertices[14] = this.vertices[19];
                            this.vertices[19] = f8;
                            float f9 = this.vertices[3];
                            this.vertices[3] = this.vertices[8];
                            this.vertices[8] = this.vertices[13];
                            this.vertices[13] = this.vertices[18];
                            this.vertices[18] = f9;
                        } else if (rotation == 2) {
                            float f10 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = f10;
                            float f11 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = f11;
                            float f12 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = f12;
                            float f13 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = f13;
                        } else if (rotation == 3) {
                            float f14 = this.vertices[4];
                            this.vertices[4] = this.vertices[19];
                            this.vertices[19] = this.vertices[14];
                            this.vertices[14] = this.vertices[9];
                            this.vertices[9] = f14;
                            float f15 = this.vertices[3];
                            this.vertices[3] = this.vertices[18];
                            this.vertices[18] = this.vertices[13];
                            this.vertices[13] = this.vertices[8];
                            this.vertices[8] = f15;
                        }
                    }
                    z = false;
                    this.batch.draw(textureRegion.getTexture(), this.vertices, 0, 20);
                }
                i4--;
                z2 = z;
                renderOffsetX = renderOffsetX;
                iMin = iMin;
                parallaxY = parallaxY;
                tileWidth = tileWidth;
                f2 = f2;
                i2 = i;
                tiledMapTileLayer2 = tiledMapTileLayer;
            }
            i3--;
            i2 = i2;
            tiledMapTileLayer2 = tiledMapTileLayer;
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer, com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderImageLayer(TiledMapImageLayer tiledMapImageLayer) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        int i;
        float imageLayerColor = getImageLayerColor(tiledMapImageLayer, this.batch.getColor());
        float[] fArr = this.vertices;
        TextureRegion textureRegion = tiledMapImageLayer.getTextureRegion();
        if (textureRegion == null) {
            return;
        }
        float fIntValue = ((Integer) getMap().getProperties().get("tilewidth", Integer.class)).intValue() * 0.5f * this.unitScale;
        float x = tiledMapImageLayer.getX();
        float y = tiledMapImageLayer.getY();
        float parallaxX = ((x * this.unitScale) - (this.viewBounds.x * (tiledMapImageLayer.getParallaxX() - 1.0f))) - fIntValue;
        float parallaxY = (y * this.unitScale) - (this.viewBounds.y * (tiledMapImageLayer.getParallaxY() - 1.0f));
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
        float f6 = this.viewBounds.x;
        float f7 = this.viewBounds.y;
        float f8 = f6 - (f6 % this.imageBounds.width);
        float f9 = f7 - (f7 % this.imageBounds.height);
        int i3 = 0;
        while (i3 <= iCeil) {
            int i4 = i2;
            while (i4 <= iCeil2) {
                if (tiledMapImageLayer.isRepeatX()) {
                    f2 = ((i3 - 2) * this.imageBounds.width) + f8 + (parallaxX % this.imageBounds.width);
                    f = this.imageBounds.width + f2;
                } else {
                    f = regionWidth;
                    f2 = parallaxX;
                }
                if (tiledMapImageLayer.isRepeatY()) {
                    f3 = ((i4 - 2) * this.imageBounds.height) + f9 + (parallaxY % this.imageBounds.height);
                    f5 = this.imageBounds.height + f3;
                    f4 = parallaxY;
                } else {
                    f3 = parallaxY;
                    f4 = f3;
                    f5 = regionHeight;
                }
                float f10 = regionHeight;
                int i5 = iCeil;
                this.repeatedImageBounds.set(f2, f3, f - f2, f5 - f3);
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
                    fArr[6] = f5;
                    fArr[7] = imageLayerColor;
                    fArr[8] = u3;
                    fArr[9] = v4;
                    fArr[10] = f;
                    fArr[11] = f5;
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
                parallaxX = parallaxX;
                parallaxY = f4;
                regionHeight = f10;
                iCeil = i5;
            }
            i3++;
            i2 = i2;
            parallaxX = parallaxX;
            parallaxY = parallaxY;
            iCeil = iCeil;
        }
    }
}
