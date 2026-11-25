package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/maps/tiled/renderers/HexagonalTiledMapRenderer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class HexagonalTiledMapRenderer extends BatchTiledMapRenderer {
    private float hexSideLength;
    private boolean staggerAxisX;
    private boolean staggerIndexEven;

    public HexagonalTiledMapRenderer(TiledMap tiledMap) {
        super(tiledMap);
        this.staggerAxisX = true;
        this.staggerIndexEven = false;
        this.hexSideLength = 0.0f;
        init(tiledMap);
    }

    public HexagonalTiledMapRenderer(TiledMap tiledMap, float f) {
        super(tiledMap, f);
        this.staggerAxisX = true;
        this.staggerIndexEven = false;
        this.hexSideLength = 0.0f;
        init(tiledMap);
    }

    public HexagonalTiledMapRenderer(TiledMap tiledMap, Batch batch) {
        super(tiledMap, batch);
        this.staggerAxisX = true;
        this.staggerIndexEven = false;
        this.hexSideLength = 0.0f;
        init(tiledMap);
    }

    public HexagonalTiledMapRenderer(TiledMap tiledMap, float f, Batch batch) {
        super(tiledMap, f, batch);
        this.staggerAxisX = true;
        this.staggerIndexEven = false;
        this.hexSideLength = 0.0f;
        init(tiledMap);
    }

    private void init(TiledMap tiledMap) {
        String str = (String) tiledMap.getProperties().get("staggeraxis", String.class);
        if (str != null) {
            if (str.equals("x")) {
                this.staggerAxisX = true;
            } else {
                this.staggerAxisX = false;
            }
        }
        String str2 = (String) tiledMap.getProperties().get("staggerindex", String.class);
        if (str2 != null) {
            if (str2.equals("even")) {
                this.staggerIndexEven = true;
            } else {
                this.staggerIndexEven = false;
            }
        }
        if (!this.staggerAxisX && ((Integer) tiledMap.getProperties().get("height", Integer.class)).intValue() % 2 == 0) {
            this.staggerIndexEven = !this.staggerIndexEven;
        }
        Integer num = (Integer) tiledMap.getProperties().get("hexsidelength", Integer.class);
        if (num != null) {
            this.hexSideLength = num.intValue();
            return;
        }
        if (this.staggerAxisX) {
            Integer num2 = (Integer) tiledMap.getProperties().get("tilewidth", Integer.class);
            if (num2 != null) {
                this.hexSideLength = num2.intValue() * 0.5f;
                return;
            } else {
                this.hexSideLength = ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getTileWidth() * 0.5f;
                return;
            }
        }
        Integer num3 = (Integer) tiledMap.getProperties().get("tileheight", Integer.class);
        if (num3 != null) {
            this.hexSideLength = num3.intValue() * 0.5f;
        } else {
            this.hexSideLength = ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getTileHeight() * 0.5f;
        }
    }

    @Override // com.badlogic.gdx.maps.tiled.TiledMapRenderer
    public void renderTileLayer(TiledMapTileLayer tiledMapTileLayer) {
        TiledMapTileLayer tiledMapTileLayer2 = tiledMapTileLayer;
        float tileLayerColor = getTileLayerColor(tiledMapTileLayer2, this.batch.getColor());
        int width = tiledMapTileLayer.getWidth();
        int height = tiledMapTileLayer.getHeight();
        float tileWidth = tiledMapTileLayer.getTileWidth() * this.unitScale;
        float tileHeight = tiledMapTileLayer.getTileHeight() * this.unitScale;
        float renderOffsetX = (tiledMapTileLayer.getRenderOffsetX() * this.unitScale) - (this.viewBounds.x * (tiledMapTileLayer.getParallaxX() - 1.0f));
        float parallaxY = ((-tiledMapTileLayer.getRenderOffsetY()) * this.unitScale) - (this.viewBounds.y * (tiledMapTileLayer.getParallaxY() - 1.0f));
        float f = this.hexSideLength * this.unitScale;
        boolean z = true;
        if (this.staggerAxisX) {
            float f2 = (tileWidth - f) / 2.0f;
            float f3 = (tileWidth + f) / 2.0f;
            float f4 = 0.5f * tileHeight;
            int iMax = Math.max(0, (int) (((this.viewBounds.y - f4) - renderOffsetX) / tileHeight));
            int iMin = Math.min(height, (int) ((((this.viewBounds.y + this.viewBounds.height) + tileHeight) - renderOffsetX) / tileHeight));
            int iMax2 = Math.max(0, (int) (((this.viewBounds.x - f2) - parallaxY) / f3));
            int iMin2 = Math.min(width, (int) ((((this.viewBounds.x + this.viewBounds.width) + f3) - parallaxY) / f3));
            boolean z2 = this.staggerIndexEven;
            int i = iMax2 % 2;
            int i2 = z2 == (i == 0) ? iMax2 + 1 : iMax2;
            if (z2 != (i == 0)) {
                iMax2++;
            }
            int i3 = iMin - 1;
            while (i3 >= iMax) {
                int i4 = i2;
                while (i4 < iMin2) {
                    renderCell(tiledMapTileLayer2.getCell(i4, i3), (i4 * f3) + renderOffsetX, (i3 * tileHeight) + f4 + parallaxY, tileLayerColor);
                    i4 += 2;
                    iMax = iMax;
                }
                int i5 = iMax;
                for (int i6 = iMax2; i6 < iMin2; i6 += 2) {
                    renderCell(tiledMapTileLayer2.getCell(i6, i3), (i6 * f3) + renderOffsetX, (i3 * tileHeight) + parallaxY, tileLayerColor);
                }
                i3--;
                iMax = i5;
            }
            return;
        }
        float f5 = (tileHeight - f) / 2.0f;
        float f6 = (tileHeight + f) / 2.0f;
        float f7 = 0.5f * tileWidth;
        int iMax3 = Math.max(0, (int) (((this.viewBounds.y - f5) - renderOffsetX) / f6));
        int iMin3 = Math.min(height, (int) ((((this.viewBounds.y + this.viewBounds.height) + f6) - renderOffsetX) / f6));
        boolean z3 = false;
        int iMax4 = Math.max(0, (int) (((this.viewBounds.x - f7) - parallaxY) / tileWidth));
        int iMin4 = Math.min(width, (int) ((((this.viewBounds.x + this.viewBounds.width) + tileWidth) - parallaxY) / tileWidth));
        int i7 = iMin3 - 1;
        while (i7 >= iMax3) {
            float f8 = (i7 % 2 == 0 ? z : z3) == this.staggerIndexEven ? f7 : 0.0f;
            int i8 = iMax4;
            while (i8 < iMin4) {
                renderCell(tiledMapTileLayer2.getCell(i8, i7), (i8 * tileWidth) + f8 + renderOffsetX, (i7 * f6) + parallaxY, tileLayerColor);
                i8++;
                tiledMapTileLayer2 = tiledMapTileLayer;
            }
            i7--;
            tiledMapTileLayer2 = tiledMapTileLayer;
            z3 = false;
            z = true;
        }
    }

    private void renderCell(TiledMapTileLayer.Cell cell, float f, float f2, float f3) {
        TiledMapTile tile;
        if (cell == null || (tile = cell.getTile()) == null || (tile instanceof AnimatedTiledMapTile)) {
            return;
        }
        boolean flipHorizontally = cell.getFlipHorizontally();
        boolean flipVertically = cell.getFlipVertically();
        int rotation = cell.getRotation();
        TextureRegion textureRegion = tile.getTextureRegion();
        float offsetX = f + (tile.getOffsetX() * this.unitScale);
        float offsetY = f2 + (tile.getOffsetY() * this.unitScale);
        float regionWidth = (textureRegion.getRegionWidth() * this.unitScale) + offsetX;
        float regionHeight = (textureRegion.getRegionHeight() * this.unitScale) + offsetY;
        float u = textureRegion.getU();
        float v2 = textureRegion.getV2();
        float u2 = textureRegion.getU2();
        float v = textureRegion.getV();
        this.vertices[0] = offsetX;
        this.vertices[1] = offsetY;
        this.vertices[2] = f3;
        this.vertices[3] = u;
        this.vertices[4] = v2;
        this.vertices[5] = offsetX;
        this.vertices[6] = regionHeight;
        this.vertices[7] = f3;
        this.vertices[8] = u;
        this.vertices[9] = v;
        this.vertices[10] = regionWidth;
        this.vertices[11] = regionHeight;
        this.vertices[12] = f3;
        this.vertices[13] = u2;
        this.vertices[14] = v;
        this.vertices[15] = regionWidth;
        this.vertices[16] = offsetY;
        this.vertices[17] = f3;
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
        if (rotation == 2) {
            float f8 = this.vertices[3];
            this.vertices[3] = this.vertices[13];
            this.vertices[13] = f8;
            float f9 = this.vertices[8];
            this.vertices[8] = this.vertices[18];
            this.vertices[18] = f9;
            float f10 = this.vertices[4];
            this.vertices[4] = this.vertices[14];
            this.vertices[14] = f10;
            float f11 = this.vertices[9];
            this.vertices[9] = this.vertices[19];
            this.vertices[19] = f11;
        }
        this.batch.draw(textureRegion.getTexture(), this.vertices, 0, 20);
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
        int iIntValue = ((Integer) getMap().getProperties().get("tileheight", Integer.class)).intValue();
        int iIntValue2 = ((Integer) getMap().getProperties().get("height", Integer.class)).intValue();
        float f6 = this.hexSideLength;
        float f7 = iIntValue2 * iIntValue;
        float f8 = this.unitScale * f7;
        float f9 = ((f7 * 0.75f) + (f6 * 0.5f)) * this.unitScale;
        float f10 = iIntValue * this.unitScale * 0.5f;
        if (!this.staggerAxisX) {
            f10 = -(f8 - f9);
        }
        float x = tiledMapImageLayer.getX();
        float y = tiledMapImageLayer.getY();
        float parallaxX = (x * this.unitScale) - (this.viewBounds.x * (tiledMapImageLayer.getParallaxX() - 1.0f));
        float parallaxY = ((y * this.unitScale) - (this.viewBounds.y * (tiledMapImageLayer.getParallaxY() - 1.0f))) + f10;
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
        float f11 = this.viewBounds.x;
        float f12 = this.viewBounds.y;
        float f13 = f11 - (f11 % this.imageBounds.width);
        float f14 = f12 - (f12 % this.imageBounds.height);
        int i3 = 0;
        while (i3 <= iCeil) {
            int i4 = i2;
            while (i4 <= iCeil2) {
                if (tiledMapImageLayer.isRepeatX()) {
                    f2 = ((i3 - 2) * this.imageBounds.width) + f13 + (parallaxX % this.imageBounds.width);
                    f = this.imageBounds.width + f2;
                } else {
                    f = regionWidth;
                    f2 = parallaxX;
                }
                if (tiledMapImageLayer.isRepeatY()) {
                    f3 = ((i4 - 2) * this.imageBounds.height) + f14 + (parallaxY % this.imageBounds.height);
                    f5 = this.imageBounds.height + f3;
                    f4 = parallaxY;
                } else {
                    f3 = parallaxY;
                    f4 = f3;
                    f5 = regionHeight;
                }
                float f15 = regionHeight;
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
                regionHeight = f15;
                iCeil = i5;
            }
            i3++;
            i2 = i2;
            regionWidth = regionWidth;
            parallaxX = parallaxX;
            parallaxY = parallaxY;
        }
    }
}
