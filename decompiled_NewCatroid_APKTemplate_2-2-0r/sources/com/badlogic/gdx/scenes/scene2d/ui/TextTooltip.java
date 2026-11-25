package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextTooltip.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class TextTooltip extends Tooltip<Label> {
    public TextTooltip(String str, Skin skin) {
        this(str, TooltipManager.getInstance(), (TextTooltipStyle) skin.get(TextTooltipStyle.class));
    }

    public TextTooltip(String str, Skin skin, String str2) {
        this(str, TooltipManager.getInstance(), (TextTooltipStyle) skin.get(str2, TextTooltipStyle.class));
    }

    public TextTooltip(String str, TextTooltipStyle textTooltipStyle) {
        this(str, TooltipManager.getInstance(), textTooltipStyle);
    }

    public TextTooltip(String str, TooltipManager tooltipManager, Skin skin) {
        this(str, tooltipManager, (TextTooltipStyle) skin.get(TextTooltipStyle.class));
    }

    public TextTooltip(String str, TooltipManager tooltipManager, Skin skin, String str2) {
        this(str, tooltipManager, (TextTooltipStyle) skin.get(str2, TextTooltipStyle.class));
    }

    public TextTooltip(String str, TooltipManager tooltipManager, TextTooltipStyle textTooltipStyle) {
        super(null, tooltipManager);
        this.container.setActor(newLabel(str, textTooltipStyle.label));
        setStyle(textTooltipStyle);
    }

    protected Label newLabel(String str, Label.LabelStyle labelStyle) {
        return new Label(str, labelStyle);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void setStyle(TextTooltipStyle textTooltipStyle) {
        if (textTooltipStyle == null) {
            throw new NullPointerException("style cannot be null");
        }
        this.container.setBackground(textTooltipStyle.background);
        this.container.maxWidth(textTooltipStyle.wrapWidth);
        boolean z = textTooltipStyle.wrapWidth != 0.0f;
        this.container.fill(z);
        Label label = (Label) this.container.getActor();
        label.setStyle(textTooltipStyle.label);
        label.setWrap(z);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/ui/TextTooltip$TextTooltipStyle.class
      classes8.dex
     */
    public static class TextTooltipStyle {
        public Drawable background;
        public Label.LabelStyle label;
        public float wrapWidth;

        public TextTooltipStyle() {
        }

        public TextTooltipStyle(Label.LabelStyle labelStyle, Drawable drawable) {
            this.label = labelStyle;
            this.background = drawable;
        }

        public TextTooltipStyle(TextTooltipStyle textTooltipStyle) {
            this.label = new Label.LabelStyle(textTooltipStyle.label);
            this.background = textTooltipStyle.background;
            this.wrapWidth = textTooltipStyle.wrapWidth;
        }
    }
}
