package org.catrobat.catroid.formulaeditor;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Base64;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.NewCatroidHttpManager;
import org.catrobat.catroid.content.FloatArrayManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.TableManager;
import org.catrobat.catroid.content.UserVarsManager;
import org.catrobat.catroid.fast2d.Fast2DFormulaBridge;
import org.catrobat.catroid.fast2d.FastTwoDManager;
import org.catrobat.catroid.formulaeditor.common.Conversions;
import org.catrobat.catroid.formulaeditor.common.FormulaElementOperations;
import org.catrobat.catroid.formulaeditor.common.FormulaElementResources;
import org.catrobat.catroid.formulaeditor.function.ArduinoFunctionProvider;
import org.catrobat.catroid.formulaeditor.function.BinaryFunction;
import org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction;
import org.catrobat.catroid.formulaeditor.function.FormulaFunction;
import org.catrobat.catroid.formulaeditor.function.FunctionProvider;
import org.catrobat.catroid.formulaeditor.function.MathFunctionProvider;
import org.catrobat.catroid.formulaeditor.function.ObjectDetectorFunctionProvider;
import org.catrobat.catroid.formulaeditor.function.RaspiFunctionProvider;
import org.catrobat.catroid.formulaeditor.function.TernaryFunction;
import org.catrobat.catroid.formulaeditor.function.TernaryFunctionAction;
import org.catrobat.catroid.formulaeditor.function.TextBlockFunctionProvider;
import org.catrobat.catroid.formulaeditor.function.TouchFunctionProvider;
import org.catrobat.catroid.libraries.LibraryManager;
import org.catrobat.catroid.libraries.LoadedLibrary;
import org.catrobat.catroid.ml.MLBridge;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.raptor.VoxelManager;
import org.catrobat.catroid.sensing.CollisionDetection;
import org.catrobat.catroid.sensing.ColorAtXYDetection;
import org.catrobat.catroid.sensing.ColorCollisionDetection;
import org.catrobat.catroid.sensing.ColorEqualsColor;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.HashUtils;
import org.catrobat.catroid.utils.NewCatroidMqttManager;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.catrobat.catroid.utils.NumberFormats;
import org.catrobat.catroid.utils.PerformanceTracker;
import org.catrobat.catroid.utils.lunoscript.Interpreter;
import org.catrobat.catroid.utils.lunoscript.LunoRuntimeError;
import org.catrobat.catroid.utils.lunoscript.LunoValue;
import org.catrobat.catroid.utils.lunoscript.Token;
import org.catrobat.catroid.utils.lunoscript.TokenType;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.mozilla.javascript.ContextFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaElement.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaElement implements Serializable {
    private static final String TAG_FORMULA_ELEMENT = "FormulaElement";
    private static final Map<String, LuaValue> compiledLuaCache = new ConcurrentHashMap();
    private static Globals luaGlobals = null;
    private static boolean rhinoInitialized = false;
    private static final long serialVersionUID = 1;
    public List<FormulaElement> additionalChildren;
    private transient UserData cachedBrickInput;
    private transient Double cachedDoubleValue;
    private transient Scope cachedScope;
    private transient SequenceAction cachedSequence;
    private transient UserList cachedUserList;
    private transient UserVariable cachedUserVariable;
    private transient Map<Functions, FormulaFunction> formulaFunctions;
    public FormulaElement leftChild;
    private transient FormulaElement parent;
    public FormulaElement rightChild;
    private transient TextBlockFunctionProvider textBlockFunctionProvider;
    public ElementType type;
    private String value;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaElement$ElementType.class
      classes14.dex
     */
    public enum ElementType {
        OPERATOR,
        FUNCTION,
        NUMBER,
        SENSOR,
        USER_VARIABLE,
        USER_LIST,
        USER_DEFINED_BRICK_INPUT,
        BRACKET,
        STRING,
        COLLISION_FORMULA
    }

    private static void ensureRhinoInitialized() {
        if (ContextFactory.hasExplicitGlobal()) {
            return;
        }
        ContextFactory.initGlobal(new ContextFactory());
    }

    protected FormulaElement() {
        this.cachedUserVariable = null;
        this.cachedUserList = null;
        this.cachedScope = null;
        this.cachedBrickInput = null;
        this.cachedSequence = null;
        this.cachedDoubleValue = null;
        this.leftChild = null;
        this.rightChild = null;
        this.textBlockFunctionProvider = new TextBlockFunctionProvider();
        List<FunctionProvider> listAsList = Arrays.asList(new ArduinoFunctionProvider(), new RaspiFunctionProvider(), new MathFunctionProvider(), new TouchFunctionProvider(), this.textBlockFunctionProvider, new ObjectDetectorFunctionProvider());
        EnumMap enumMap = new EnumMap(Functions.class);
        this.formulaFunctions = enumMap;
        initFunctionMap(listAsList, enumMap);
        this.additionalChildren = new ArrayList();
    }

    public FormulaElement(ElementType type, String value, FormulaElement parent) {
        this();
        this.type = type;
        this.value = value;
        this.parent = parent;
    }

    public FormulaElement(ElementType type, String value, FormulaElement parent, FormulaElement leftChild, FormulaElement rightChild) {
        this(type, value, parent);
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        if (leftChild != null) {
            leftChild.parent = this;
        }
        if (rightChild != null) {
            rightChild.parent = this;
        }
    }

    public FormulaElement(ElementType type, String value, FormulaElement parent, FormulaElement leftChild, FormulaElement rightChild, List<FormulaElement> additionalChildren) {
        this(type, value, parent, leftChild, rightChild);
        Iterator<FormulaElement> it = additionalChildren.iterator();
        while (it.hasNext()) {
            addAdditionalChild(it.next());
        }
    }

    private void initFunctionMap(List<FunctionProvider> functionProviders, Map<Functions, FormulaFunction> formulaFunctions) {
        Iterator<FunctionProvider> it = functionProviders.iterator();
        while (it.hasNext()) {
            it.next().addFunctionsToMap(formulaFunctions);
        }
        formulaFunctions.put(Functions.RAND, new BinaryFunction(new BinaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.FormulaElement$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.BinaryFunctionAction
            public final Object execute(double d, double d2) {
                return Double.valueOf(this.f$0.interpretFunctionRand(d, d2));
            }
        }));
        formulaFunctions.put(Functions.IF_THEN_ELSE, new TernaryFunction(new TernaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.FormulaElement$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.formulaeditor.function.TernaryFunctionAction
            public final Object execute(double d, double d2, double d3) {
                return Double.valueOf(this.f$0.interpretFunctionIfThenElse(d, d2, d3));
            }
        }));
    }

    public ElementType getElementType() {
        return this.type;
    }

    private FastTwoDManager getFastTwoDManager() {
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || stageActivity.stageListener == null) {
            return null;
        }
        return stageActivity.stageListener.fastTwoDManager;
    }

    public String getValue() {
        return NumberFormats.trimTrailingCharacters(this.value);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public List<InternToken> getInternTokenList() {
        LinkedList linkedList = new LinkedList();
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[this.type.ordinal()]) {
            case 1:
                addBracketTokens(linkedList, this.rightChild);
                return linkedList;
            case 2:
                addOperatorTokens(linkedList, this.value);
                return linkedList;
            case 3:
                addFunctionTokens(linkedList, this.value, this.leftChild, this.rightChild);
                return linkedList;
            case 4:
                addToken(linkedList, InternTokenType.USER_VARIABLE, this.value);
                return linkedList;
            case 5:
                addToken(linkedList, InternTokenType.USER_LIST, this.value);
                return linkedList;
            case 6:
                addToken(linkedList, InternTokenType.USER_DEFINED_BRICK_INPUT, this.value);
                return linkedList;
            case 7:
                addToken(linkedList, InternTokenType.NUMBER, NumberFormats.trimTrailingCharacters(this.value));
                return linkedList;
            case 8:
                addToken(linkedList, InternTokenType.SENSOR, this.value);
                return linkedList;
            case 9:
                addToken(linkedList, InternTokenType.STRING, this.value);
                return linkedList;
            case 10:
                addToken(linkedList, InternTokenType.COLLISION_FORMULA, this.value);
                return linkedList;
            default:
                return linkedList;
        }
    }

    private void addToken(List<InternToken> tokens, InternTokenType tokenType) {
        tokens.add(new InternToken(tokenType));
    }

    private void addToken(List<InternToken> tokens, InternTokenType tokenType, String value) {
        tokens.add(new InternToken(tokenType, value));
    }

    private void addBracketTokens(List<InternToken> internTokenList, FormulaElement element) {
        addToken(internTokenList, InternTokenType.BRACKET_OPEN);
        tryAddInternTokens(internTokenList, element);
        addToken(internTokenList, InternTokenType.BRACKET_CLOSE);
    }

    private void addOperatorTokens(List<InternToken> tokens, String value) {
        tryAddInternTokens(tokens, this.leftChild);
        addToken(tokens, InternTokenType.OPERATOR, value);
        tryAddInternTokens(tokens, this.rightChild);
    }

    private void addFunctionTokens(List<InternToken> tokens, String value, FormulaElement leftChild, FormulaElement rightChild) {
        boolean z;
        addToken(tokens, InternTokenType.FUNCTION_NAME, value);
        if (leftChild != null) {
            addToken(tokens, InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN);
            tokens.addAll(leftChild.getInternTokenList());
            z = true;
        } else {
            z = false;
        }
        if (rightChild != null) {
            addToken(tokens, InternTokenType.FUNCTION_PARAMETER_DELIMITER);
            tokens.addAll(rightChild.getInternTokenList());
        }
        for (FormulaElement formulaElement : this.additionalChildren) {
            if (formulaElement != null) {
                addToken(tokens, InternTokenType.FUNCTION_PARAMETER_DELIMITER);
                tokens.addAll(formulaElement.getInternTokenList());
            }
        }
        if (z) {
            addToken(tokens, InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE);
        }
    }

    private void tryAddInternTokens(List<InternToken> tokens, FormulaElement child) {
        if (child != null) {
            tokens.addAll(child.getInternTokenList());
        }
    }

    public FormulaElement getRoot() {
        FormulaElement parent = this;
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent;
    }

    public void updateElementByName(String oldName, String newName, ElementType type) {
        tryUpdateElementByName(this.leftChild, oldName, newName, type);
        tryUpdateElementByName(this.rightChild, oldName, newName, type);
        Iterator<FormulaElement> it = this.additionalChildren.iterator();
        while (it.hasNext()) {
            tryUpdateElementByName(it.next(), oldName, newName, type);
        }
        if (matchesTypeAndName(type, oldName)) {
            this.value = newName;
        }
    }

    private void tryUpdateElementByName(FormulaElement element, String oldName, String newName, ElementType type) {
        if (element != null) {
            element.updateElementByName(oldName, newName, type);
        }
    }

    public final boolean containsSpriteInCollision(String name2) {
        if (containsSpriteInCollision(this.leftChild, name2) || containsSpriteInCollision(this.rightChild, name2)) {
            return true;
        }
        Iterator<FormulaElement> it = this.additionalChildren.iterator();
        while (it.hasNext()) {
            if (containsSpriteInCollision(it.next(), name2)) {
                return true;
            }
        }
        return matchesTypeAndName(ElementType.COLLISION_FORMULA, name2);
    }

    private boolean containsSpriteInCollision(FormulaElement element, String name2) {
        return element != null && element.containsSpriteInCollision(name2);
    }

    public final void insertFlattenForAllUserLists(FormulaElement element, FormulaElement parent) {
        FormulaElement formulaElement = element.leftChild;
        if (formulaElement != null) {
            insertFlattenForAllUserLists(formulaElement, element);
        }
        FormulaElement formulaElement2 = element.rightChild;
        if (formulaElement2 != null) {
            insertFlattenForAllUserLists(formulaElement2, element);
        }
        for (FormulaElement formulaElement3 : element.additionalChildren) {
            if (formulaElement3 != null) {
                insertFlattenForAllUserLists(formulaElement3, element);
            }
        }
        if (element.type == ElementType.USER_LIST && isNotUserListFunction(parent)) {
            insertFlattenBetweenParentAndElement(parent, element);
        }
    }

    public boolean isNotUserListFunction(FormulaElement element) {
        return (element != null && element.type == ElementType.FUNCTION && (element.value.equals(Functions.CONTAINS.name()) || element.value.equals(Functions.NUMBER_OF_ITEMS.name()) || element.value.equals(Functions.LIST_ITEM.name()) || element.value.equals(Functions.INDEX_OF_ITEM.name()) || element.value.equals(Functions.FLATTEN.name()) || element.value.equals(Functions.CONNECT.name()) || element.value.equals(Functions.FIND.name()))) ? false : true;
    }

    public void insertFlattenBetweenParentAndElement(FormulaElement parent, FormulaElement element) {
        insertElementBeforeChildInFormulaTree(parent, element, new FormulaElement(ElementType.FUNCTION, Functions.FLATTEN.name(), parent));
    }

    private void insertElementBeforeChildInFormulaTree(FormulaElement parent, FormulaElement child, FormulaElement elementToInsert) {
        if (child == null || elementToInsert == null) {
            return;
        }
        child.parent = elementToInsert;
        elementToInsert.setLeftChild(child);
        if (parent == null) {
            return;
        }
        if (parent.leftChild == child) {
            parent.leftChild = elementToInsert;
            return;
        }
        if (parent.rightChild == child) {
            parent.rightChild = elementToInsert;
            return;
        }
        for (int i = 0; i < parent.additionalChildren.size(); i++) {
            if (parent.additionalChildren.get(i) == child) {
                parent.additionalChildren.set(i, elementToInsert);
            }
        }
    }

    private boolean matchesTypeAndName(ElementType queriedType, String name2) {
        return this.type == queriedType && this.value.equals(name2);
    }

    public void updateCollisionFormulaToVersion(Project currentProject) {
        String secondSpriteNameFromCollisionFormulaString;
        tryUpdateCollisionFormulaToVersion(this.leftChild, currentProject);
        tryUpdateCollisionFormulaToVersion(this.rightChild, currentProject);
        Iterator<FormulaElement> it = this.additionalChildren.iterator();
        while (it.hasNext()) {
            tryUpdateCollisionFormulaToVersion(it.next(), currentProject);
        }
        if (this.type != ElementType.COLLISION_FORMULA || (secondSpriteNameFromCollisionFormulaString = CollisionDetection.getSecondSpriteNameFromCollisionFormulaString(this.value, currentProject)) == null) {
            return;
        }
        this.value = secondSpriteNameFromCollisionFormulaString;
    }

    private void tryUpdateCollisionFormulaToVersion(FormulaElement element, Project currentProject) {
        if (element != null) {
            element.updateCollisionFormulaToVersion(currentProject);
        }
    }

    public Object interpretRecursive(Scope scope) {
        return FormulaElementOperations.normalizeDegeneratedDoubleValues(rawInterpretRecursive(scope));
    }

    private Object rawInterpretRecursive(Scope scope) {
        PerformanceTracker.formulaEvaluations++;
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[this.type.ordinal()]) {
            case 1:
                if (this.additionalChildren.size() != 0) {
                    List<FormulaElement> list = this.additionalChildren;
                    return list.get(list.size() - 1).interpretRecursive(scope);
                }
                return this.rightChild.interpretRecursive(scope);
            case 2:
                return tryInterpretOperator(scope, this.value);
            case 3:
                return interpretFunction(this.value, scope);
            case 4:
                if (this.cachedUserVariable == null || this.cachedScope != scope) {
                    this.cachedUserVariable = UserDataWrapper.getUserVariable(this.value, scope);
                    this.cachedScope = scope;
                }
                return FormulaElementOperations.interpretUserVariable(this.cachedUserVariable);
            case 5:
                if (this.cachedUserList == null || this.cachedScope != scope) {
                    this.cachedUserList = UserDataWrapper.getUserList(this.value, scope);
                    this.cachedScope = scope;
                }
                return FormulaElementOperations.interpretUserList(this.cachedUserList);
            case 6:
                if (this.cachedBrickInput == null || this.cachedSequence != scope.getSequence()) {
                    this.cachedBrickInput = UserDataWrapper.getUserDefinedBrickInput(this.value, scope.getSequence());
                    this.cachedSequence = scope.getSequence();
                }
                return FormulaElementOperations.interpretUserDefinedBrickInput(this.cachedBrickInput);
            case 7:
                Double d = this.cachedDoubleValue;
                if (d != null) {
                    return d;
                }
                try {
                    Double dValueOf = Double.valueOf(this.value);
                    this.cachedDoubleValue = dValueOf;
                    return dValueOf;
                } catch (NumberFormatException unused) {
                    return this.value;
                }
            case 8:
                ProjectManager projectManager = ProjectManager.getInstance();
                return FormulaElementOperations.interpretSensor(scope.getSprite(), projectManager != null ? projectManager.getCurrentlyEditedScene() : null, projectManager != null ? projectManager.getCurrentProject() : null, this.value);
            case 9:
                return this.value;
            case 10:
                ProjectManager projectManager2 = ProjectManager.getInstance();
                return Double.valueOf(FormulaElementOperations.tryInterpretCollision(scope.getSprite().look, this.value, projectManager2 != null ? projectManager2.getCurrentlyPlayingScene() : null, StageActivity.getActiveStageListener()));
            default:
                return Double.valueOf(0.0d);
        }
    }

    private Object tryInterpretOperator(Scope scope, String value) {
        Operators operatorByValue = Operators.getOperatorByValue(value);
        if (operatorByValue == null) {
            return false;
        }
        return Double.valueOf(interpretOperator(operatorByValue, scope));
    }

    private Object interpretFunction(String name2, Scope scope) {
        Functions functionByValue = Functions.getFunctionByValue(name2);
        if (functionByValue != null) {
            return interpretFunction(functionByValue, scope);
        }
        CustomFormula formulaByUniqueName = CustomFormulaManager.INSTANCE.getFormulaByUniqueName(name2);
        if (formulaByUniqueName != null) {
            ArrayList arrayList = new ArrayList();
            FormulaElement formulaElement = this.leftChild;
            if (formulaElement != null) {
                arrayList.add(tryInterpretRecursive(formulaElement, scope));
            }
            FormulaElement formulaElement2 = this.rightChild;
            if (formulaElement2 != null) {
                arrayList.add(tryInterpretRecursive(formulaElement2, scope));
            }
            for (int i = 0; i < this.additionalChildren.size(); i++) {
                arrayList.add(tryInterpretRecursive(this.additionalChildren.get(i), scope));
            }
            return interpretCustomLunoFunction(formulaByUniqueName, arrayList, scope);
        }
        return Double.valueOf(0.0d);
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
    private Object interpretFunction(Functions functions, Scope scope) {
        PhysicsWorld.RayCastResult rayCastResult;
        PhysicsWorld.RayCastResult rayCastResult2;
        PhysicsWorld.RayCastResult rayCastResult3;
        PhysicsWorld.RayCastResult rayCastResult4;
        Functions functions2 = Functions.IF_THEN_ELSE;
        z = false;
        boolean z = false;
        z = false;
        boolean z2 = false;
        Double dValueOf = Double.valueOf(0.0d);
        if (functions == functions2) {
            Double dConvertArgumentToDouble = Conversions.convertArgumentToDouble(tryInterpretRecursive(this.leftChild, scope));
            if (dConvertArgumentToDouble == null || dConvertArgumentToDouble.isNaN()) {
                return Double.valueOf(Double.NaN);
            }
            Object objTryInterpretRecursive = tryInterpretRecursive(dConvertArgumentToDouble.doubleValue() != 0.0d ? this.rightChild : this.additionalChildren.isEmpty() ? null : this.additionalChildren.get(0), scope);
            if (objTryInterpretRecursive instanceof String) {
                return objTryInterpretRecursive;
            }
            Double dConvertArgumentToDouble2 = Conversions.convertArgumentToDouble(objTryInterpretRecursive);
            return Double.valueOf(dConvertArgumentToDouble2 != null ? dConvertArgumentToDouble2.doubleValue() : 0.0d);
        }
        Object objTryInterpretRecursive2 = tryInterpretRecursive(this.leftChild, scope);
        Object objTryInterpretRecursive3 = tryInterpretRecursive(this.rightChild, scope);
        Object objTryInterpretRecursive4 = this.additionalChildren.isEmpty() ? null : tryInterpretRecursive(this.additionalChildren.get(0), scope);
        boolean z3 = true;
        Object objTryInterpretRecursive5 = this.additionalChildren.size() > 1 ? tryInterpretRecursive(this.additionalChildren.get(1), scope) : null;
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Functions[functions.ordinal()]) {
            case 1:
                return interpretFunctionClamp(objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4);
            case 2:
                return interpretFunctionDistan(objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4, objTryInterpretRecursive5);
            case 3:
                return interpretFunctionLetter(objTryInterpretRecursive2, objTryInterpretRecursive3);
            case 4:
                return interpretFunctionSubtext(objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4);
            case 5:
                try {
                    File file = scope.getProject().getFile(String.valueOf(objTryInterpretRecursive2));
                    if (file == null || !file.exists() || !file.isFile() || !file.canRead()) {
                        z3 = false;
                    }
                    return Boolean.valueOf(z3);
                } catch (Exception unused) {
                    return false;
                }
            case 6:
                return getFileListString(ProjectManager.getInstance().getCurrentProject().getFilesDir());
            case 7:
                return ProjectManager.getInstance().getCurrentProject().getFilesDir().getAbsolutePath();
            case 8:
                try {
                    if (luaGlobals == null) {
                        luaGlobals = JsePlatform.standardGlobals();
                    }
                    String strValueOf = String.valueOf(objTryInterpretRecursive2);
                    Map<String, LuaValue> map = compiledLuaCache;
                    LuaValue luaValueLoad = map.get(strValueOf);
                    if (luaValueLoad == null) {
                        luaValueLoad = luaGlobals.load(strValueOf);
                        map.put(strValueOf, luaValueLoad);
                    }
                    return luaValueLoad.call().tojstring();
                } catch (Throwable th) {
                    Log.e("FormulaEvaluator", "Error during Lua formula execution", th);
                    return "0";
                }
            case 9:
                return Integer.valueOf((int) getFileSize(ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2))));
            case 10:
                Integer numValueOf = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive2));
                return numValueOf != null ? Integer.toHexString(numValueOf.intValue()).toUpperCase() : "0";
            case 11:
                try {
                    return Integer.valueOf(Integer.parseInt(String.valueOf(objTryInterpretRecursive2), 16));
                } catch (NumberFormatException unused2) {
                    return 0;
                }
            case 12:
                Integer numValueOf2 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive2));
                return numValueOf2 == null ? dValueOf : Double.valueOf(Conversions.booleanToDouble(Gdx.input.isButtonPressed(numValueOf2.intValue())));
            case 13:
                Integer numValueOf3 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive2));
                if (numValueOf3 == null || numValueOf3.intValue() <= 0) {
                    return "";
                }
                StringBuilder sb = new StringBuilder(numValueOf3.intValue());
                ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
                for (int i = 0; i < numValueOf3.intValue(); i++) {
                    sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(threadLocalRandomCurrent.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
                }
                return sb.toString();
            case 14:
                return Double.valueOf(MathUtils.cosDeg((float) FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive2)));
            case 15:
                return Double.valueOf(MathUtils.sinDeg((float) FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive2)));
            case 16:
                return Double.valueOf(MathUtils.atan2Deg((float) FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive3), (float) FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive2)));
            case 17:
                ThreeDManager threeDManager = getThreeDManager();
                return Double.valueOf(threeDManager != null ? threeDManager.getVelocity(String.valueOf(objTryInterpretRecursive2)).x : 0.0d);
            case 18:
                ThreeDManager threeDManager2 = getThreeDManager();
                return Double.valueOf(threeDManager2 != null ? threeDManager2.getVelocity(String.valueOf(objTryInterpretRecursive2)).y : 0.0d);
            case 19:
                ThreeDManager threeDManager3 = getThreeDManager();
                return Double.valueOf(threeDManager3 != null ? threeDManager3.getVelocity(String.valueOf(objTryInterpretRecursive2)).z : 0.0d);
            case 20:
                ThreeDManager threeDManager4 = getThreeDManager();
                if (threeDManager4 == null) {
                    return dValueOf;
                }
                Vector3 position = threeDManager4.getPosition(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(position != null ? position.x : 0.0d);
            case 21:
                return Integer.valueOf(MLBridge.nativeArgMax(String.valueOf(objTryInterpretRecursive2)));
            case 22:
                return Fast2DFormulaBridge.isTouched(String.valueOf(objTryInterpretRecursive2));
            case 23:
                Integer numValueOf4 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive3));
                return Fast2DFormulaBridge.isTouched(String.valueOf(objTryInterpretRecursive2), numValueOf4 != null ? numValueOf4.intValue() : 0);
            case 24:
                return Fast2DFormulaBridge.getX(String.valueOf(objTryInterpretRecursive2));
            case 25:
                return Fast2DFormulaBridge.getY(String.valueOf(objTryInterpretRecursive2));
            case 26:
                return Fast2DFormulaBridge.getRotation(String.valueOf(objTryInterpretRecursive2));
            case 27:
                return Fast2DFormulaBridge.getScaleX(String.valueOf(objTryInterpretRecursive2));
            case 28:
                return Fast2DFormulaBridge.getScaleY(String.valueOf(objTryInterpretRecursive2));
            case 29:
                return Fast2DFormulaBridge.getColorR(String.valueOf(objTryInterpretRecursive2));
            case 30:
                return Fast2DFormulaBridge.getColorG(String.valueOf(objTryInterpretRecursive2));
            case 31:
                return Fast2DFormulaBridge.getColorB(String.valueOf(objTryInterpretRecursive2));
            case 32:
                return Fast2DFormulaBridge.getAlpha(String.valueOf(objTryInterpretRecursive2));
            case 33:
                return Fast2DFormulaBridge.getTexture(String.valueOf(objTryInterpretRecursive2));
            case 34:
                return Fast2DFormulaBridge.getCamX();
            case 35:
                return Fast2DFormulaBridge.getCamY();
            case 36:
                return Fast2DFormulaBridge.getCamZoom();
            case 37:
                return MLBridge.nativeGetTensorAsString(String.valueOf(objTryInterpretRecursive2));
            case 38:
                return Integer.valueOf(MLBridge.nativeGetTotalSize(String.valueOf(objTryInterpretRecursive2)));
            case 39:
                Integer numValueOf5 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive3));
                return Float.valueOf(MLBridge.nativeGetTensorValueByIndex(String.valueOf(objTryInterpretRecursive2), numValueOf5 != null ? numValueOf5.intValue() : 0));
            case 40:
                return MLBridge.nativeGetShape(String.valueOf(objTryInterpretRecursive2));
            case 41:
                return Float.valueOf(MLBridge.nativeGetValueND(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3)));
            case 42:
                ThreeDManager threeDManager5 = getThreeDManager();
                return threeDManager5 != null ? threeDManager5.getIntersectionCollisionsList(String.valueOf(objTryInterpretRecursive2)) : dValueOf;
            case 43:
                ThreeDManager threeDManager6 = getThreeDManager();
                return threeDManager6 != null ? threeDManager6.getPhysicsCollisionsList(String.valueOf(objTryInterpretRecursive2)) : dValueOf;
            case 44:
                ThreeDManager threeDManager7 = getThreeDManager();
                return threeDManager7 != null ? String.valueOf(threeDManager7.getDeltaTime()) : ServerAuthenticationConstants.TOKEN_CODE_INVALID;
            case 45:
                ThreeDManager threeDManager8 = getThreeDManager();
                return Double.valueOf(threeDManager8 != null ? Conversions.booleanToDouble(threeDManager8.checkCollision(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3))) : 0.0d);
            case 46:
                ThreeDManager threeDManager9 = getThreeDManager();
                return Double.valueOf(threeDManager9 != null ? Conversions.booleanToDouble(threeDManager9.checkIntersection(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3))) : 0.0d);
            case 47:
                ThreeDManager threeDManager10 = getThreeDManager();
                return Double.valueOf(threeDManager10 != null ? threeDManager10.getCameraRotation().y : 0.0d);
            case 48:
                ThreeDManager threeDManager11 = getThreeDManager();
                return Double.valueOf(threeDManager11 != null ? threeDManager11.getCameraRotation().x : 0.0d);
            case 49:
                ThreeDManager threeDManager12 = getThreeDManager();
                return Double.valueOf(threeDManager12 != null ? threeDManager12.getCameraRotation().z : 0.0d);
            case 50:
                ThreeDManager threeDManager13 = getThreeDManager();
                if (threeDManager13 == null) {
                    return dValueOf;
                }
                Vector3 position2 = threeDManager13.getPosition(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(position2 != null ? position2.y : 0.0d);
            case 51:
                ThreeDManager threeDManager14 = getThreeDManager();
                if (threeDManager14 == null) {
                    return dValueOf;
                }
                Vector3 position3 = threeDManager14.getPosition(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(position3 != null ? position3.z : 0.0d);
            case 52:
                ThreeDManager threeDManager15 = getThreeDManager();
                return Double.valueOf(threeDManager15 != null ? threeDManager15.getCameraPosition().x : 0.0d);
            case 53:
                ThreeDManager threeDManager16 = getThreeDManager();
                return Double.valueOf(threeDManager16 != null ? threeDManager16.getCameraPosition().y : 0.0d);
            case 54:
                ThreeDManager threeDManager17 = getThreeDManager();
                return Double.valueOf(threeDManager17 != null ? threeDManager17.getCameraPosition().z : 0.0d);
            case 55:
                ThreeDManager threeDManager18 = getThreeDManager();
                return Double.valueOf(threeDManager18 != null ? threeDManager18.getCameraDirection().x : 0.0d);
            case 56:
                ThreeDManager threeDManager19 = getThreeDManager();
                return Double.valueOf(threeDManager19 != null ? threeDManager19.getCameraDirection().y : 0.0d);
            case 57:
                ThreeDManager threeDManager20 = getThreeDManager();
                return Double.valueOf(threeDManager20 != null ? threeDManager20.getCameraDirection().z : 0.0d);
            case 58:
                ThreeDManager threeDManager21 = getThreeDManager();
                if (threeDManager21 == null) {
                    return dValueOf;
                }
                Vector3 rotation = threeDManager21.getRotation(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(rotation != null ? rotation.y : 0.0d);
            case 59:
                ThreeDManager threeDManager22 = getThreeDManager();
                if (threeDManager22 == null) {
                    return dValueOf;
                }
                Vector3 rotation2 = threeDManager22.getRotation(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(rotation2 != null ? rotation2.x : 0.0d);
            case 60:
                ThreeDManager threeDManager23 = getThreeDManager();
                if (threeDManager23 == null) {
                    return dValueOf;
                }
                Vector3 rotation3 = threeDManager23.getRotation(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(rotation3 != null ? rotation3.z : 0.0d);
            case 61:
                ThreeDManager threeDManager24 = getThreeDManager();
                if (threeDManager24 == null) {
                    return dValueOf;
                }
                Vector3 scale = threeDManager24.getScale(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(scale != null ? scale.x : 0.0d);
            case 62:
                ThreeDManager threeDManager25 = getThreeDManager();
                if (threeDManager25 == null) {
                    return dValueOf;
                }
                Vector3 scale2 = threeDManager25.getScale(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(scale2 != null ? scale2.y : 0.0d);
            case 63:
                ThreeDManager threeDManager26 = getThreeDManager();
                if (threeDManager26 == null) {
                    return dValueOf;
                }
                Vector3 scale3 = threeDManager26.getScale(String.valueOf(objTryInterpretRecursive2));
                return Double.valueOf(scale3 != null ? scale3.z : 0.0d);
            case 64:
                ThreeDManager threeDManager27 = getThreeDManager();
                if (threeDManager27 == null) {
                    return dValueOf;
                }
                Float distance = threeDManager27.getDistance(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3));
                return Double.valueOf(distance != null ? distance.floatValue() : 0.0d);
            case 65:
                Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
                if (currentlyPlayingScene == null) {
                    return dValueOf;
                }
                PhysicsWorld.RayCastResult rayCastResult5 = currentlyPlayingScene.getPhysicsWorld().getRayCastResult(String.valueOf(objTryInterpretRecursive2));
                if (rayCastResult5 != null && rayCastResult5.hasHit) {
                    z2 = true;
                }
                return Double.valueOf(Conversions.booleanToDouble(z2));
            case 66:
                Scene currentlyPlayingScene2 = ProjectManager.getInstance().getCurrentlyPlayingScene();
                return (currentlyPlayingScene2 == null || (rayCastResult = currentlyPlayingScene2.getPhysicsWorld().getRayCastResult(String.valueOf(objTryInterpretRecursive2))) == null || !rayCastResult.hasHit || rayCastResult.hitSprite == null) ? "" : rayCastResult.hitSprite.getName();
            case 67:
                Scene currentlyPlayingScene3 = ProjectManager.getInstance().getCurrentlyPlayingScene();
                return (currentlyPlayingScene3 == null || (rayCastResult2 = currentlyPlayingScene3.getPhysicsWorld().getRayCastResult(String.valueOf(objTryInterpretRecursive2))) == null || !rayCastResult2.hasHit) ? dValueOf : Double.valueOf(rayCastResult2.hitPoint.x);
            case 68:
                Scene currentlyPlayingScene4 = ProjectManager.getInstance().getCurrentlyPlayingScene();
                return (currentlyPlayingScene4 == null || (rayCastResult3 = currentlyPlayingScene4.getPhysicsWorld().getRayCastResult(String.valueOf(objTryInterpretRecursive2))) == null || !rayCastResult3.hasHit) ? dValueOf : Double.valueOf(rayCastResult3.hitPoint.y);
            case 69:
                Scene currentlyPlayingScene5 = ProjectManager.getInstance().getCurrentlyPlayingScene();
                return (currentlyPlayingScene5 == null || (rayCastResult4 = currentlyPlayingScene5.getPhysicsWorld().getRayCastResult(String.valueOf(objTryInterpretRecursive2))) == null || !rayCastResult4.hasHit) ? dValueOf : Double.valueOf(rayCastResult4.hitPoint.dst(scope.getSprite().look.getX(), scope.getSprite().look.getY()));
            case 70:
                try {
                    File file2 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                    if (file2 != null && file2.exists()) {
                        byte[] bArr = new byte[(int) file2.length()];
                        FileInputStream fileInputStream = new FileInputStream(file2);
                        try {
                            fileInputStream.read(bArr);
                            fileInputStream.close();
                            return Base64.encodeToString(bArr, 2);
                        } catch (Throwable th2) {
                            try {
                                fileInputStream.close();
                                throw th2;
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                                throw th2;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            case 71:
                return NewCatroidHttpManager.INSTANCE.getResponseText(String.valueOf(objTryInterpretRecursive2));
            case 72:
                return Double.valueOf(NewCatroidHttpManager.INSTANCE.getResponseCode(String.valueOf(objTryInterpretRecursive2)));
            case 73:
                return NewCatroidHttpManager.INSTANCE.getResponseHeader(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3));
            case 74:
                return NewCatroidMqttManager.INSTANCE.getLatestMessage(String.valueOf(objTryInterpretRecursive2));
            case 75:
                File file3 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                if (file3 != null && file3.exists()) {
                    try {
                        return file3.length() > 2097152 ? "File is too large" : Gdx.files.absolute(file3.getAbsolutePath()).readString("UTF-8");
                    } catch (Exception unused3) {
                    }
                }
                return "";
            case 76:
                File file4 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                if (file4 != null && file4.exists()) {
                    MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                    try {
                        try {
                            mediaMetadataRetriever.setDataSource(file4.getAbsolutePath());
                            Double dValueOf2 = Double.valueOf(Double.parseDouble(mediaMetadataRetriever.extractMetadata(9)) / 1000.0d);
                            try {
                                mediaMetadataRetriever.release();
                                break;
                            } catch (Exception unused4) {
                            }
                            return dValueOf2;
                        } catch (Exception unused5) {
                        }
                    } catch (Exception unused6) {
                        mediaMetadataRetriever.release();
                        return dValueOf;
                    } catch (Throwable th4) {
                        try {
                            mediaMetadataRetriever.release();
                            break;
                        } catch (Exception unused7) {
                        }
                        throw th4;
                    }
                }
                return dValueOf;
            case 77:
                File file5 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                if (file5 == null || !file5.exists()) {
                    return dValueOf;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file5.getAbsolutePath(), options);
                return Double.valueOf(options.outWidth);
            case 78:
                File file6 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                if (file6 == null || !file6.exists()) {
                    return dValueOf;
                }
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file6.getAbsolutePath(), options2);
                return Double.valueOf(options2.outHeight);
            case 79:
                File file7 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                return (file7 == null || !file7.exists()) ? dValueOf : Double.valueOf(file7.lastModified());
            case 80:
                return interpretFunctionJsonGet(objTryInterpretRecursive2, objTryInterpretRecursive3);
            case 81:
                return interpretFunctionJsonSet(objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4);
            case 82:
                return interpretFunctionJsonIsValid(objTryInterpretRecursive2);
            case 83:
                Integer numValueOf6 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive3));
                return FormulaElement$$ExternalSyntheticBackport0.m(String.valueOf(objTryInterpretRecursive2), numValueOf6 != null ? numValueOf6.intValue() : 0);
            case 84:
                return String.valueOf(objTryInterpretRecursive2).replace(String.valueOf(objTryInterpretRecursive3), String.valueOf(objTryInterpretRecursive4));
            case 85:
                return Boolean.valueOf(String.valueOf(objTryInterpretRecursive2).contains(String.valueOf(objTryInterpretRecursive3)));
            case 86:
                return Integer.valueOf(TableManager.INSTANCE.getTableXSize(String.valueOf(objTryInterpretRecursive2)));
            case 87:
                StageActivity stageActivity = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                return Double.valueOf(stageActivity == null ? 0.0f : stageActivity.getViewX(String.valueOf(objTryInterpretRecursive2)));
            case 88:
                StageActivity stageActivity2 = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                return Double.valueOf(stageActivity2 == null ? 0.0f : stageActivity2.getViewY(String.valueOf(objTryInterpretRecursive2)));
            case 89:
                StageActivity stageActivity3 = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                return Double.valueOf(stageActivity3 != null ? stageActivity3.getViewWidth(String.valueOf(objTryInterpretRecursive2)) : 0);
            case 90:
                StageActivity stageActivity4 = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                return Double.valueOf(stageActivity4 != null ? stageActivity4.getViewHeight(String.valueOf(objTryInterpretRecursive2)) : 0);
            case 91:
                ThreeDManager threeDManager28 = getThreeDManager();
                return Double.valueOf(threeDManager28 != null ? Conversions.booleanToDouble(threeDManager28.getRayDidHit(String.valueOf(objTryInterpretRecursive2))) : 0.0d);
            case 92:
                ThreeDManager threeDManager29 = getThreeDManager();
                return Double.valueOf(threeDManager29 != null ? threeDManager29.getRayHitPointX(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 93:
                ThreeDManager threeDManager30 = getThreeDManager();
                return Double.valueOf(threeDManager30 != null ? threeDManager30.getRayHitPointY(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 94:
                ThreeDManager threeDManager31 = getThreeDManager();
                return Double.valueOf(threeDManager31 != null ? threeDManager31.getRayHitPointZ(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 95:
                ThreeDManager threeDManager32 = getThreeDManager();
                return Double.valueOf(threeDManager32 != null ? threeDManager32.getRayHitNormalX(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 96:
                ThreeDManager threeDManager33 = getThreeDManager();
                return Double.valueOf(threeDManager33 != null ? threeDManager33.getRayHitNormalY(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 97:
                ThreeDManager threeDManager34 = getThreeDManager();
                return Double.valueOf(threeDManager34 != null ? threeDManager34.getRayHitNormalZ(String.valueOf(objTryInterpretRecursive2)) : 0.0d);
            case 98:
                StageActivity stageActivity5 = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                if (stageActivity5 != null && stageActivity5.isVideoPlaying(String.valueOf(objTryInterpretRecursive2))) {
                    z = true;
                }
                return Boolean.valueOf(z);
            case 99:
                StageActivity stageActivity6 = StageActivity.activeStageActivity != null ? StageActivity.activeStageActivity.get() : null;
                return Float.valueOf(stageActivity6 != null ? stageActivity6.getVideoCurrentTime(String.valueOf(objTryInterpretRecursive2)) : 0.0f);
            case 100:
                ThreeDManager threeDManager35 = getThreeDManager();
                return Double.valueOf(threeDManager35 != null ? threeDManager35.getRaycastDistance(String.valueOf(objTryInterpretRecursive2)) : -1.0d);
            case 101:
                ThreeDManager threeDManager36 = getThreeDManager();
                return threeDManager36 != null ? threeDManager36.getRaycastHitObjectId(String.valueOf(objTryInterpretRecursive2)) : "";
            case 102:
                StageListener activeStageListener = StageActivity.getActiveStageListener();
                return Double.valueOf(activeStageListener != null ? activeStageListener.getCameraPositionX() : 0.0d);
            case 103:
                StageListener activeStageListener2 = StageActivity.getActiveStageListener();
                return Double.valueOf(activeStageListener2 != null ? activeStageListener2.getCameraPositionY() : 0.0d);
            case 104:
                StageListener activeStageListener3 = StageActivity.getActiveStageListener();
                return Double.valueOf(activeStageListener3 != null ? activeStageListener3.getCameraRotation() : 0.0d);
            case 105:
                StageListener activeStageListener4 = StageActivity.getActiveStageListener();
                return Double.valueOf(activeStageListener4 != null ? activeStageListener4.getCameraZoom() : 1.0d);
            case 106:
                String strValueOf2 = String.valueOf(objTryInterpretRecursive2);
                int iFloor = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive3));
                int iFloor2 = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive4));
                int iFloor3 = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive5));
                VoxelManager.VoxelBuffer buffer = VoxelManager.INSTANCE.getBuffer(strValueOf2);
                return Double.valueOf(buffer != null ? buffer.get(iFloor, iFloor2, iFloor3) : 0.0d);
            case 107:
                String strValueOf3 = String.valueOf(objTryInterpretRecursive2);
                int iFloor4 = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive3));
                int iFloor5 = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive4));
                int iFloor6 = (int) Math.floor(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive5));
                VoxelManager.VoxelBuffer buffer2 = VoxelManager.INSTANCE.getBuffer(strValueOf3);
                return Double.valueOf(buffer2 != null ? buffer2.getData(iFloor4, iFloor5, iFloor6) : (byte) 0);
            case 108:
                return Integer.valueOf(FloatArrayManager.INSTANCE.getArraySize(String.valueOf(objTryInterpretRecursive2)));
            case 109:
                return Integer.valueOf(TableManager.INSTANCE.getTableYSize(String.valueOf(objTryInterpretRecursive2)));
            case 110:
                Integer numValueOf7 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive3));
                Integer numValueOf8 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive4));
                return TableManager.INSTANCE.getElementValue(String.valueOf(objTryInterpretRecursive2), numValueOf7 != null ? numValueOf7.intValue() : 0, numValueOf8 != null ? numValueOf8.intValue() : 0);
            case 111:
                return TableManager.INSTANCE.tableToString(String.valueOf(objTryInterpretRecursive2), String.valueOf(objTryInterpretRecursive3), String.valueOf(objTryInterpretRecursive4));
            case 112:
                return String.valueOf(objTryInterpretRecursive2).toUpperCase();
            case 113:
                return String.valueOf(objTryInterpretRecursive2).toLowerCase();
            case 114:
                return new StringBuilder(String.valueOf(objTryInterpretRecursive2)).reverse().toString();
            case 115:
                return UserVarsManager.INSTANCE.getVar(String.valueOf(objTryInterpretRecursive2));
            case 116:
                Integer numValueOf9 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive2));
                return UserVarsManager.INSTANCE.getVarName(numValueOf9 != null ? numValueOf9.intValue() : -1);
            case 117:
                Integer numValueOf10 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(objTryInterpretRecursive2));
                return UserVarsManager.INSTANCE.getVarValue(numValueOf10 != null ? numValueOf10.intValue() : -1);
            case 118:
                return interpretFunctionLength(objTryInterpretRecursive2, scope);
            case 119:
                return interpretFunctionJoin(scope, this.leftChild, this.rightChild);
            case 120:
                return interpretFunctionJoin3(scope, this.leftChild, this.rightChild, this.additionalChildren);
            case 121:
                return HashUtils.hashString(String.valueOf(objTryInterpretRecursive2), "MD5");
            case 122:
                return HashUtils.hashString(String.valueOf(objTryInterpretRecursive2), McElieceCCA2KeyGenParameterSpec.SHA256);
            case 123:
                File file8 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                return (file8 == null || !file8.exists()) ? "" : HashUtils.hashFile(file8, "MD5");
            case 124:
                File file9 = ProjectManager.getInstance().getCurrentProject().getFile(String.valueOf(objTryInterpretRecursive2));
                return (file9 == null || !file9.exists()) ? "" : HashUtils.hashFile(file9, McElieceCCA2KeyGenParameterSpec.SHA256);
            case 125:
                return interpretFunctionDistance(scope, objTryInterpretRecursive2, objTryInterpretRecursive3);
            case 126:
                return NewCatroidNotificationManager.INSTANCE.getSavedReplies().getOrDefault(NewCatroidNotificationManager.cleanStringId(String.valueOf(objTryInterpretRecursive2)), "");
            case 127:
                return interpretFunctionJoinNumber(scope, this.leftChild, this.rightChild);
            case 128:
                return tryInterpretFunctionRegex(scope, this.leftChild, this.rightChild);
            case 129:
                return interpretFunctionListItem(objTryInterpretRecursive2, scope);
            case 130:
                return interpretFunctionContains(objTryInterpretRecursive3, scope);
            case 131:
                return interpretFunctionNumberOfItems(objTryInterpretRecursive2, scope);
            case 132:
                return interpretFunctionIndexOfItem(objTryInterpretRecursive2, scope);
            case 133:
                return interpretFunctionFlatten(scope, this.leftChild);
            case 134:
                return interpretFunctionConnect(objTryInterpretRecursive3, scope);
            case 135:
                return interpretFunctionFind(objTryInterpretRecursive3, scope);
            case 136:
                return Double.valueOf(Conversions.booleanToDouble(new ColorCollisionDetection(scope, StageActivity.getActiveStageListener()).tryInterpretFunctionTouchesColor(objTryInterpretRecursive2)));
            case 137:
                return Double.valueOf(Conversions.booleanToDouble(new ColorCollisionDetection(scope, StageActivity.getActiveStageListener()).tryInterpretFunctionColorTouchesColor(objTryInterpretRecursive2, objTryInterpretRecursive3)));
            case 138:
                return new ColorAtXYDetection(scope, StageActivity.getActiveStageListener()).tryInterpretFunctionColorAtXY(objTryInterpretRecursive2, objTryInterpretRecursive3);
            case 139:
                StageListener activeStageListener5 = StageActivity.getActiveStageListener();
                return activeStageListener5 == null ? dValueOf : Double.valueOf(FormulaElementOperations.tryInterpretCollision(scope.getSprite().look, String.valueOf(objTryInterpretRecursive2), ProjectManager.getInstance().getCurrentlyPlayingScene(), activeStageListener5));
            case 140:
                return this.textBlockFunctionProvider.interpretFunctionTextBlock(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive2));
            case 141:
                return this.textBlockFunctionProvider.interpretFunctionTextBlockLanguage(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretRecursive2));
            case 142:
                return Double.valueOf(Conversions.booleanToDouble(new ColorEqualsColor().tryInterpretFunctionColorEqualsColor(objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4)));
            default:
                return interpretFormulaFunction(functions, objTryInterpretRecursive2, objTryInterpretRecursive3, objTryInterpretRecursive4);
        }
    }

    private Object interpretCustomLunoFunction(CustomFormula customFormula, List<Object> arguments, Scope scope) {
        Object objLunoValueToString;
        LoadedLibrary loadedLibrary = LibraryManager.INSTANCE.getLoadedLibrary(customFormula.getOwnerLibraryId());
        if (loadedLibrary == null) {
            Log.e(TAG_FORMULA_ELEMENT, "Библиотека " + customFormula.getOwnerLibraryId() + " не загружена для функции " + customFormula.getUniqueName());
            return "LIB NOT FOUND";
        }
        Interpreter interpreter = loadedLibrary.getInterpreter();
        try {
            LunoValue lunoValue = interpreter.getGlobals().get(new Token(TokenType.IDENTIFIER, customFormula.getLunoFunctionName(), null, -1, -1));
            if (!(lunoValue instanceof LunoValue.Callable)) {
                Log.e(TAG_FORMULA_ELEMENT, "Функция " + customFormula.getLunoFunctionName() + " не найдена или не является функцией в библиотеке " + loadedLibrary.getId());
                return "FUNC NOT FOUND";
            }
            LunoValue.Callable callable = (LunoValue.Callable) lunoValue;
            ArrayList arrayList = new ArrayList();
            Iterator<Object> it = arguments.iterator();
            while (it.hasNext()) {
                arrayList.add(LunoValue.INSTANCE.fromKotlin(it.next()));
            }
            LunoValue lunoValueCall = callable.call(interpreter, arrayList, new Token(TokenType.EOF, "", null, -1, -1));
            if (lunoValueCall instanceof LunoValue.Number) {
                objLunoValueToString = Double.valueOf(((LunoValue.Number) lunoValueCall).getValue());
            } else if (lunoValueCall instanceof LunoValue.String) {
                objLunoValueToString = ((LunoValue.String) lunoValueCall).getValue();
            } else if (lunoValueCall instanceof LunoValue.Boolean) {
                objLunoValueToString = Double.valueOf(((LunoValue.Boolean) lunoValueCall).getValue() ? 1.0d : 0.0d);
            } else {
                objLunoValueToString = interpreter.lunoValueToString(lunoValueCall, true);
            }
            Log.d("LunoFormulaLibs", "Функция " + customFormula.getLunoFunctionName() + " вернула LunoValue: " + lunoValueCall.toString() + ", конвертировано в Java: " + objLunoValueToString.toString());
            return objLunoValueToString;
        } catch (LunoRuntimeError e) {
            Log.e(TAG_FORMULA_ELEMENT, "Ошибка выполнения LunoScript для функции " + customFormula.getUniqueName(), e);
            return "LUNO ERROR";
        }
    }

    private Object tryInterpretRecursive(FormulaElement element, Scope scope) {
        if (element == null) {
            return null;
        }
        return element.interpretRecursive(scope);
    }

    private Object interpretFunctionIfThenElseObject(Double condition, Object thenValue, Object elseValue) {
        if (Double.isNaN(condition.doubleValue())) {
            return Double.valueOf(Double.NaN);
        }
        return condition.doubleValue() != 0.0d ? thenValue : elseValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionIfThenElse(double condition, double thenValue, double elseValue) {
        if (Double.isNaN(condition)) {
            return Double.NaN;
        }
        return condition != 0.0d ? thenValue : elseValue;
    }

    private Object interpretFormulaFunction(Functions function, Object arg0, Object arg1, Object arg2) {
        FormulaFunction formulaFunction = this.formulaFunctions.get(function);
        if (formulaFunction == null) {
            return Double.valueOf(0.0d);
        }
        Double dConvertArgumentToDouble = Conversions.convertArgumentToDouble(arg0);
        Double dConvertArgumentToDouble2 = Conversions.convertArgumentToDouble(arg1);
        double dDoubleValue = dConvertArgumentToDouble != null ? dConvertArgumentToDouble.doubleValue() : 0.0d;
        double dDoubleValue2 = dConvertArgumentToDouble2 != null ? dConvertArgumentToDouble2.doubleValue() : 0.0d;
        if (arg2 == null) {
            return formulaFunction.execute(Double.valueOf(dDoubleValue), Double.valueOf(dDoubleValue2));
        }
        Double dConvertArgumentToDouble3 = Conversions.convertArgumentToDouble(arg2);
        return formulaFunction.execute(Double.valueOf(dDoubleValue), Double.valueOf(dDoubleValue2), Double.valueOf(dConvertArgumentToDouble3 != null ? dConvertArgumentToDouble3.doubleValue() : 0.0d));
    }

    private Object interpretFunctionNumberOfItems(Object left, Scope scope) {
        if (this.leftChild.type == ElementType.USER_LIST) {
            return Double.valueOf(handleNumberOfItemsOfUserListParameter(UserDataWrapper.getUserList(this.leftChild.value, scope)));
        }
        return interpretFunctionLength(left, scope);
    }

    private int handleNumberOfItemsOfUserListParameter(UserList userList) {
        if (userList == null) {
            return 0;
        }
        return userList.getValue().size();
    }

    private Object interpretFunctionContains(Object right, Scope scope) {
        UserList userListOfChild = getUserListOfChild(this.leftChild, scope);
        Double dValueOf = Double.valueOf(0.0d);
        if (userListOfChild == null) {
            return dValueOf;
        }
        Iterator<Object> it = userListOfChild.getValue().iterator();
        while (it.hasNext()) {
            if (FormulaElementOperations.interpretOperatorEqual(it.next(), right)) {
                return Double.valueOf(1.0d);
            }
        }
        return dValueOf;
    }

    private Object interpretFunctionIndexOfItem(Object left, Scope scope) {
        if (this.rightChild.getElementType() == ElementType.USER_LIST) {
            return Double.valueOf(UserDataWrapper.getUserList(this.rightChild.value, scope).getIndexOf(left) + 1);
        }
        return Double.valueOf(0.0d);
    }

    private Object interpretFunctionListItem(Object left, Scope scope) {
        UserList userListOfChild;
        Integer numValueOf;
        int iIntValue;
        if (left == null || (userListOfChild = getUserListOfChild(this.rightChild, scope)) == null || (numValueOf = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(left))) == null || (iIntValue = numValueOf.intValue() - 1) < 0 || iIntValue >= userListOfChild.getValue().size()) {
            return "";
        }
        return userListOfChild.getValue().get(iIntValue);
    }

    private Object interpretFunctionConnect(Object right, Scope scope) {
        UserList userListOfChild = getUserListOfChild(this.leftChild, scope);
        if (userListOfChild == null) {
            return "";
        }
        List<Object> value = userListOfChild.getValue();
        StringBuilder sb = new StringBuilder();
        String strValueOf = String.valueOf(right);
        for (Object obj : value) {
            if (obj != null) {
                sb.append(String.valueOf(obj)).append(strValueOf);
            }
        }
        return sb.toString();
    }

    private Object interpretFunctionFind(Object right, Scope scope) {
        UserList userListOfChild = getUserListOfChild(this.leftChild, scope);
        if (userListOfChild == null) {
            return null;
        }
        List<Object> value = userListOfChild.getValue();
        for (int i = 0; i < value.size(); i++) {
            Object obj = value.get(i);
            if (obj != null && String.valueOf(obj).equals(String.valueOf(right))) {
                return Integer.valueOf(i + 1);
            }
        }
        return "";
    }

    private UserList getUserListOfChild(FormulaElement child, Scope scope) {
        if (child.getElementType() != ElementType.USER_LIST) {
            return null;
        }
        return UserDataWrapper.getUserList(child.value, scope);
    }

    private static String interpretFunctionJoin(Scope scope, FormulaElement leftChild, FormulaElement rightChild) {
        return interpretFunctionString(leftChild, scope).concat(interpretFunctionString(rightChild, scope));
    }

    private static String interpretFunctionJoinNumber(Scope scope, FormulaElement leftChild, FormulaElement rightChild) {
        return interpretFunctionString(leftChild, scope) + interpretFunctionString(rightChild, scope);
    }

    private static String interpretFunctionJoin3(Scope scope, FormulaElement leftChild, FormulaElement rightChild, List<FormulaElement> additionalChildren) {
        return interpretFunctionString(leftChild, scope).concat(interpretFunctionString(rightChild, scope).concat(interpretFunctionString(additionalChildren.get(0), scope)));
    }

    private static String interpretFunctionFlatten(Scope scope, FormulaElement leftChild) {
        return interpretFunctionString(leftChild, scope);
    }

    private static String tryInterpretFunctionRegex(Scope scope, FormulaElement leftChild, FormulaElement rightChild) {
        try {
            return interpretFunctionRegex(interpretFunctionString(leftChild, scope), interpretFunctionString(rightChild, scope));
        } catch (IllegalArgumentException e) {
            return e.getLocalizedMessage();
        }
    }

    private static String interpretFunctionRegex(String patternString, String matcherString) {
        Matcher matcher = Pattern.compile(patternString, 40).matcher(matcherString);
        if (matcher.find()) {
            return matcher.group(matcher.groupCount() == 0 ? 0 : 1);
        }
        return "";
    }

    private static String interpretFunctionString(FormulaElement child, Scope scope) {
        String value;
        if (child == null) {
            return "";
        }
        Object objInterpretRecursive = child.interpretRecursive(scope);
        Project project = scope != null ? scope.getProject() : null;
        if (project == null && ProjectManager.getInstance() != null) {
            project = ProjectManager.getInstance().getCurrentProject();
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[child.getElementType().ordinal()];
        if (i == 7) {
            if (project != null) {
                try {
                    return project.formatValue(Double.valueOf(child.getValue()));
                } catch (Exception unused) {
                    return formatNumberString((String) objInterpretRecursive);
                }
            }
            return formatNumberString((String) objInterpretRecursive);
        }
        if (i == 9) {
            return child.getValue();
        }
        if (project != null) {
            value = project.formatValue(objInterpretRecursive);
        } else {
            value = "" + objInterpretRecursive;
        }
        return NumberFormats.trimTrailingCharacters(value);
    }

    private static String formatNumberString(String numberString) {
        double d = Double.parseDouble(numberString);
        String string = "";
        if (!Double.isNaN(d)) {
            StringBuilder sb = new StringBuilder("");
            if (FormulaElementOperations.isInteger(d)) {
                d = (int) d;
            }
            string = sb.append(d).toString();
        }
        return NumberFormats.trimTrailingCharacters(string);
    }

    private Object interpretFunctionLength(Object left, Scope scope) {
        FormulaElement formulaElement = this.leftChild;
        Double dValueOf = Double.valueOf(0.0d);
        if (formulaElement == null) {
            return dValueOf;
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[this.leftChild.type.ordinal()];
        if (i == 4) {
            return Double.valueOf(calculateUserVariableLength(UserDataWrapper.getUserVariable(this.leftChild.value, scope)));
        }
        if (i == 5) {
            return Double.valueOf(calculateUserListLength(UserDataWrapper.getUserList(this.leftChild.value, scope), left, scope));
        }
        if (i == 7 || i == 9) {
            return Double.valueOf(this.leftChild.value.length());
        }
        return ((left instanceof Double) && ((Double) left).isNaN()) ? dValueOf : Double.valueOf(String.valueOf(left).length());
    }

    private int calculateUserVariableLength(UserVariable userVariable) {
        Object value = userVariable.getValue();
        if (value instanceof String) {
            return String.valueOf(value).length();
        }
        if (value.toString().equals("true") || value.toString().equals("false")) {
            return 1;
        }
        Double d = (Double) value;
        if (FormulaElementOperations.isInteger(d.doubleValue())) {
            return Integer.toString(d.intValue()).length();
        }
        return Double.toString(d.doubleValue()).length();
    }

    private double calculateUserListLength(UserList userList, Object left, Scope scope) {
        int length;
        if (userList == null || userList.getValue().isEmpty()) {
            return 0.0d;
        }
        Object objInterpretRecursive = this.leftChild.interpretRecursive(scope);
        if (objInterpretRecursive instanceof Double) {
            Double d = (Double) objInterpretRecursive;
            if (d.isNaN() || d.isInfinite()) {
                return 0.0d;
            }
            length = String.valueOf(d.intValue()).length();
        } else if (objInterpretRecursive instanceof String) {
            length = ((String) objInterpretRecursive).length();
        } else {
            if ((left instanceof Double) && ((Double) left).isNaN()) {
                return 0.0d;
            }
            length = String.valueOf(left).length();
        }
        return length;
    }

    private Object interpretFunctionLetter(Object left, Object right) {
        Integer numValueOf;
        if (left == null || right == null || (numValueOf = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(left))) == null) {
            return "";
        }
        int iIntValue = numValueOf.intValue() - 1;
        String strValueOf = String.valueOf(right);
        if (iIntValue >= 0 && iIntValue < strValueOf.length()) {
            return String.valueOf(strValueOf.charAt(iIntValue));
        }
        return "";
    }

    public static int levenshtain(String str1, String str2) {
        int length = str1.length();
        int length2 = str2.length();
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length + 1, length2 + 1);
        for (int i = 0; i <= length; i++) {
            iArr[i][0] = i;
        }
        for (int i2 = 0; i2 <= length2; i2++) {
            iArr[0][i2] = i2;
        }
        for (int i3 = 1; i3 <= length; i3++) {
            for (int i4 = 1; i4 <= length2; i4++) {
                int i5 = i3 - 1;
                int i6 = i4 - 1;
                int i7 = str1.charAt(i5) == str2.charAt(i6) ? 0 : 1;
                int[] iArr2 = iArr[i3];
                iArr2[i4] = Math.min(Math.min(iArr[i5][i4] + 1, iArr2[i6] + 1), iArr[i5][i6] + i7);
            }
        }
        return iArr[length][length2];
    }

    private Object interpretFunctionDistance(Scope scope, Object val1, Object val2) {
        return Integer.valueOf(levenshtain(val1.toString(), val2.toString()));
    }

    private Object interpretFunctionJsonIsValid(Object arg) {
        try {
            parseJsonRoot(String.valueOf(arg));
            return Double.valueOf(1.0d);
        } catch (Exception unused) {
            return Double.valueOf(0.0d);
        }
    }

    private Object interpretFunctionJsonGet(Object jsonArg, Object pathArg) {
        String strValueOf = String.valueOf(jsonArg);
        String strValueOf2 = String.valueOf(pathArg);
        try {
            Object jsonRoot = parseJsonRoot(strValueOf);
            List<String> pathTokens = parsePathTokens(strValueOf2);
            for (int i = 0; i < pathTokens.size(); i++) {
                String str = pathTokens.get(i);
                if (jsonRoot != null && jsonRoot != JSONObject.NULL) {
                    if (jsonRoot instanceof JSONObject) {
                        JSONObject jSONObject = (JSONObject) jsonRoot;
                        if (i == pathTokens.size() - 1) {
                            return sanitizeResult(jSONObject.opt(str));
                        }
                        jsonRoot = jSONObject.opt(str);
                    } else if (jsonRoot instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) jsonRoot;
                        try {
                            int i2 = Integer.parseInt(str);
                            if (i2 >= 0 && i2 < jSONArray.length()) {
                                if (i == pathTokens.size() - 1) {
                                    return sanitizeResult(jSONArray.opt(i2));
                                }
                                jsonRoot = jSONArray.opt(i2);
                            }
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
                return "";
            }
            return sanitizeResult(jsonRoot);
        } catch (Exception unused2) {
            return "";
        }
    }

    private Object interpretFunctionJsonSet(Object jsonArg, Object pathArg, Object valueArg) {
        Object objOpt;
        Object jSONArray;
        String strValueOf = String.valueOf(jsonArg);
        String strValueOf2 = String.valueOf(pathArg);
        try {
            Object jsonRoot = parseJsonRoot(strValueOf);
            List<String> pathTokens = parsePathTokens(strValueOf2);
            if (pathTokens.isEmpty()) {
                return strValueOf;
            }
            int i = 0;
            Object obj = jsonRoot;
            while (i < pathTokens.size() - 1) {
                String str = pathTokens.get(i);
                i++;
                boolean zIsStringInteger = isStringInteger(pathTokens.get(i));
                if (obj instanceof JSONObject) {
                    JSONObject jSONObject = (JSONObject) obj;
                    objOpt = jSONObject.opt(str);
                    if (objOpt != null && objOpt != JSONObject.NULL) {
                        obj = objOpt;
                    }
                    jSONArray = zIsStringInteger ? new JSONArray() : new JSONObject();
                    jSONObject.put(str, jSONArray);
                    obj = jSONArray;
                } else if (obj instanceof JSONArray) {
                    JSONArray jSONArray2 = (JSONArray) obj;
                    int i2 = Integer.parseInt(str);
                    while (jSONArray2.length() <= i2) {
                        jSONArray2.put(JSONObject.NULL);
                    }
                    objOpt = jSONArray2.opt(i2);
                    if (objOpt == null || objOpt == JSONObject.NULL) {
                        jSONArray = zIsStringInteger ? new JSONArray() : new JSONObject();
                        jSONArray2.put(i2, jSONArray);
                        obj = jSONArray;
                    } else {
                        obj = objOpt;
                    }
                }
            }
            String str2 = pathTokens.get(pathTokens.size() - 1);
            Object value = parseValue(valueArg);
            if (obj instanceof JSONObject) {
                ((JSONObject) obj).put(str2, value);
            } else if (obj instanceof JSONArray) {
                JSONArray jSONArray3 = (JSONArray) obj;
                int i3 = Integer.parseInt(str2);
                while (jSONArray3.length() <= i3) {
                    jSONArray3.put(JSONObject.NULL);
                }
                jSONArray3.put(i3, value);
            }
            return jsonRoot.toString();
        } catch (Exception unused) {
            return strValueOf;
        }
    }

    private boolean isStringInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private Object parseJsonRoot(String jsonString) throws JSONException {
        String strTrim = jsonString.trim();
        if (strTrim.startsWith("[")) {
            return new JSONArray(strTrim);
        }
        return new JSONObject(strTrim);
    }

    private List<String> parsePathTokens(String path) {
        ArrayList arrayList = new ArrayList();
        for (String str : path.replace("[", ".").replace("]", "").split("\\.")) {
            if (str != null && !str.trim().isEmpty()) {
                arrayList.add(str.trim());
            }
        }
        return arrayList;
    }

    private Object sanitizeResult(Object result) {
        if (result == null || result == JSONObject.NULL) {
            return "";
        }
        return ((result instanceof JSONObject) || (result instanceof JSONArray)) ? result.toString() : result;
    }

    private Object parseValue(Object valueArg) {
        if (valueArg == null) {
            return JSONObject.NULL;
        }
        String strValueOf = String.valueOf(valueArg);
        if ("true".equalsIgnoreCase(strValueOf)) {
            return true;
        }
        if ("false".equalsIgnoreCase(strValueOf)) {
            return false;
        }
        try {
            String strTrim = strValueOf.trim();
            if (strTrim.startsWith("{")) {
                return new JSONObject(strTrim);
            }
            if (strTrim.startsWith("[")) {
                return new JSONArray(strTrim);
            }
            try {
                if (strValueOf.contains(".")) {
                    return Double.valueOf(Double.parseDouble(strValueOf));
                }
                return Integer.valueOf(Integer.parseInt(strValueOf));
            } catch (NumberFormatException unused) {
                return valueArg;
            }
        } catch (Exception unused2) {
        }
    }

    private Object interpretFunctionSubtext(Object leftArg, Object rightArg, Object stringObj) {
        if (leftArg != null && rightArg != null) {
            Integer numValueOf = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(leftArg));
            Integer numValueOf2 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(rightArg));
            if (numValueOf != null && numValueOf2 != null) {
                int iIntValue = numValueOf.intValue() - 1;
                int iIntValue2 = numValueOf2.intValue();
                String strValueOf = String.valueOf(stringObj);
                if (iIntValue >= 0 && iIntValue2 >= 0 && iIntValue <= iIntValue2 && iIntValue2 <= strValueOf.length()) {
                    return strValueOf.substring(iIntValue, iIntValue2);
                }
            }
        }
        return "";
    }

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2.0d) + Math.pow(y2 - y1, 2.0d));
    }

    private Object interpretFunctionDistan(Object x1, Object y1, Object x2, Object y2) {
        return Double.valueOf(calculateDistance(FormulaElementOperations.tryInterpretDoubleValue(x1), FormulaElementOperations.tryInterpretDoubleValue(y1), FormulaElementOperations.tryInterpretDoubleValue(x2), FormulaElementOperations.tryInterpretDoubleValue(y2)));
    }

    private Object interpretFunctionClamp(Object valueArg, Object leftArg, Object rightArg) {
        if (leftArg != null && rightArg != null) {
            Integer numValueOf = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(leftArg));
            Integer numValueOf2 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(rightArg));
            Integer numValueOf3 = Integer.valueOf(FormulaElementOperations.tryParseIntFromObject(valueArg));
            if (numValueOf != null && numValueOf2 != null && numValueOf3 != null) {
                int iIntValue = numValueOf.intValue();
                int iIntValue2 = numValueOf2.intValue();
                int iIntValue3 = numValueOf3.intValue();
                if (iIntValue3 >= iIntValue) {
                    iIntValue = iIntValue3;
                }
                if (iIntValue <= iIntValue2) {
                    iIntValue2 = iIntValue;
                }
                return Integer.valueOf(iIntValue2);
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionRand(double from, double to) {
        double dMin = Math.min(from, to);
        double dMax = Math.max(from, to);
        if (dMin == dMax) {
            return dMin;
        }
        if (FormulaElementOperations.isInteger(dMin) && FormulaElementOperations.isInteger(dMax) && !isNumberWithDecimalPoint(this.leftChild) && !isNumberWithDecimalPoint(this.rightChild)) {
            return Math.floor(Math.random() * ((dMax + 1.0d) - dMin)) + dMin;
        }
        return (Math.random() * (dMax - dMin)) + dMin;
    }

    private static boolean isNumberWithDecimalPoint(FormulaElement element) {
        return element != null && element.type == ElementType.NUMBER && element.value.contains(".");
    }

    private double interpretOperator(Operators operator, Scope scope) {
        if (this.leftChild != null) {
            return interpretBinaryOperator(operator, scope);
        }
        return interpretUnaryOperator(operator, scope);
    }

    private double interpretUnaryOperator(Operators operator, Scope scope) {
        double dTryInterpretDoubleValue = FormulaElementOperations.tryInterpretDoubleValue(FormulaElementOperations.tryInterpretElementRecursive(this.rightChild, scope));
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Operators[operator.ordinal()];
        if (i == 1) {
            return -dTryInterpretDoubleValue;
        }
        if (i != 2) {
            return 0.0d;
        }
        return Conversions.booleanToDouble(dTryInterpretDoubleValue == 0.0d);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaElement$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.formulaeditor.FormulaElement$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Functions;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$formulaeditor$Operators;

        static {
            int[] iArr = new int[Operators.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Operators = iArr;
            try {
                iArr[Operators.MINUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.LOGICAL_NOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.PLUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.MULT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.DIVIDE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.POW.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.EQUAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.NOT_EQUAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.GREATER_THAN.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.GREATER_OR_EQUAL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.SMALLER_THAN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.SMALLER_OR_EQUAL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.LOGICAL_AND.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Operators[Operators.LOGICAL_OR.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr2 = new int[Functions.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$Functions = iArr2;
            try {
                iArr2[Functions.CLAMP.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.DISTAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.LETTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.SUBTEXT.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILES_PATH.ordinal()] = 6;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.ALL_FILES.ordinal()] = 7;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.LUA.ordinal()] = 8;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE_SIZE.ordinal()] = 9;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TO_HEX.ordinal()] = 10;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TO_DEC.ordinal()] = 11;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.IS_MOUSE_BUTTON_DOWN.ordinal()] = 12;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RANDOM_STR.ordinal()] = 13;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_DIRECTION_X.ordinal()] = 14;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_DIRECTION_Y.ordinal()] = 15;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_ANGLE.ordinal()] = 16;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_VELOCITY_X.ordinal()] = 17;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_VELOCITY_Y.ordinal()] = 18;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_VELOCITY_Z.ordinal()] = 19;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_POSITION_X.ordinal()] = 20;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_ARGMAX.ordinal()] = 21;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_IS_TOUCHED.ordinal()] = 22;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_IS_TOUCHED_INDEX.ordinal()] = 23;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_X.ordinal()] = 24;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_Y.ordinal()] = 25;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_ROTATION.ordinal()] = 26;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_SCALE_X.ordinal()] = 27;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_SCALE_Y.ordinal()] = 28;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_COLOR_R.ordinal()] = 29;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_COLOR_G.ordinal()] = 30;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_COLOR_B.ordinal()] = 31;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_ALPHA.ordinal()] = 32;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_TEXTURE.ordinal()] = 33;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_CAM_X.ordinal()] = 34;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_CAM_Y.ordinal()] = 35;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.F2D_CAM_ZOOM.ordinal()] = 36;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_DUMP.ordinal()] = 37;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_TOTALSIZE.ordinal()] = 38;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_VALUE.ordinal()] = 39;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_SHAPE.ordinal()] = 40;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.PT_VALUEND.ordinal()] = 41;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.INTERSECT_LIST.ordinal()] = 42;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.COLLISION_LIST.ordinal()] = 43;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.DELTA.ordinal()] = 44;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.OBJECT_TOUCHES_OBJECT.ordinal()] = 45;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.OBJECT_INTERSECTS_OBJECT.ordinal()] = 46;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_ROTATION_YAW.ordinal()] = 47;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_ROTATION_PITCH.ordinal()] = 48;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_ROTATION_ROLL.ordinal()] = 49;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_POSITION_Y.ordinal()] = 50;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_POSITION_Z.ordinal()] = 51;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_POS_X.ordinal()] = 52;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_POS_Y.ordinal()] = 53;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_POS_Z.ordinal()] = 54;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_DIR_X.ordinal()] = 55;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_DIR_Y.ordinal()] = 56;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_CAMERA_DIR_Z.ordinal()] = 57;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_ROTATION_YAW.ordinal()] = 58;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_ROTATION_PITCH.ordinal()] = 59;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_ROTATION_ROLL.ordinal()] = 60;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_SCALE_X.ordinal()] = 61;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_SCALE_Y.ordinal()] = 62;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_SCALE_Z.ordinal()] = 63;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_3D_DISTANCE.ordinal()] = 64;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_DID_HIT2.ordinal()] = 65;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_HIT_SPRITE_NAME.ordinal()] = 66;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_HIT_X.ordinal()] = 67;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_HIT_Y.ordinal()] = 68;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_HIT_DISTANCE.ordinal()] = 69;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE_TO_BASE64.ordinal()] = 70;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.HTTP_RESPONSE_TEXT.ordinal()] = 71;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.HTTP_RESPONSE_CODE.ordinal()] = 72;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.HTTP_RESPONSE_HEADER.ordinal()] = 73;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.MQTT_MESSAGE.ordinal()] = 74;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.READ_FILE.ordinal()] = 75;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.MEDIA_DURATION.ordinal()] = 76;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.IMAGE_WIDTH.ordinal()] = 77;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.IMAGE_HEIGHT.ordinal()] = 78;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE_LAST_MODIFIED.ordinal()] = 79;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JSON_GET.ordinal()] = 80;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JSON_SET.ordinal()] = 81;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JSON_IS_VALID.ordinal()] = 82;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.REPEAT.ordinal()] = 83;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.REPLACE.ordinal()] = 84;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CONTAINS_STR.ordinal()] = 85;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TABLE_X.ordinal()] = 86;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIEW_X.ordinal()] = 87;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIEW_Y.ordinal()] = 88;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIEW_WIDTH.ordinal()] = 89;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIEW_HEIGHT.ordinal()] = 90;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.RAY_DID_HIT.ordinal()] = 91;
            } catch (NoSuchFieldError unused105) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_X.ordinal()] = 92;
            } catch (NoSuchFieldError unused106) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_Y.ordinal()] = 93;
            } catch (NoSuchFieldError unused107) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_Z.ordinal()] = 94;
            } catch (NoSuchFieldError unused108) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_NORMAL_X.ordinal()] = 95;
            } catch (NoSuchFieldError unused109) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_NORMAL_Y.ordinal()] = 96;
            } catch (NoSuchFieldError unused110) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_NORMAL_Z.ordinal()] = 97;
            } catch (NoSuchFieldError unused111) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIDEO_PLAYING.ordinal()] = 98;
            } catch (NoSuchFieldError unused112) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VIDEO_TIME.ordinal()] = 99;
            } catch (NoSuchFieldError unused113) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_DISTANCE.ordinal()] = 100;
            } catch (NoSuchFieldError unused114) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.GET_RAY_HIT_OBJECT.ordinal()] = 101;
            } catch (NoSuchFieldError unused115) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CAMERA_X.ordinal()] = 102;
            } catch (NoSuchFieldError unused116) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CAMERA_Y.ordinal()] = 103;
            } catch (NoSuchFieldError unused117) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CAMERA_ROTATION.ordinal()] = 104;
            } catch (NoSuchFieldError unused118) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CAMERA_ZOOM.ordinal()] = 105;
            } catch (NoSuchFieldError unused119) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VOXEL_GET_ID.ordinal()] = 106;
            } catch (NoSuchFieldError unused120) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VOXEL_GET_DATA.ordinal()] = 107;
            } catch (NoSuchFieldError unused121) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FLOATARRAY.ordinal()] = 108;
            } catch (NoSuchFieldError unused122) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TABLE_Y.ordinal()] = 109;
            } catch (NoSuchFieldError unused123) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TABLE_ELEMENT.ordinal()] = 110;
            } catch (NoSuchFieldError unused124) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TABLE_JOIN.ordinal()] = 111;
            } catch (NoSuchFieldError unused125) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.UPPER.ordinal()] = 112;
            } catch (NoSuchFieldError unused126) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.LOWER.ordinal()] = 113;
            } catch (NoSuchFieldError unused127) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.REVERSE.ordinal()] = 114;
            } catch (NoSuchFieldError unused128) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VAR.ordinal()] = 115;
            } catch (NoSuchFieldError unused129) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VARNAME.ordinal()] = 116;
            } catch (NoSuchFieldError unused130) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.VARVALUE.ordinal()] = 117;
            } catch (NoSuchFieldError unused131) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.LENGTH.ordinal()] = 118;
            } catch (NoSuchFieldError unused132) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JOIN.ordinal()] = 119;
            } catch (NoSuchFieldError unused133) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JOIN3.ordinal()] = 120;
            } catch (NoSuchFieldError unused134) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.MD5.ordinal()] = 121;
            } catch (NoSuchFieldError unused135) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.SHA256.ordinal()] = 122;
            } catch (NoSuchFieldError unused136) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE_MD5.ordinal()] = 123;
            } catch (NoSuchFieldError unused137) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FILE_SHA256.ordinal()] = 124;
            } catch (NoSuchFieldError unused138) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.DISTANCE.ordinal()] = 125;
            } catch (NoSuchFieldError unused139) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.NOTIFICATION_REPLY.ordinal()] = 126;
            } catch (NoSuchFieldError unused140) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.JOINNUMBER.ordinal()] = 127;
            } catch (NoSuchFieldError unused141) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.REGEX.ordinal()] = 128;
            } catch (NoSuchFieldError unused142) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.LIST_ITEM.ordinal()] = 129;
            } catch (NoSuchFieldError unused143) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CONTAINS.ordinal()] = 130;
            } catch (NoSuchFieldError unused144) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.NUMBER_OF_ITEMS.ordinal()] = 131;
            } catch (NoSuchFieldError unused145) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.INDEX_OF_ITEM.ordinal()] = 132;
            } catch (NoSuchFieldError unused146) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FLATTEN.ordinal()] = 133;
            } catch (NoSuchFieldError unused147) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.CONNECT.ordinal()] = 134;
            } catch (NoSuchFieldError unused148) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.FIND.ordinal()] = 135;
            } catch (NoSuchFieldError unused149) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.COLLIDES_WITH_COLOR.ordinal()] = 136;
            } catch (NoSuchFieldError unused150) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.COLOR_TOUCHES_COLOR.ordinal()] = 137;
            } catch (NoSuchFieldError unused151) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.COLOR_AT_XY.ordinal()] = 138;
            } catch (NoSuchFieldError unused152) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TOUCHES_OBJECT_BY_NAME.ordinal()] = 139;
            } catch (NoSuchFieldError unused153) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TEXT_BLOCK_FROM_CAMERA.ordinal()] = 140;
            } catch (NoSuchFieldError unused154) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.TEXT_BLOCK_LANGUAGE_FROM_CAMERA.ordinal()] = 141;
            } catch (NoSuchFieldError unused155) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$Functions[Functions.COLOR_EQUALS_COLOR.ordinal()] = 142;
            } catch (NoSuchFieldError unused156) {
            }
            int[] iArr3 = new int[ElementType.values().length];
            $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType = iArr3;
            try {
                iArr3[ElementType.BRACKET.ordinal()] = 1;
            } catch (NoSuchFieldError unused157) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.OPERATOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused158) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.FUNCTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused159) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.USER_VARIABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused160) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.USER_LIST.ordinal()] = 5;
            } catch (NoSuchFieldError unused161) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.USER_DEFINED_BRICK_INPUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused162) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.NUMBER.ordinal()] = 7;
            } catch (NoSuchFieldError unused163) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.SENSOR.ordinal()] = 8;
            } catch (NoSuchFieldError unused164) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused165) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[ElementType.COLLISION_FORMULA.ordinal()] = 10;
            } catch (NoSuchFieldError unused166) {
            }
        }
    }

    private double interpretBinaryOperator(Operators operator, Scope scope) {
        Object objTryInterpretElementRecursive = FormulaElementOperations.tryInterpretElementRecursive(this.leftChild, scope);
        Object objTryInterpretElementRecursive2 = FormulaElementOperations.tryInterpretElementRecursive(this.rightChild, scope);
        Double dValueOf = Double.valueOf(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretElementRecursive));
        Double dValueOf2 = Double.valueOf(FormulaElementOperations.tryInterpretDoubleValue(objTryInterpretElementRecursive2));
        double dDoubleValue = dValueOf != null ? dValueOf.doubleValue() : 0.0d;
        double dDoubleValue2 = dValueOf2 != null ? dValueOf2.doubleValue() : 0.0d;
        boolean z = false;
        boolean z2 = Double.isNaN(dDoubleValue) || Double.isNaN(dDoubleValue2);
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$Operators[operator.ordinal()]) {
            case 1:
                if (z2) {
                    return Double.NaN;
                }
                return dDoubleValue - dDoubleValue2;
            case 2:
            default:
                return 0.0d;
            case 3:
                if (z2) {
                    return Double.NaN;
                }
                return dDoubleValue + dDoubleValue2;
            case 4:
                if (z2) {
                    return Double.NaN;
                }
                return dDoubleValue * dDoubleValue2;
            case 5:
                if (z2 || dDoubleValue2 == 0.0d) {
                    return Double.NaN;
                }
                return dDoubleValue / dDoubleValue2;
            case 6:
                if (z2) {
                    return Double.NaN;
                }
                return Math.pow(dDoubleValue, dDoubleValue2);
            case 7:
                return Conversions.booleanToDouble(FormulaElementOperations.interpretOperatorEqual(objTryInterpretElementRecursive, objTryInterpretElementRecursive2));
            case 8:
                return Conversions.booleanToDouble(!FormulaElementOperations.interpretOperatorEqual(objTryInterpretElementRecursive, objTryInterpretElementRecursive2));
            case 9:
                return Conversions.booleanToDouble(dDoubleValue > dDoubleValue2);
            case 10:
                return Conversions.booleanToDouble(dDoubleValue >= dDoubleValue2);
            case 11:
                return Conversions.booleanToDouble(dDoubleValue < dDoubleValue2);
            case 12:
                return Conversions.booleanToDouble(dDoubleValue <= dDoubleValue2);
            case 13:
                if (dDoubleValue != 0.0d && dDoubleValue2 != 0.0d) {
                    z = true;
                }
                return Conversions.booleanToDouble(z);
            case 14:
                return Conversions.booleanToDouble((dDoubleValue == 0.0d && dDoubleValue2 == 0.0d) ? false : true);
        }
    }

    public FormulaElement getParent() {
        return this.parent;
    }

    public void setRightChild(FormulaElement rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            rightChild.parent = this;
        }
    }

    public void setLeftChild(FormulaElement leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            leftChild.parent = this;
        }
    }

    public void addAdditionalChild(FormulaElement child) {
        this.additionalChildren.add(child);
        if (child != null) {
            child.parent = this;
        }
    }

    public void replaceElement(FormulaElement current) {
        this.parent = current.parent;
        this.leftChild = current.leftChild;
        this.rightChild = current.rightChild;
        for (int i = 0; i < current.additionalChildren.size(); i++) {
            if (i < this.additionalChildren.size()) {
                this.additionalChildren.set(i, current.additionalChildren.get(i));
            } else {
                this.additionalChildren.add(current.additionalChildren.get(i));
            }
        }
        this.value = current.value;
        this.type = current.type;
        FormulaElement formulaElement = this.leftChild;
        if (formulaElement != null) {
            formulaElement.parent = this;
        }
        FormulaElement formulaElement2 = this.rightChild;
        if (formulaElement2 != null) {
            formulaElement2.parent = this;
        }
        for (FormulaElement formulaElement3 : this.additionalChildren) {
            if (formulaElement3 != null) {
                formulaElement3.parent = this;
            }
        }
    }

    public void replaceElement(ElementType type, String value) {
        this.value = value;
        this.type = type;
    }

    public void replaceWithSubElement(String operator, FormulaElement rightChild) {
        FormulaElement formulaElement = new FormulaElement(ElementType.OPERATOR, operator, getParent(), this, rightChild);
        formulaElement.parent.rightChild = formulaElement;
    }

    public boolean isBoolean(Scope scope) {
        if (this.type == ElementType.USER_VARIABLE) {
            return isUserVariableBoolean(scope);
        }
        if (this.type == ElementType.USER_LIST) {
            return isUserListBoolean(scope);
        }
        if (this.type == ElementType.USER_DEFINED_BRICK_INPUT) {
            return isUserDefinedBrickInputBoolean(scope);
        }
        return isOtherBooleanFormulaElement();
    }

    private boolean isUserVariableBoolean(Scope scope) {
        UserVariable userVariable = UserDataWrapper.getUserVariable(this.value, scope);
        return userVariable != null && (userVariable.getValue() instanceof Boolean);
    }

    private boolean isUserListBoolean(Scope scope) {
        List<Object> value = UserDataWrapper.getUserList(this.value, scope).getValue();
        if (value.size() != 1) {
            return false;
        }
        return value.get(0) instanceof Boolean;
    }

    private boolean isUserDefinedBrickInputBoolean(Scope scope) {
        UserData userDefinedBrickInput = UserDataWrapper.getUserDefinedBrickInput(this.value, scope.getSequence());
        if (userDefinedBrickInput == null || !(userDefinedBrickInput.getValue() instanceof Formula)) {
            return false;
        }
        return ((Formula) userDefinedBrickInput.getValue()).getRoot().isBoolean(scope);
    }

    private boolean isOtherBooleanFormulaElement() {
        return (this.type == ElementType.FUNCTION && Functions.isBoolean(Functions.getFunctionByValue(this.value))) || (this.type == ElementType.SENSOR && Sensors.isBoolean(Sensors.getSensorByValue(this.value))) || ((this.type == ElementType.OPERATOR && Operators.getOperatorByValue(this.value).isLogicalOperator) || this.type == ElementType.COLLISION_FORMULA);
    }

    public boolean containsElement(ElementType elementType) {
        FormulaElement formulaElement;
        FormulaElement formulaElement2;
        if (this.type.equals(elementType) || (((formulaElement = this.leftChild) != null && formulaElement.containsElement(elementType)) || ((formulaElement2 = this.rightChild) != null && formulaElement2.containsElement(elementType)))) {
            return true;
        }
        for (FormulaElement formulaElement3 : this.additionalChildren) {
            if (formulaElement3 != null && formulaElement3.containsElement(elementType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNumber() {
        if (this.type == ElementType.OPERATOR) {
            return Operators.getOperatorByValue(this.value) == Operators.MINUS && this.leftChild == null && this.rightChild.isNumber();
        }
        return this.type == ElementType.NUMBER;
    }

    public FormulaElement clone() {
        FormulaElement formulaElementTryCloneElement = tryCloneElement(this.leftChild);
        FormulaElement formulaElementTryCloneElement2 = tryCloneElement(this.rightChild);
        ArrayList arrayList = new ArrayList();
        Iterator<FormulaElement> it = this.additionalChildren.iterator();
        while (it.hasNext()) {
            arrayList.add(tryCloneElement(it.next()));
        }
        String str = this.value;
        if (str == null) {
            str = "";
        }
        return new FormulaElement(this.type, str, null, formulaElementTryCloneElement, formulaElementTryCloneElement2, arrayList);
    }

    private FormulaElement tryCloneElement(FormulaElement element) {
        if (element == null) {
            return null;
        }
        return element.clone();
    }

    public void addRequiredResources(final Set<Integer> requiredResourcesSet) {
        tryAddRequiredResources(requiredResourcesSet, this.leftChild);
        tryAddRequiredResources(requiredResourcesSet, this.rightChild);
        Iterator<FormulaElement> it = this.additionalChildren.iterator();
        while (it.hasNext()) {
            tryAddRequiredResources(requiredResourcesSet, it.next());
        }
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$formulaeditor$FormulaElement$ElementType[this.type.ordinal()];
        if (i == 3) {
            FormulaElementResources.addFunctionResources(requiredResourcesSet, Functions.getFunctionByValue(this.value));
        } else if (i == 8) {
            FormulaElementResources.addSensorsResources(requiredResourcesSet, Sensors.getSensorByValue(this.value));
        } else {
            if (i != 10) {
                return;
            }
            requiredResourcesSet.add(19);
        }
    }

    private void tryAddRequiredResources(Set<Integer> resourceSet, FormulaElement element) {
        if (element != null) {
            element.addRequiredResources(resourceSet);
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getUserDataRecursive(ElementType type) {
        ArrayList arrayList = new ArrayList();
        if (this.type == type) {
            arrayList.add(this.value);
        }
        FormulaElement formulaElement = this.leftChild;
        if (formulaElement != null) {
            arrayList.addAll(formulaElement.getUserDataRecursive(type));
        }
        FormulaElement formulaElement2 = this.rightChild;
        if (formulaElement2 != null) {
            arrayList.addAll(formulaElement2.getUserDataRecursive(type));
        }
        for (FormulaElement formulaElement3 : this.additionalChildren) {
            if (formulaElement3 != null) {
                arrayList.addAll(formulaElement3.getUserDataRecursive(type));
            }
        }
        return arrayList;
    }

    public String getFileListString(File directory) {
        File[] fileArrListFiles;
        if (directory == null || !directory.isDirectory() || (fileArrListFiles = directory.listFiles()) == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (File file : fileArrListFiles) {
            sb.append(file.getName()).append("\n");
        }
        return sb.toString();
    }

    private ThreeDManager getThreeDManager() {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener != null) {
            return activeStageListener.getThreeDManager();
        }
        return null;
    }

    public long getFileSize(File file) {
        if (file != null && file.exists() && file.isFile()) {
            return file.length();
        }
        return 0L;
    }
}
