package com.badlogic.gdx.scenes.scene2d.ui;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Skin.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Skin implements Disposable {
    private static final Class[] defaultTagClasses = {BitmapFont.class, Color.class, TintedDrawable.class, NinePatchDrawable.class, SpriteDrawable.class, TextureRegionDrawable.class, TiledDrawable.class, Button.ButtonStyle.class, CheckBox.CheckBoxStyle.class, ImageButton.ImageButtonStyle.class, ImageTextButton.ImageTextButtonStyle.class, Label.LabelStyle.class, List.ListStyle.class, ProgressBar.ProgressBarStyle.class, ScrollPane.ScrollPaneStyle.class, SelectBox.SelectBoxStyle.class, Slider.SliderStyle.class, SplitPane.SplitPaneStyle.class, TextButton.TextButtonStyle.class, TextField.TextFieldStyle.class, TextTooltip.TextTooltipStyle.class, Touchpad.TouchpadStyle.class, Tree.TreeStyle.class, Window.WindowStyle.class};
    TextureAtlas atlas;
    private final ObjectMap<String, Class> jsonClassTags;
    ObjectMap<Class, ObjectMap<String, Object>> resources = new ObjectMap<>();
    float scale = 1.0f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/Skin$TintedDrawable.class
      classes8.dex
     */
    public static class TintedDrawable {
        public Color color;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f123name;
    }

    public Skin() {
        Class[] clsArr = defaultTagClasses;
        this.jsonClassTags = new ObjectMap<>(clsArr.length);
        for (Class cls : clsArr) {
            this.jsonClassTags.put(cls.getSimpleName(), cls);
        }
    }

    public Skin(FileHandle fileHandle) {
        Class[] clsArr = defaultTagClasses;
        this.jsonClassTags = new ObjectMap<>(clsArr.length);
        for (Class cls : clsArr) {
            this.jsonClassTags.put(cls.getSimpleName(), cls);
        }
        FileHandle fileHandleSibling = fileHandle.sibling(fileHandle.nameWithoutExtension() + ".atlas");
        if (fileHandleSibling.exists()) {
            TextureAtlas textureAtlas = new TextureAtlas(fileHandleSibling);
            this.atlas = textureAtlas;
            addRegions(textureAtlas);
        }
        load(fileHandle);
    }

    public Skin(FileHandle fileHandle, TextureAtlas textureAtlas) {
        Class[] clsArr = defaultTagClasses;
        this.jsonClassTags = new ObjectMap<>(clsArr.length);
        for (Class cls : clsArr) {
            this.jsonClassTags.put(cls.getSimpleName(), cls);
        }
        this.atlas = textureAtlas;
        addRegions(textureAtlas);
        load(fileHandle);
    }

    public Skin(TextureAtlas textureAtlas) {
        Class[] clsArr = defaultTagClasses;
        this.jsonClassTags = new ObjectMap<>(clsArr.length);
        for (Class cls : clsArr) {
            this.jsonClassTags.put(cls.getSimpleName(), cls);
        }
        this.atlas = textureAtlas;
        addRegions(textureAtlas);
    }

    public void load(FileHandle fileHandle) {
        try {
            getJsonLoader(fileHandle).fromJson(Skin.class, fileHandle);
        } catch (SerializationException e) {
            throw new SerializationException("Error reading file: " + fileHandle, e);
        }
    }

    public void addRegions(TextureAtlas textureAtlas) {
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
        int i = regions.size;
        for (int i2 = 0; i2 < i; i2++) {
            TextureAtlas.AtlasRegion atlasRegion = regions.get(i2);
            String str = atlasRegion.f97name;
            if (atlasRegion.index != -1) {
                str = str + "_" + atlasRegion.index;
            }
            add(str, atlasRegion, TextureRegion.class);
        }
    }

    public void add(String str, Object obj) {
        add(str, obj, obj.getClass());
    }

    public void add(String str, Object obj, Class cls) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (obj == null) {
            throw new IllegalArgumentException("resource cannot be null.");
        }
        ObjectMap<String, Object> objectMap = this.resources.get(cls);
        if (objectMap == null) {
            objectMap = new ObjectMap<>((cls == TextureRegion.class || cls == Drawable.class || cls == Sprite.class) ? 256 : 64);
            this.resources.put(cls, objectMap);
        }
        objectMap.put(str, obj);
    }

    public void remove(String str, Class cls) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        this.resources.get(cls).remove(str);
    }

    public <T> T get(Class<T> cls) {
        return (T) get("default", cls);
    }

    public <T> T get(String str, Class<T> cls) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        if (cls == Drawable.class) {
            return (T) getDrawable(str);
        }
        if (cls == TextureRegion.class) {
            return (T) getRegion(str);
        }
        if (cls == NinePatch.class) {
            return (T) getPatch(str);
        }
        if (cls == Sprite.class) {
            return (T) getSprite(str);
        }
        ObjectMap<String, Object> objectMap = this.resources.get(cls);
        if (objectMap == null) {
            throw new GdxRuntimeException("No " + cls.getName() + " registered with name: " + str);
        }
        T t = (T) objectMap.get(str);
        if (t != null) {
            return t;
        }
        throw new GdxRuntimeException("No " + cls.getName() + " registered with name: " + str);
    }

    public <T> T optional(String str, Class<T> cls) {
        if (str == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }
        ObjectMap<String, Object> objectMap = this.resources.get(cls);
        if (objectMap == null) {
            return null;
        }
        return (T) objectMap.get(str);
    }

    public boolean has(String str, Class cls) {
        ObjectMap<String, Object> objectMap = this.resources.get(cls);
        if (objectMap == null) {
            return false;
        }
        return objectMap.containsKey(str);
    }

    public <T> ObjectMap<String, T> getAll(Class<T> cls) {
        return (ObjectMap) this.resources.get(cls);
    }

    public Color getColor(String str) {
        return (Color) get(str, Color.class);
    }

    public BitmapFont getFont(String str) {
        return (BitmapFont) get(str, BitmapFont.class);
    }

    public TextureRegion getRegion(String str) {
        TextureRegion textureRegion = (TextureRegion) optional(str, TextureRegion.class);
        if (textureRegion != null) {
            return textureRegion;
        }
        Texture texture = (Texture) optional(str, Texture.class);
        if (texture == null) {
            throw new GdxRuntimeException("No TextureRegion or Texture registered with name: " + str);
        }
        TextureRegion textureRegion2 = new TextureRegion(texture);
        add(str, textureRegion2, TextureRegion.class);
        return textureRegion2;
    }

    public Array<TextureRegion> getRegions(String str) {
        TextureRegion textureRegion = (TextureRegion) optional(str + "_0", TextureRegion.class);
        if (textureRegion == null) {
            return null;
        }
        Array<TextureRegion> array = new Array<>();
        int i = 1;
        while (textureRegion != null) {
            array.add(textureRegion);
            textureRegion = (TextureRegion) optional(str + "_" + i, TextureRegion.class);
            i++;
        }
        return array;
    }

    public TiledDrawable getTiledDrawable(String str) {
        TiledDrawable tiledDrawable = (TiledDrawable) optional(str, TiledDrawable.class);
        if (tiledDrawable != null) {
            return tiledDrawable;
        }
        TiledDrawable tiledDrawable2 = new TiledDrawable(getRegion(str));
        tiledDrawable2.setName(str);
        if (this.scale != 1.0f) {
            scale(tiledDrawable2);
            tiledDrawable2.setScale(this.scale);
        }
        add(str, tiledDrawable2, TiledDrawable.class);
        return tiledDrawable2;
    }

    public NinePatch getPatch(String str) {
        int[] iArrFindValue;
        NinePatch ninePatch = (NinePatch) optional(str, NinePatch.class);
        if (ninePatch != null) {
            return ninePatch;
        }
        try {
            TextureRegion region = getRegion(str);
            if ((region instanceof TextureAtlas.AtlasRegion) && (iArrFindValue = ((TextureAtlas.AtlasRegion) region).findValue("split")) != null) {
                ninePatch = new NinePatch(region, iArrFindValue[0], iArrFindValue[1], iArrFindValue[2], iArrFindValue[3]);
                int[] iArrFindValue2 = ((TextureAtlas.AtlasRegion) region).findValue("pad");
                if (iArrFindValue2 != null) {
                    ninePatch.setPadding(iArrFindValue2[0], iArrFindValue2[1], iArrFindValue2[2], iArrFindValue2[3]);
                }
            }
            if (ninePatch == null) {
                ninePatch = new NinePatch(region);
            }
            float f = this.scale;
            if (f != 1.0f) {
                ninePatch.scale(f, f);
            }
            add(str, ninePatch, NinePatch.class);
            return ninePatch;
        } catch (GdxRuntimeException unused) {
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + str);
        }
    }

    public Sprite getSprite(String str) {
        Sprite sprite = (Sprite) optional(str, Sprite.class);
        if (sprite != null) {
            return sprite;
        }
        try {
            TextureRegion region = getRegion(str);
            if (region instanceof TextureAtlas.AtlasRegion) {
                TextureAtlas.AtlasRegion atlasRegion = (TextureAtlas.AtlasRegion) region;
                if (atlasRegion.rotate || atlasRegion.packedWidth != atlasRegion.originalWidth || atlasRegion.packedHeight != atlasRegion.originalHeight) {
                    sprite = new TextureAtlas.AtlasSprite(atlasRegion);
                }
            }
            if (sprite == null) {
                sprite = new Sprite(region);
            }
            if (this.scale != 1.0f) {
                sprite.setSize(sprite.getWidth() * this.scale, sprite.getHeight() * this.scale);
            }
            add(str, sprite, Sprite.class);
            return sprite;
        } catch (GdxRuntimeException unused) {
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + str);
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0058  */
    /* JADX WARN: Code duplicated, block: B:27:0x0062  */
    /* JADX WARN: Code duplicated, block: B:29:0x0069  */
    /* JADX WARN: Code duplicated, block: B:31:0x0073  */
    /* JADX WARN: Code duplicated, block: B:32:0x0079  */
    /* JADX WARN: Code duplicated, block: B:36:0x0092  */
    public Drawable getDrawable(String str) {
        NinePatch ninePatch;
        Sprite sprite;
        Drawable spriteDrawable;
        Drawable spriteDrawable2;
        Drawable drawable = (Drawable) optional(str, Drawable.class);
        if (drawable != null) {
            return drawable;
        }
        try {
            TextureRegion region = getRegion(str);
            if (region instanceof TextureAtlas.AtlasRegion) {
                TextureAtlas.AtlasRegion atlasRegion = (TextureAtlas.AtlasRegion) region;
                if (atlasRegion.findValue("split") != null) {
                    spriteDrawable2 = new NinePatchDrawable(getPatch(str));
                } else {
                    if (atlasRegion.rotate || atlasRegion.packedWidth != atlasRegion.originalWidth || atlasRegion.packedHeight != atlasRegion.originalHeight) {
                        spriteDrawable2 = new SpriteDrawable(getSprite(str));
                    }
                    if (drawable == null) {
                        ninePatch = (NinePatch) optional(str, NinePatch.class);
                        if (ninePatch != null) {
                            spriteDrawable = new NinePatchDrawable(ninePatch);
                        } else {
                            sprite = (Sprite) optional(str, Sprite.class);
                            if (sprite != null) {
                                spriteDrawable = new SpriteDrawable(sprite);
                            } else {
                                throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + str);
                            }
                        }
                        drawable = spriteDrawable;
                    }
                    if (drawable instanceof BaseDrawable) {
                        ((BaseDrawable) drawable).setName(str);
                    }
                    add(str, drawable, Drawable.class);
                    return drawable;
                }
                drawable = spriteDrawable2;
            }
            if (drawable == null) {
                Drawable textureRegionDrawable = new TextureRegionDrawable(region);
                try {
                    if (this.scale != 1.0f) {
                        scale(textureRegionDrawable);
                    }
                } catch (GdxRuntimeException unused) {
                }
                drawable = textureRegionDrawable;
            }
        } catch (GdxRuntimeException unused2) {
        }
        if (drawable == null) {
            ninePatch = (NinePatch) optional(str, NinePatch.class);
            if (ninePatch != null) {
                spriteDrawable = new NinePatchDrawable(ninePatch);
            } else {
                sprite = (Sprite) optional(str, Sprite.class);
                if (sprite != null) {
                    spriteDrawable = new SpriteDrawable(sprite);
                } else {
                    throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + str);
                }
            }
            drawable = spriteDrawable;
        }
        if (drawable instanceof BaseDrawable) {
            ((BaseDrawable) drawable).setName(str);
        }
        add(str, drawable, Drawable.class);
        return drawable;
    }

    public String find(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        ObjectMap<String, Object> objectMap = this.resources.get(obj.getClass());
        if (objectMap == null) {
            return null;
        }
        return objectMap.findKey(obj, true);
    }

    public Drawable newDrawable(String str) {
        return newDrawable(getDrawable(str));
    }

    public Drawable newDrawable(String str, float f, float f2, float f3, float f4) {
        return newDrawable(getDrawable(str), new Color(f, f2, f3, f4));
    }

    public Drawable newDrawable(String str, Color color) {
        return newDrawable(getDrawable(str), color);
    }

    public Drawable newDrawable(Drawable drawable) {
        if (drawable instanceof TiledDrawable) {
            return new TiledDrawable((TiledDrawable) drawable);
        }
        if (drawable instanceof TextureRegionDrawable) {
            return new TextureRegionDrawable((TextureRegionDrawable) drawable);
        }
        if (drawable instanceof NinePatchDrawable) {
            return new NinePatchDrawable((NinePatchDrawable) drawable);
        }
        if (drawable instanceof SpriteDrawable) {
            return new SpriteDrawable((SpriteDrawable) drawable);
        }
        throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
    }

    public Drawable newDrawable(Drawable drawable, float f, float f2, float f3, float f4) {
        return newDrawable(drawable, new Color(f, f2, f3, f4));
    }

    public Drawable newDrawable(Drawable drawable, Color color) {
        Drawable drawableTint;
        if (drawable instanceof TextureRegionDrawable) {
            drawableTint = ((TextureRegionDrawable) drawable).tint(color);
        } else if (drawable instanceof NinePatchDrawable) {
            drawableTint = ((NinePatchDrawable) drawable).tint(color);
        } else if (drawable instanceof SpriteDrawable) {
            drawableTint = ((SpriteDrawable) drawable).tint(color);
        } else {
            throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
        }
        if (drawableTint instanceof BaseDrawable) {
            BaseDrawable baseDrawable = (BaseDrawable) drawableTint;
            if (drawable instanceof BaseDrawable) {
                baseDrawable.setName(((BaseDrawable) drawable).getName() + " (" + color + Constant.AFTER_QUTO);
            } else {
                baseDrawable.setName(" (" + color + Constant.AFTER_QUTO);
            }
        }
        return drawableTint;
    }

    public void scale(Drawable drawable) {
        drawable.setLeftWidth(drawable.getLeftWidth() * this.scale);
        drawable.setRightWidth(drawable.getRightWidth() * this.scale);
        drawable.setBottomHeight(drawable.getBottomHeight() * this.scale);
        drawable.setTopHeight(drawable.getTopHeight() * this.scale);
        drawable.setMinWidth(drawable.getMinWidth() * this.scale);
        drawable.setMinHeight(drawable.getMinHeight() * this.scale);
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public void setEnabled(Actor actor, boolean z) {
        Method methodFindMethod = findMethod(actor.getClass(), "getStyle");
        if (methodFindMethod == null) {
            return;
        }
        try {
            Object objInvoke = methodFindMethod.invoke(actor, new Object[0]);
            String strFind = find(objInvoke);
            if (strFind == null) {
                return;
            }
            Object obj = get(strFind.replace("-disabled", "") + (z ? "" : "-disabled"), objInvoke.getClass());
            Method methodFindMethod2 = findMethod(actor.getClass(), "setStyle");
            if (methodFindMethod2 == null) {
                return;
            }
            methodFindMethod2.invoke(actor, obj);
        } catch (Exception unused) {
        }
    }

    public TextureAtlas getAtlas() {
        return this.atlas;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        TextureAtlas textureAtlas = this.atlas;
        if (textureAtlas != null) {
            textureAtlas.dispose();
        }
        ObjectMap.Values<ObjectMap<String, Object>> it = this.resources.values().iterator();
        while (it.hasNext()) {
            ObjectMap.Values<Object> it2 = it.next().values().iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (next instanceof Disposable) {
                    ((Disposable) next).dispose();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Json getJsonLoader(final FileHandle fileHandle) {
        Json json = new Json() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Skin.1
            private static final String parentFieldName = "parent";

            @Override // com.badlogic.gdx.utils.Json
            public <T> T readValue(Class<T> cls, Class cls2, JsonValue jsonValue) {
                if (jsonValue != null && jsonValue.isString() && !ClassReflection.isAssignableFrom(CharSequence.class, cls)) {
                    return (T) Skin.this.get(jsonValue.asString(), cls);
                }
                return (T) super.readValue(cls, cls2, jsonValue);
            }

            @Override // com.badlogic.gdx.utils.Json
            protected boolean ignoreUnknownField(Class cls, String str) {
                return str.equals(parentFieldName);
            }

            @Override // com.badlogic.gdx.utils.Json
            public void readFields(Object obj, JsonValue jsonValue) {
                if (jsonValue.has(parentFieldName)) {
                    String str = (String) readValue(parentFieldName, String.class, jsonValue);
                    Class<?> superclass = obj.getClass();
                    do {
                        try {
                            copyFields(Skin.this.get(str, superclass), obj);
                        } catch (GdxRuntimeException unused) {
                            superclass = superclass.getSuperclass();
                        }
                    } while (superclass != Object.class);
                    SerializationException serializationException = new SerializationException("Unable to find parent resource with name: " + str);
                    serializationException.addTrace(jsonValue.child.trace());
                    throw serializationException;
                }
                super.readFields(obj, jsonValue);
            }
        };
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setSerializer(Skin.class, new Json.ReadOnlySerializer<Skin>() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Skin.2
            @Override // com.badlogic.gdx.utils.Json.ReadOnlySerializer, com.badlogic.gdx.utils.Json.Serializer
            public Skin read(Json json2, JsonValue jsonValue, Class cls) {
                for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
                    try {
                        Class clsForName = json2.getClass(jsonValue2.name());
                        if (clsForName == null) {
                            clsForName = ClassReflection.forName(jsonValue2.name());
                        }
                        readNamedObjects(json2, clsForName, jsonValue2);
                    } catch (ReflectionException e) {
                        throw new SerializationException(e);
                    }
                }
                return this;
            }

            private void readNamedObjects(Json json2, Class cls, JsonValue jsonValue) {
                Class cls2 = cls == TintedDrawable.class ? Drawable.class : cls;
                for (JsonValue jsonValue2 = jsonValue.child; jsonValue2 != null; jsonValue2 = jsonValue2.next) {
                    Object value = json2.readValue(cls, jsonValue2);
                    if (value != null) {
                        try {
                            Skin.this.add(jsonValue2.f125name, value, cls2);
                            if (cls2 != Drawable.class && ClassReflection.isAssignableFrom(Drawable.class, cls2)) {
                                Skin.this.add(jsonValue2.f125name, value, Drawable.class);
                            }
                        } catch (Exception e) {
                            throw new SerializationException("Error reading " + ClassReflection.getSimpleName(cls) + ": " + jsonValue2.f125name, e);
                        }
                    }
                }
            }
        });
        json.setSerializer(BitmapFont.class, new Json.ReadOnlySerializer<BitmapFont>() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Skin.3
            @Override // com.badlogic.gdx.utils.Json.ReadOnlySerializer, com.badlogic.gdx.utils.Json.Serializer
            public BitmapFont read(Json json2, JsonValue jsonValue, Class cls) {
                BitmapFont bitmapFont;
                String str = (String) json2.readValue("file", String.class, jsonValue);
                float fFloatValue = ((Float) json2.readValue("scaledSize", (Class<Float>) Float.TYPE, Float.valueOf(-1.0f), jsonValue)).floatValue();
                Boolean bool = (Boolean) json2.readValue("flip", (Class<boolean>) Boolean.class, false, jsonValue);
                Boolean bool2 = (Boolean) json2.readValue("markupEnabled", (Class<boolean>) Boolean.class, false, jsonValue);
                Boolean bool3 = (Boolean) json2.readValue("useIntegerPositions", (Class<boolean>) Boolean.class, true, jsonValue);
                FileHandle fileHandleChild = fileHandle.parent().child(str);
                if (!fileHandleChild.exists()) {
                    fileHandleChild = Gdx.files.internal(str);
                }
                if (!fileHandleChild.exists()) {
                    throw new SerializationException("Font file not found: " + fileHandleChild);
                }
                String strNameWithoutExtension = fileHandleChild.nameWithoutExtension();
                try {
                    Array<TextureRegion> regions = this.getRegions(strNameWithoutExtension);
                    if (regions != null) {
                        bitmapFont = new BitmapFont(new BitmapFont.BitmapFontData(fileHandleChild, bool.booleanValue()), regions, true);
                    } else {
                        TextureRegion textureRegion = (TextureRegion) this.optional(strNameWithoutExtension, TextureRegion.class);
                        if (textureRegion != null) {
                            bitmapFont = new BitmapFont(fileHandleChild, textureRegion, bool.booleanValue());
                        } else {
                            FileHandle fileHandleChild2 = fileHandleChild.parent().child(strNameWithoutExtension + Constants.DEFAULT_IMAGE_EXTENSION);
                            if (fileHandleChild2.exists()) {
                                bitmapFont = new BitmapFont(fileHandleChild, fileHandleChild2, bool.booleanValue());
                            } else {
                                bitmapFont = new BitmapFont(fileHandleChild, bool.booleanValue());
                            }
                        }
                    }
                    bitmapFont.getData().markupEnabled = bool2.booleanValue();
                    bitmapFont.setUseIntegerPositions(bool3.booleanValue());
                    if (fFloatValue != -1.0f) {
                        bitmapFont.getData().setScale(fFloatValue / bitmapFont.getCapHeight());
                    }
                    return bitmapFont;
                } catch (RuntimeException e) {
                    throw new SerializationException("Error loading bitmap font: " + fileHandleChild, e);
                }
            }
        });
        json.setSerializer(Color.class, new Json.ReadOnlySerializer<Color>() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Skin.4
            @Override // com.badlogic.gdx.utils.Json.ReadOnlySerializer, com.badlogic.gdx.utils.Json.Serializer
            public Color read(Json json2, JsonValue jsonValue, Class cls) {
                if (jsonValue.isString()) {
                    return (Color) Skin.this.get(jsonValue.asString(), Color.class);
                }
                String str = (String) json2.readValue("hex", (Class<Object>) String.class, (Object) null, jsonValue);
                if (str != null) {
                    return Color.valueOf(str);
                }
                return new Color(((Float) json2.readValue("r", (Class<Float>) Float.TYPE, Float.valueOf(0.0f), jsonValue)).floatValue(), ((Float) json2.readValue("g", (Class<Float>) Float.TYPE, Float.valueOf(0.0f), jsonValue)).floatValue(), ((Float) json2.readValue(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, (Class<Float>) Float.TYPE, Float.valueOf(0.0f), jsonValue)).floatValue(), ((Float) json2.readValue("a", (Class<Float>) Float.TYPE, Float.valueOf(1.0f), jsonValue)).floatValue());
            }
        });
        json.setSerializer(TintedDrawable.class, new Json.ReadOnlySerializer() { // from class: com.badlogic.gdx.scenes.scene2d.ui.Skin.5
            @Override // com.badlogic.gdx.utils.Json.ReadOnlySerializer, com.badlogic.gdx.utils.Json.Serializer
            public Object read(Json json2, JsonValue jsonValue, Class cls) {
                String str = (String) json2.readValue("name", String.class, jsonValue);
                Color color = (Color) json2.readValue(TypedValues.Custom.S_COLOR, Color.class, jsonValue);
                if (color == null) {
                    throw new SerializationException("TintedDrawable missing color: " + jsonValue);
                }
                Drawable drawableNewDrawable = Skin.this.newDrawable(str, color);
                if (drawableNewDrawable instanceof BaseDrawable) {
                    ((BaseDrawable) drawableNewDrawable).setName(jsonValue.f125name + " (" + str + ", " + color + Constant.AFTER_QUTO);
                }
                return drawableNewDrawable;
            }
        });
        ObjectMap.Entries<String, Class> it = this.jsonClassTags.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            json.addClassTag((String) next.key, (Class) next.value);
        }
        return json;
    }

    public ObjectMap<String, Class> getJsonClassTags() {
        return this.jsonClassTags;
    }

    private static Method findMethod(Class cls, String str) {
        for (Method method : ClassReflection.getMethods(cls)) {
            if (method.getName().equals(str)) {
                return method;
            }
        }
        return null;
    }
}
