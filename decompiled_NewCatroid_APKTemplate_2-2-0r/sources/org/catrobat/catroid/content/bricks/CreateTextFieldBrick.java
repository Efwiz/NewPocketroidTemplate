package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateTextFieldBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateTextFieldBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_create_textfield_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_textfield;
    }

    public CreateTextFieldBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.create_textfield_name);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.create_textfield_default);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.create_textfield_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.create_textfield_y);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.create_textfield_width);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.create_textfield_height);
        addAllowedBrickField(Brick.BrickField.TEXTSIZE, R.id.create_textfield_textsize);
        addAllowedBrickField(Brick.BrickField.TEXTCOLOR, R.id.create_textfield_textcolor);
        addAllowedBrickField(Brick.BrickField.BGCOLOR, R.id.create_textfield_bgcolor);
        addAllowedBrickField(Brick.BrickField.HINT_TEXT, R.id.create_textfield_hinttext);
        addAllowedBrickField(Brick.BrickField.HINT_COLOR, R.id.create_textfield_hintcolor);
        addAllowedBrickField(Brick.BrickField.ALIGNMENT, R.id.create_textfield_alignment);
        addAllowedBrickField(Brick.BrickField.PASSWORD, R.id.create_textfield_password);
        addAllowedBrickField(Brick.BrickField.FILE, R.id.create_textfield_font);
        addAllowedBrickField(Brick.BrickField.CORNER, R.id.create_textfield_corner);
        addAllowedBrickField(Brick.BrickField.MAX_LEN, R.id.create_textfield_length);
        addAllowedBrickField(Brick.BrickField.TYPE, R.id.create_textfield_type);
    }

    public CreateTextFieldBrick(String name2, String defaultV, Integer x, Integer y, Integer w, Integer h, Integer ts, String tc, String bc, String ht, String hc, String alignment, Integer password, Integer corner, Integer max, String type, String font) {
        this(new Formula(name2), new Formula(defaultV), new Formula(x), new Formula(y), new Formula(w), new Formula(h), new Formula(ts), new Formula(tc), new Formula(bc), new Formula(ht), new Formula(hc), new Formula(alignment), new Formula(password), new Formula(corner), new Formula(max), new Formula(type), new Formula(font));
    }

    private CreateTextFieldBrick(Formula name2, Formula def, Formula x, Formula y, Formula w, Formula h, Formula ts, Formula tc, Formula bc, Formula ht, Formula hc, Formula align, Formula pass, Formula corner, Formula max, Formula type, Formula font) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.TEXT, def);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.WIDTH, w);
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, h);
        setFormulaWithBrickField(Brick.BrickField.TEXTSIZE, ts);
        setFormulaWithBrickField(Brick.BrickField.TEXTCOLOR, tc);
        setFormulaWithBrickField(Brick.BrickField.BGCOLOR, bc);
        setFormulaWithBrickField(Brick.BrickField.HINT_TEXT, ht);
        setFormulaWithBrickField(Brick.BrickField.HINT_COLOR, hc);
        setFormulaWithBrickField(Brick.BrickField.ALIGNMENT, align);
        setFormulaWithBrickField(Brick.BrickField.PASSWORD, pass);
        setFormulaWithBrickField(Brick.BrickField.FILE, font);
        setFormulaWithBrickField(Brick.BrickField.CORNER, corner);
        setFormulaWithBrickField(Brick.BrickField.MAX_LEN, max);
        setFormulaWithBrickField(Brick.BrickField.TYPE, type);
    }

    public CreateTextFieldBrick(Formula name2, Formula def, Formula x, Formula y, Formula w, Formula h, Formula ts, Formula tc, Formula bc, Formula ht, Formula hc, Formula align, Formula pass, Formula corner, Formula max, Formula type, Formula font, UserVariable userVariable) {
        this(name2, def, x, y, w, h, ts, tc, bc, ht, hc, align, pass, corner, max, type, font);
        this.userVariable = userVariable;
    }

    public CreateTextFieldBrick(Sensors defaultValue) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, defaultValue.name(), null)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTextfield(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT), getFormulaWithBrickField(Brick.BrickField.TEXTSIZE), getFormulaWithBrickField(Brick.BrickField.TEXTCOLOR), getFormulaWithBrickField(Brick.BrickField.BGCOLOR), getFormulaWithBrickField(Brick.BrickField.HINT_TEXT), getFormulaWithBrickField(Brick.BrickField.HINT_COLOR), getFormulaWithBrickField(Brick.BrickField.ALIGNMENT), getFormulaWithBrickField(Brick.BrickField.PASSWORD), getFormulaWithBrickField(Brick.BrickField.CORNER), getFormulaWithBrickField(Brick.BrickField.MAX_LEN), getFormulaWithBrickField(Brick.BrickField.TYPE), getFormulaWithBrickField(Brick.BrickField.FILE), this.userVariable));
    }
}
