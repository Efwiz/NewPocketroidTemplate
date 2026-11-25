package org.jetbrains.kotlin.com.intellij.openapi.editor.markup;

import java.awt.Color;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class AttributesFlyweight {
    private static final ConcurrentMap<FlyweightKey, AttributesFlyweight> entries = new ConcurrentHashMap();
    private static final ThreadLocal<FlyweightKey> ourKey = new ThreadLocal<>();
    private final Map<EffectType, ? extends Color> myAdditionalEffects;
    private final Color myBackground;
    private final Color myEffectColor;
    private final EffectType myEffectType;
    private final Color myErrorStripeColor;
    private final int myFontType;
    private final Color myForeground;
    private final int myHashCode;

    /* JADX WARN: Code duplicated, block: B:7:0x000c  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 2) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/markup/AttributesFlyweight";
                break;
            case 3:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 4:
            case 5:
            case 7:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 6:
                objArr[0] = "fieldName";
                break;
            default:
                objArr[0] = "additionalEffects";
                break;
        }
        if (i != 1 && i != 2) {
            switch (i) {
                case 8:
                    objArr[1] = "getAdditionalEffects";
                    break;
                case 9:
                case 10:
                    objArr[1] = "getAllEffects";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/markup/AttributesFlyweight";
                    break;
            }
        } else {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        switch (i) {
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
                break;
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 5:
            case 6:
                objArr[2] = "writeColor";
                break;
            case 7:
                objArr[2] = "writeExternal";
                break;
            case 11:
                objArr[2] = "withAdditionalEffects";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            switch (i) {
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private static final class FlyweightKey implements Cloneable {
        private Color background;
        private Color effectColor;
        private EffectType effectType;
        private Color errorStripeColor;
        private int fontType;
        private Color foreground;
        private Map<EffectType, ? extends Color> myAdditionalEffects;

        /* synthetic */ FlyweightKey(AnonymousClass1 anonymousClass1) {
            this();
        }

        private FlyweightKey() {
            this.myAdditionalEffects = Collections.emptyMap();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FlyweightKey)) {
                return false;
            }
            FlyweightKey flyweightKey = (FlyweightKey) obj;
            return this.fontType == flyweightKey.fontType && Objects.equals(this.background, flyweightKey.background) && Objects.equals(this.effectColor, flyweightKey.effectColor) && this.effectType == flyweightKey.effectType && Objects.equals(this.errorStripeColor, flyweightKey.errorStripeColor) && Objects.equals(this.foreground, flyweightKey.foreground) && this.myAdditionalEffects.equals(flyweightKey.myAdditionalEffects);
        }

        public int hashCode() {
            Color color = this.foreground;
            int iHashCode = (color != null ? color.hashCode() : 0) * 31;
            Color color2 = this.background;
            int iHashCode2 = (((iHashCode + (color2 != null ? color2.hashCode() : 0)) * 31) + this.fontType) * 31;
            Color color3 = this.effectColor;
            int iHashCode3 = (iHashCode2 + (color3 != null ? color3.hashCode() : 0)) * 31;
            EffectType effectType = this.effectType;
            int iHashCode4 = (iHashCode3 + (effectType != null ? effectType.hashCode() : 0)) * 31;
            Color color4 = this.errorStripeColor;
            return ((iHashCode4 + (color4 != null ? color4.hashCode() : 0)) * 31) + this.myAdditionalEffects.hashCode();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public FlyweightKey clone() {
            try {
                return (FlyweightKey) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static AttributesFlyweight create(Color color, Color color2, int i, Color color3, EffectType effectType, Map<EffectType, ? extends Color> map, Color color4) {
        if (map == null) {
            $$$reportNull$$$0(0);
        }
        ThreadLocal<FlyweightKey> threadLocal = ourKey;
        FlyweightKey flyweightKey = threadLocal.get();
        if (flyweightKey == null) {
            flyweightKey = new FlyweightKey(null);
            threadLocal.set(flyweightKey);
        }
        flyweightKey.foreground = color;
        flyweightKey.background = color2;
        flyweightKey.fontType = i;
        flyweightKey.effectColor = color3;
        flyweightKey.effectType = effectType;
        flyweightKey.myAdditionalEffects = map.isEmpty() ? Collections.emptyMap() : new EnumMap(map);
        flyweightKey.errorStripeColor = color4;
        ConcurrentMap<FlyweightKey, AttributesFlyweight> concurrentMap = entries;
        AttributesFlyweight attributesFlyweight = concurrentMap.get(flyweightKey);
        if (attributesFlyweight != null) {
            if (attributesFlyweight == null) {
                $$$reportNull$$$0(1);
            }
            return attributesFlyweight;
        }
        AttributesFlyweight attributesFlyweight2 = (AttributesFlyweight) ConcurrencyUtil.cacheOrGet(concurrentMap, flyweightKey.clone(), new AttributesFlyweight(flyweightKey));
        if (attributesFlyweight2 == null) {
            $$$reportNull$$$0(2);
        }
        return attributesFlyweight2;
    }

    private AttributesFlyweight(FlyweightKey flyweightKey) {
        if (flyweightKey == null) {
            $$$reportNull$$$0(3);
        }
        this.myForeground = flyweightKey.foreground;
        this.myBackground = flyweightKey.background;
        this.myFontType = flyweightKey.fontType;
        this.myEffectColor = flyweightKey.effectColor;
        this.myEffectType = flyweightKey.effectType;
        this.myErrorStripeColor = flyweightKey.errorStripeColor;
        this.myAdditionalEffects = flyweightKey.myAdditionalEffects;
        this.myHashCode = flyweightKey.hashCode();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.editor.markup.AttributesFlyweight$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$openapi$editor$markup$EffectType;

        static {
            int[] iArr = new int[EffectType.values().length];
            $SwitchMap$com$intellij$openapi$editor$markup$EffectType = iArr;
            try {
                iArr[EffectType.BOXED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$openapi$editor$markup$EffectType[EffectType.LINE_UNDERSCORE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$openapi$editor$markup$EffectType[EffectType.BOLD_LINE_UNDERSCORE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$openapi$editor$markup$EffectType[EffectType.STRIKEOUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$openapi$editor$markup$EffectType[EffectType.WAVE_UNDERSCORE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$openapi$editor$markup$EffectType[EffectType.BOLD_DOTTED_LINE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public Color getForeground() {
        return this.myForeground;
    }

    public Color getBackground() {
        return this.myBackground;
    }

    public int getFontType() {
        return this.myFontType;
    }

    public Color getEffectColor() {
        return this.myEffectColor;
    }

    public EffectType getEffectType() {
        return this.myEffectType;
    }

    Map<EffectType, ? extends Color> getAdditionalEffects() {
        Map<EffectType, ? extends Color> map = this.myAdditionalEffects;
        if (map == null) {
            $$$reportNull$$$0(8);
        }
        return map;
    }

    Color getErrorStripeColor() {
        return this.myErrorStripeColor;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttributesFlyweight attributesFlyweight = (AttributesFlyweight) obj;
        return this.myFontType == attributesFlyweight.myFontType && Objects.equals(this.myBackground, attributesFlyweight.myBackground) && Objects.equals(this.myEffectColor, attributesFlyweight.myEffectColor) && this.myEffectType == attributesFlyweight.myEffectType && Objects.equals(this.myErrorStripeColor, attributesFlyweight.myErrorStripeColor) && Objects.equals(this.myForeground, attributesFlyweight.myForeground) && this.myAdditionalEffects.equals(attributesFlyweight.myAdditionalEffects);
    }

    public int hashCode() {
        return this.myHashCode;
    }

    public String toString() {
        return "AttributesFlyweight{myForeground=" + this.myForeground + ", myBackground=" + this.myBackground + ", myFontType=" + this.myFontType + ", myEffectColor=" + this.myEffectColor + ", myEffectType=" + this.myEffectType + ", myErrorStripeColor=" + this.myErrorStripeColor + '}';
    }
}
