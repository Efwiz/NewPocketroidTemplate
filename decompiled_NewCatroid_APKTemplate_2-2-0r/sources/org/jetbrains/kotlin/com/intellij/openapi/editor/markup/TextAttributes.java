package org.jetbrains.kotlin.com.intellij.openapi.editor.markup;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.awt.Color;
import java.util.Collections;
import java.util.Objects;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public class TextAttributes implements Cloneable {
    private AttributesFlyweight myAttrs;
    private static final Logger LOG = Logger.getInstance((Class<?>) TextAttributes.class);
    private static final AttributesFlyweight DEFAULT_FLYWEIGHT = AttributesFlyweight.create(null, null, 0, null, EffectType.BOXED, Collections.emptyMap(), null);
    public static final TextAttributes ERASE_MARKER = new TextAttributes();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        switch (i) {
            case 1:
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
                objArr[0] = "other";
                break;
            case 3:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/markup/TextAttributes";
                break;
            case 4:
                objArr[0] = "flyweight";
                break;
            case 5:
                objArr[0] = "effectsMap";
                break;
            case 6:
                objArr[0] = "effectType";
                break;
            case 7:
                objArr[0] = TypedValues.Custom.S_COLOR;
                break;
            case 8:
            case 9:
                objArr[0] = "consumer";
                break;
            default:
                objArr[0] = "attributesFlyweight";
                break;
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/markup/TextAttributes";
        } else {
            objArr[1] = "getFlyweight";
        }
        switch (i) {
            case 2:
                objArr[2] = "copyFrom";
                break;
            case 3:
                break;
            case 4:
                objArr[2] = "fromFlyweight";
                break;
            case 5:
                objArr[2] = "setAdditionalEffects";
                break;
            case 6:
            case 7:
                objArr[2] = "withAdditionalEffect";
                break;
            case 8:
                objArr[2] = "forEachAdditionalEffect";
                break;
            case 9:
                objArr[2] = "forEachEffect";
                break;
            case 10:
                objArr[2] = "readExternal";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    public TextAttributes() {
        this(DEFAULT_FLYWEIGHT);
    }

    private TextAttributes(AttributesFlyweight attributesFlyweight) {
        if (attributesFlyweight == null) {
            $$$reportNull$$$0(0);
        }
        this.myAttrs = attributesFlyweight;
    }

    public Color getForegroundColor() {
        return this.myAttrs.getForeground();
    }

    public Color getBackgroundColor() {
        return this.myAttrs.getBackground();
    }

    public Color getEffectColor() {
        return this.myAttrs.getEffectColor();
    }

    public Color getErrorStripeColor() {
        return this.myAttrs.getErrorStripeColor();
    }

    public EffectType getEffectType() {
        return this.myAttrs.getEffectType();
    }

    public int getFontType() {
        return this.myAttrs.getFontType();
    }

    public TextAttributes clone() {
        return new TextAttributes(this.myAttrs);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TextAttributes) {
            return Objects.equals(this.myAttrs, ((TextAttributes) obj).myAttrs);
        }
        return false;
    }

    public int hashCode() {
        return this.myAttrs.hashCode();
    }

    public String toString() {
        return "[" + getForegroundColor() + "," + getBackgroundColor() + "," + getFontType() + "," + getEffectType() + "," + getEffectColor() + "," + this.myAttrs.getAdditionalEffects() + "," + getErrorStripeColor() + "]";
    }
}
