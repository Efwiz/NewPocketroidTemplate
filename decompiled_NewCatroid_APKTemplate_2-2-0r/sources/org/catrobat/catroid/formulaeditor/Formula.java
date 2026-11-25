package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import java.io.Serializable;
import java.util.Set;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.utils.NumberFormats;
import org.catrobat.catroid.utils.PerformanceTracker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/Formula.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Formula implements Serializable {
    private static final String ERROR_STRING = "NaN";
    private static final long serialVersionUID = 1;
    private Double cachedNumberValue;
    private String cachedStringValue;
    private FormulaElement formulaTree;
    private transient InternFormula internFormula;
    private boolean sceneFirstStart;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/Formula$StringProvider.class
      classes14.dex
     */
    public interface StringProvider {
        String getTrueOrFalse(Boolean value);
    }

    public Formula(FormulaElement formulaElement) {
        this.internFormula = null;
        this.sceneFirstStart = false;
        this.cachedNumberValue = null;
        this.cachedStringValue = null;
        this.formulaTree = formulaElement;
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
    }

    public Formula(Integer value) {
        this.internFormula = null;
        this.sceneFirstStart = false;
        this.cachedNumberValue = null;
        this.cachedStringValue = null;
        if (value.intValue() < 0) {
            initInverted(Long.toString(Math.abs(value.intValue())));
        } else {
            init(FormulaElement.ElementType.NUMBER, value.toString());
        }
    }

    public Formula(Double value) {
        this.internFormula = null;
        this.sceneFirstStart = false;
        this.cachedNumberValue = null;
        this.cachedStringValue = null;
        if (value.doubleValue() < 0.0d) {
            initInverted(Double.toString(Math.abs(value.doubleValue())));
        } else {
            init(FormulaElement.ElementType.NUMBER, value.toString());
        }
    }

    public Formula(Float value) {
        this(Double.valueOf(value.floatValue()));
    }

    public Formula(String value) {
        this.internFormula = null;
        this.sceneFirstStart = false;
        this.cachedNumberValue = null;
        this.cachedStringValue = null;
        if (value.equalsIgnoreCase(Functions.ARDUINOANALOG.toString())) {
            this.formulaTree = new FormulaElement(FormulaElement.ElementType.SENSOR, Functions.ARDUINOANALOG.toString(), null);
        } else if (value.equalsIgnoreCase(Functions.ARDUINODIGITAL.toString())) {
            this.formulaTree = new FormulaElement(FormulaElement.ElementType.SENSOR, Functions.ARDUINODIGITAL.toString(), null);
        } else {
            init(FormulaElement.ElementType.STRING, value);
        }
    }

    public Formula(boolean z) {
        this(Integer.valueOf(z ? 1 : 0));
    }

    public FormulaElement getFormulaTree() {
        return this.formulaTree;
    }

    public void setFormulaTree(FormulaElement tree) {
        this.formulaTree = tree;
    }

    private void init(FormulaElement.ElementType number, String s) {
        this.formulaTree = new FormulaElement(number, s, null);
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
        if (number == FormulaElement.ElementType.NUMBER) {
            try {
                this.cachedNumberValue = Double.valueOf(s);
            } catch (Exception unused) {
            }
        } else if (number == FormulaElement.ElementType.STRING) {
            this.cachedStringValue = s;
        }
    }

    private void initInverted(String value) {
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.MINUS.toString(), null);
        this.formulaTree = formulaElement;
        formulaElement.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, value, this.formulaTree));
        this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
        try {
            this.cachedNumberValue = Double.valueOf(-Double.valueOf(value).doubleValue());
        } catch (Exception unused) {
        }
    }

    public void updateCollisionFormulas(String oldName, String newName, Context context) {
        if (this.internFormula == null && this.formulaTree != null) {
            this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
        }
        InternFormula internFormula = this.internFormula;
        if (internFormula != null) {
            internFormula.updateCollisionFormula(oldName, newName, context);
        }
        FormulaElement formulaElement = this.formulaTree;
        if (formulaElement != null) {
            formulaElement.updateElementByName(oldName, newName, FormulaElement.ElementType.COLLISION_FORMULA);
        }
    }

    public void flattenAllLists() {
        FormulaElement formulaElement = this.formulaTree;
        formulaElement.insertFlattenForAllUserLists(formulaElement, null);
        FormulaElement root = this.formulaTree.getRoot();
        this.formulaTree = root;
        this.internFormula.setInternTokenFormulaList(root.getInternTokenList());
    }

    public void updateCollisionFormulasToVersion() {
        this.internFormula.updateCollisionFormulaToVersion(CatroidApplication.getAppContext());
        this.formulaTree.updateCollisionFormulaToVersion(ProjectManager.getInstance().getCurrentProject());
    }

    public void updateDirectionPropertyToVersion() {
        this.internFormula.updateSensorTokens("OBJECT_ROTATION", "MOTION_DIRECTION", CatroidApplication.getAppContext());
        this.formulaTree.updateElementByName("OBJECT_ROTATION", "MOTION_DIRECTION", FormulaElement.ElementType.SENSOR);
    }

    public void updateVariableName(String oldName, String newName) {
        this.internFormula.updateVariableReferences(oldName, newName, CatroidApplication.getAppContext());
        this.formulaTree.updateElementByName(oldName, newName, FormulaElement.ElementType.USER_VARIABLE);
    }

    public void updateUserlistName(String oldName, String newName) {
        this.internFormula.updateListReferences(oldName, newName, CatroidApplication.getAppContext());
        this.formulaTree.updateElementByName(oldName, newName, FormulaElement.ElementType.USER_LIST);
    }

    public boolean containsSpriteInCollision(String name2) {
        return this.formulaTree.containsSpriteInCollision(name2);
    }

    public Integer interpretInteger(Scope scope) throws InterpretationException {
        return Integer.valueOf(interpretDouble(scope).intValue());
    }

    public Double interpretDouble(Scope scope) throws InterpretationException {
        PerformanceTracker.formulaEvaluations++;
        if (this.sceneFirstStart) {
            this.sceneFirstStart = false;
            return Double.valueOf(0.0d);
        }
        Double d = this.cachedNumberValue;
        if (d != null) {
            return d;
        }
        try {
            return Double.valueOf(assertNotNaN(interpretDoubleInternal(scope)));
        } catch (ClassCastException | NumberFormatException e) {
            throw new InterpretationException("Couldn't interpret Formula.", e);
        }
    }

    private Double interpretDoubleInternal(Scope scope) {
        Object objInterpretRecursive = this.formulaTree.interpretRecursive(scope);
        if (objInterpretRecursive instanceof Double) {
            return (Double) objInterpretRecursive;
        }
        if (objInterpretRecursive instanceof Integer) {
            return Double.valueOf(((Integer) objInterpretRecursive).doubleValue());
        }
        try {
            return Double.valueOf(String.valueOf(objInterpretRecursive));
        } catch (NumberFormatException unused) {
            return Double.valueOf(0.0d);
        }
    }

    private double assertNotNaN(Double doubleReturnValue) throws InterpretationException {
        if (doubleReturnValue.isNaN()) {
            throw new InterpretationException("NaN in interpretDouble()");
        }
        return doubleReturnValue.doubleValue();
    }

    public Float interpretFloat(Scope scope) throws InterpretationException {
        return Float.valueOf(interpretDouble(scope).floatValue());
    }

    public String interpretString(Scope scope) throws InterpretationException {
        String strValueOf;
        PerformanceTracker.formulaEvaluations++;
        String str = this.cachedStringValue;
        if (str != null) {
            return str;
        }
        Object objInterpretRecursive = this.formulaTree.interpretRecursive(scope);
        if ((objInterpretRecursive instanceof Double) && ((Double) objInterpretRecursive).isNaN()) {
            throw new InterpretationException("NaN in interpretString()");
        }
        Project project = scope != null ? scope.getProject() : null;
        if (project == null && ProjectManager.getInstance() != null) {
            project = ProjectManager.getInstance().getCurrentProject();
        }
        if (project != null) {
            strValueOf = project.formatValue(objInterpretRecursive);
        } else {
            strValueOf = String.valueOf(objInterpretRecursive);
        }
        return NumberFormats.trimTrailingCharacters(strValueOf);
    }

    public Object interpretObject(Scope scope) {
        return this.formulaTree.interpretRecursive(scope);
    }

    public void setRoot(FormulaElement formula) {
        this.formulaTree = formula;
        this.internFormula = new InternFormula(formula.getInternTokenList());
        this.cachedNumberValue = null;
        this.cachedStringValue = null;
    }

    public FormulaElement getRoot() {
        return this.formulaTree;
    }

    public String getTrimmedFormulaString(Context context) {
        if (this.internFormula == null) {
            if (this.formulaTree != null) {
                this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
            } else {
                init(FormulaElement.ElementType.NUMBER, "0");
            }
        }
        return this.internFormula.trimExternFormulaString(context);
    }

    public InternFormulaState getInternFormulaState() {
        if (this.internFormula == null) {
            if (this.formulaTree != null) {
                this.internFormula = new InternFormula(this.formulaTree.getInternTokenList());
            } else {
                init(FormulaElement.ElementType.NUMBER, "0");
            }
        }
        return this.internFormula.getInternFormulaState();
    }

    public boolean containsElement(FormulaElement.ElementType elementType) {
        return this.formulaTree.containsElement(elementType);
    }

    public boolean isNumber() {
        return this.formulaTree.isNumber();
    }

    public Formula clone() {
        FormulaElement formulaElement = this.formulaTree;
        if (formulaElement != null) {
            return new Formula(formulaElement.clone());
        }
        return new Formula((Integer) 0);
    }

    public void addRequiredResources(final Set<Integer> requiredResourcesSet) {
        this.formulaTree.addRequiredResources(requiredResourcesSet);
    }

    public String getUserFriendlyString(StringProvider stringProvider, Scope scope) {
        if (this.formulaTree.isBoolean(scope)) {
            return tryInterpretBooleanToString(stringProvider, scope);
        }
        return tryInterpretString(scope);
    }

    private String tryInterpretString(Scope scope) {
        try {
            return interpretString(scope);
        } catch (InterpretationException unused) {
            return ERROR_STRING;
        }
    }

    private String tryInterpretBooleanToString(StringProvider stringProvider, Scope scope) {
        try {
            return interpretBooleanToString(scope, stringProvider);
        } catch (InterpretationException unused) {
            return ERROR_STRING;
        }
    }

    public String interpretBooleanToString(Scope scope, StringProvider stringProvider) throws InterpretationException {
        return toLocalizedString(interpretBoolean(scope), stringProvider);
    }

    public boolean interpretBoolean(Scope scope) throws InterpretationException {
        return interpretDouble(scope).intValue() != 0;
    }

    private String toLocalizedString(boolean value, StringProvider stringProvider) {
        return stringProvider.getTrueOrFalse(Boolean.valueOf(value));
    }

    public void sceneFirstStart(boolean sceneFirstStart) {
        this.sceneFirstStart = sceneFirstStart;
    }
}
