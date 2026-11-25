package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaSpannableStringBuilder;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/FormulaBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class FormulaBrick extends BrickBaseType implements View.OnClickListener {

    @XStreamAlias("formulaList")
    ConcurrentFormulaHashMap formulaMap = new ConcurrentFormulaHashMap();
    public transient BiMap<Brick.FormulaField, Integer> brickFieldToTextViewIdMap = HashBiMap.create(2);

    public Formula getFormulaWithBrickField(Brick.FormulaField formulaField) throws IllegalArgumentException {
        if (this.formulaMap.containsKey(formulaField)) {
            return this.formulaMap.get(formulaField);
        }
        throw new IllegalArgumentException("Incompatible Brick Field: " + getClass().getSimpleName() + " does not have BrickField." + formulaField.toString());
    }

    public Formula getFormulaWithBrickField(Brick.FormulaField formulaField, Boolean ret) {
        if (this.formulaMap.containsKey(formulaField)) {
            return this.formulaMap.get(formulaField);
        }
        if (ret.booleanValue()) {
            return null;
        }
        return new Formula("#000000");
    }

    public Map<Brick.BrickField, Formula> getAllFormulaFieldsWithFormulas() {
        HashMap map = new HashMap();
        ConcurrentFormulaHashMap concurrentFormulaHashMap = this.formulaMap;
        if (concurrentFormulaHashMap == null) {
            return map;
        }
        Enumeration<Brick.FormulaField> enumerationKeys = concurrentFormulaHashMap.keys();
        while (enumerationKeys.hasMoreElements()) {
            Brick.FormulaField formulaFieldNextElement = enumerationKeys.nextElement();
            if (formulaFieldNextElement instanceof Brick.BrickField) {
                map.put((Brick.BrickField) formulaFieldNextElement, this.formulaMap.get(formulaFieldNextElement));
            }
        }
        return map;
    }

    public Map<Brick.FormulaField, Formula> getAllFormulasMap() {
        HashMap map = new HashMap();
        ConcurrentFormulaHashMap concurrentFormulaHashMap = this.formulaMap;
        if (concurrentFormulaHashMap == null) {
            return map;
        }
        Enumeration<Brick.FormulaField> enumerationKeys = concurrentFormulaHashMap.keys();
        while (enumerationKeys.hasMoreElements()) {
            Brick.FormulaField formulaFieldNextElement = enumerationKeys.nextElement();
            map.put(formulaFieldNextElement, this.formulaMap.get(formulaFieldNextElement));
        }
        return map;
    }

    public void setFormulaWithBrickField(Brick.FormulaField formulaField, Formula formula) throws IllegalArgumentException {
        if (this.formulaMap.containsKey(formulaField)) {
            this.formulaMap.replace(formulaField, formula);
            return;
        }
        throw new IllegalArgumentException("Incompatible Brick Field: Cannot set BrickField." + formulaField.toString() + " for " + getClass().getSimpleName());
    }

    protected void addAllowedBrickField(Brick.FormulaField formulaField, int textViewResourceId) {
        this.formulaMap.putIfAbsent(formulaField, new Formula((Integer) 0));
        this.brickFieldToTextViewIdMap.put(formulaField, Integer.valueOf(textViewResourceId));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        Iterator<Formula> it = this.formulaMap.values().iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(requiredResourcesSet);
        }
    }

    public void replaceFormulaBrickField(Brick.FormulaField oldFormulaField, Brick.FormulaField newFormulaField) {
        if (this.formulaMap.containsKey(oldFormulaField)) {
            Formula formula = this.formulaMap.get(oldFormulaField);
            this.formulaMap.remove(oldFormulaField);
            this.formulaMap.put(newFormulaField, formula);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        FormulaBrick formulaBrick = (FormulaBrick) super.clone();
        formulaBrick.formulaMap = this.formulaMap.clone();
        return formulaBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        for (Map.Entry<Brick.FormulaField, Integer> entry : this.brickFieldToTextViewIdMap.entrySet()) {
            TextView textView = (TextView) this.view.findViewById(entry.getValue().intValue());
            textView.setText(FormulaSpannableStringBuilder.buildSpannableFormulaString(this.view.getContext(), getFormulaWithBrickField(entry.getKey()).clone().getTrimmedFormulaString(context), textView.getTextSize()), TextView.BufferType.SPANNABLE);
        }
        return this.view;
    }

    public void setClickListeners() {
        Iterator<Map.Entry<Brick.FormulaField, Integer>> it = this.brickFieldToTextViewIdMap.entrySet().iterator();
        while (it.hasNext()) {
            ((TextView) this.view.findViewById(it.next().getValue().intValue())).setOnClickListener(this);
        }
    }

    public List<Formula> getFormulas() {
        return new ArrayList(this.formulaMap.values());
    }

    public ConcurrentFormulaHashMap getFormulaMap() {
        return this.formulaMap;
    }

    public TextView getTextView(Brick.FormulaField formulaField) {
        return (TextView) this.view.findViewById(this.brickFieldToTextViewIdMap.get(formulaField).intValue());
    }

    public void highlightTextView(Brick.FormulaField formulaField) {
        getTextView(formulaField).getBackground().mutate().setColorFilter(this.view.getContext().getResources().getColor(R.color.brick_field_highlight), PorterDuff.Mode.SRC_ATOP);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        saveCodeFile(view);
        showFormulaEditorToEditFormula(view);
    }

    public void showFormulaEditorToEditFormula(View view) {
        if (this.brickFieldToTextViewIdMap.inverse().containsKey(Integer.valueOf(view.getId()))) {
            FormulaEditorFragment.showFragment(view.getContext(), this, getBrickFieldFromTextViewId(view.getId()));
        } else {
            FormulaEditorFragment.showFragment(view.getContext(), this, getDefaultBrickField());
        }
    }

    public Brick.FormulaField getDefaultBrickField() {
        return this.formulaMap.keys().nextElement();
    }

    boolean isBrickFieldANumber(Brick.FormulaField formulaField) {
        return getFormulaWithBrickField(formulaField).isNumber();
    }

    public View getCustomView(Context context) {
        throw new IllegalStateException("There is no custom view for the " + getClass().getSimpleName() + ".");
    }

    public Brick.FormulaField getBrickFieldFromTextViewId(int textViewId) {
        return this.brickFieldToTextViewIdMap.inverse().get(Integer.valueOf(textViewId));
    }

    protected void setSecondsLabel(View view, Brick.FormulaField formulaField) {
        TextView textView = (TextView) view.findViewById(R.id.brick_seconds_label);
        Context context = textView.getContext();
        if (getFormulaWithBrickField(formulaField).isNumber()) {
            try {
                ProjectManager projectManager = ProjectManager.getInstance();
                textView.setText(context.getResources().getQuantityString(R.plurals.second_plural, Utils.convertDoubleToPluralInteger(this.formulaMap.get(formulaField).interpretDouble(new Scope(projectManager.getCurrentProject(), projectManager.getCurrentSprite(), null)).doubleValue())));
                return;
            } catch (InterpretationException e) {
                Log.e(getClass().getSimpleName(), "Interpretation of formula failed, fallback to quantity \"other\" for \"second(s)\" label.", e);
            }
        }
        textView.setText(context.getResources().getQuantityString(R.plurals.second_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
    }

    public void updateUserDataReference(String oldName, String newName, UserData<?> item, boolean renameAll) {
        for (Formula formula : getFormulas()) {
            if (renameAll) {
                formula.updateVariableName(oldName, newName);
                formula.updateUserlistName(oldName, newName);
            } else if (item instanceof UserVariable) {
                formula.updateVariableName(oldName, newName);
            } else {
                formula.updateUserlistName(oldName, newName);
            }
        }
    }

    private void saveCodeFile(View view) {
        ScriptFragment scriptFragment = getScriptFragment(view);
        if (scriptFragment == null || !scriptFragment.copyProjectForUndoOption()) {
            return;
        }
        if (scriptFragment.getActivity() instanceof SpriteActivity) {
            ((SpriteActivity) scriptFragment.getActivity()).setUndoMenuItemVisibility(true);
        }
        scriptFragment.setUndoBrickPosition(this);
    }

    private ScriptFragment getScriptFragment(View view) {
        AppCompatActivity activityFromView = view != null ? UiUtils.getActivityFromView(view) : null;
        if (activityFromView == null) {
            return null;
        }
        Fragment fragmentFindFragmentById = activityFromView.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragmentFindFragmentById instanceof ScriptFragment) {
            return (ScriptFragment) fragmentFindFragmentById;
        }
        return null;
    }

    public boolean hasEditableFormulaField() {
        return !this.brickFieldToTextViewIdMap.isEmpty();
    }
}
