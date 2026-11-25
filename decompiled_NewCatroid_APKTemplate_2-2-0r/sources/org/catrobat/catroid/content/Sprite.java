package org.catrobat.catroid.content;

import android.content.Context;
import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.embroidery.RunningStitch;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.plot.Plot;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Sprite.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamFieldKeyOrder({"spriteId", "name", "lookList", "soundList", "scriptList", "nfcTagList", "userVariables", "userLists", "userDefinedBrickList"})
public class Sprite implements Nameable, Serializable {
    private static final String TAG = "Sprite";
    private static final long serialVersionUID = 1;
    private transient ActionFactory actionFactory;
    private transient Set<ConditionScriptTrigger> conditionScriptTriggers;
    private transient boolean convertToGroupItemSprite;
    private transient boolean convertToSprite;
    private transient Color embroideryThreadColor;
    private transient float glidingVelocityX;
    private transient float glidingVelocityY;
    private transient Multimap<EventId, ScriptSequenceAction> idToEventThreadMap;
    public transient boolean isClone;
    private transient boolean isGliding;
    public transient Look look;
    private List<LookData> lookList;
    public transient boolean movedByStepsBrick;
    public transient Sprite myOriginal;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    @XStreamAsAttribute
    private String f519name;
    private List<NfcTagData> nfcTagList;
    public transient PenConfiguration penConfiguration;
    public transient Plot plot;
    public transient RunningStitch runningStitch;
    private transient String runtimeName;
    private List<Script> scriptList;
    private List<SoundInfo> soundList;

    @XStreamAsAttribute
    private String spriteId;
    private transient List<Integer> usedTouchPointer;
    private List<Brick> userDefinedBrickList;
    private List<UserList> userLists;
    private List<UserVariable> userVariables;
    private transient HashMap<String, UserVariable> variableCache;

    public Sprite() {
        this.look = new Look(this);
        this.penConfiguration = new PenConfiguration();
        this.runningStitch = new RunningStitch();
        this.plot = new Plot();
        this.convertToSprite = false;
        this.convertToGroupItemSprite = false;
        this.idToEventThreadMap = LinkedHashMultimap.create();
        this.conditionScriptTriggers = new HashSet();
        this.usedTouchPointer = new ArrayList();
        this.embroideryThreadColor = Color.BLACK;
        this.variableCache = new HashMap<>();
        this.scriptList = new ArrayList();
        this.lookList = new ArrayList();
        this.soundList = new ArrayList();
        this.nfcTagList = new ArrayList();
        this.userVariables = new ArrayList();
        this.userLists = new ArrayList();
        this.userDefinedBrickList = new ArrayList();
        this.actionFactory = new ActionFactory();
        this.isClone = false;
        this.myOriginal = null;
        this.movedByStepsBrick = false;
        this.isGliding = false;
        this.glidingVelocityX = 0.0f;
        this.glidingVelocityY = 0.0f;
    }

    public Sprite(String name2) {
        this.look = new Look(this);
        this.penConfiguration = new PenConfiguration();
        this.runningStitch = new RunningStitch();
        this.plot = new Plot();
        this.convertToSprite = false;
        this.convertToGroupItemSprite = false;
        this.idToEventThreadMap = LinkedHashMultimap.create();
        this.conditionScriptTriggers = new HashSet();
        this.usedTouchPointer = new ArrayList();
        this.embroideryThreadColor = Color.BLACK;
        this.variableCache = new HashMap<>();
        this.scriptList = new ArrayList();
        this.lookList = new ArrayList();
        this.soundList = new ArrayList();
        this.nfcTagList = new ArrayList();
        this.userVariables = new ArrayList();
        this.userLists = new ArrayList();
        this.userDefinedBrickList = new ArrayList();
        this.actionFactory = new ActionFactory();
        this.isClone = false;
        this.myOriginal = null;
        this.movedByStepsBrick = false;
        this.isGliding = false;
        this.glidingVelocityX = 0.0f;
        this.glidingVelocityY = 0.0f;
        this.f519name = name2;
        this.spriteId = UUID.randomUUID().toString();
        this.runtimeName = name2;
    }

    public String getSpriteId() {
        return this.spriteId;
    }

    public void setSpriteId(String spriteId) {
        this.spriteId = spriteId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.spriteId, ((Sprite) o).spriteId);
    }

    public int hashCode() {
        return Objects.hash(this.spriteId);
    }

    public List<Script> getScriptList() {
        return this.scriptList;
    }

    public List<Brick> getAllBricks() {
        ArrayList arrayList = new ArrayList();
        for (Script script : this.scriptList) {
            arrayList.add(script.getScriptBrick());
            arrayList.addAll(script.getBrickList());
        }
        return arrayList;
    }

    public List<Brick> getUserDefinedBrickList() {
        return this.userDefinedBrickList;
    }

    public UserDefinedBrick getUserDefinedBrickWithSameUserData(UserDefinedBrick userDefinedBrick) {
        if (userDefinedBrick == null) {
            return null;
        }
        Iterator<Brick> it = this.userDefinedBrickList.iterator();
        while (it.hasNext()) {
            UserDefinedBrick userDefinedBrick2 = (UserDefinedBrick) it.next();
            if (userDefinedBrick2.isUserDefinedBrickDataEqual(userDefinedBrick)) {
                return userDefinedBrick2;
            }
        }
        return null;
    }

    public UserDefinedBrick getUserDefinedBrickByID(UUID userDefinedBrickID) {
        Iterator<Brick> it = this.userDefinedBrickList.iterator();
        while (it.hasNext()) {
            UserDefinedBrick userDefinedBrick = (UserDefinedBrick) it.next();
            if (userDefinedBrick.getUserDefinedBrickID().equals(userDefinedBrickID)) {
                return userDefinedBrick;
            }
        }
        return null;
    }

    public boolean containsUserDefinedBrickWithSameUserData(UserDefinedBrick userDefinedBrick) {
        return getUserDefinedBrickWithSameUserData(userDefinedBrick) != null;
    }

    public void addUserDefinedBrick(UserDefinedBrick userDefinedBrick) {
        this.userDefinedBrickList.add(userDefinedBrick);
    }

    public void removeUserDefinedBrick(UserDefinedBrick userDefinedBrick) {
        for (Script script : this.scriptList) {
            script.removeAllOccurrencesOfUserDefinedBrick(script.brickList, userDefinedBrick);
        }
        this.userDefinedBrickList.remove(userDefinedBrick);
    }

    public void addClonesOfUserDefinedBrickList(List<UserDefinedBrick> userDefinedBricks) {
        for (UserDefinedBrick userDefinedBrick : userDefinedBricks) {
            if (!containsUserDefinedBrickWithSameUserData(userDefinedBrick)) {
                try {
                    addUserDefinedBrick((UserDefinedBrick) userDefinedBrick.clone());
                } catch (CloneNotSupportedException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    public <T> boolean hasUserDataChanged(List<T> newUserData, List<T> oldUserData) {
        if (newUserData.size() != oldUserData.size()) {
            return true;
        }
        Iterator<T> it = newUserData.iterator();
        while (it.hasNext()) {
            if (!checkUserData(it.next(), oldUserData)) {
                return true;
            }
        }
        return false;
    }

    public String getRuntimeName() {
        String str = this.runtimeName;
        if (str != null && !str.isEmpty()) {
            return this.runtimeName;
        }
        return this.f519name;
    }

    public void setRuntimeName(String name2) {
        this.runtimeName = name2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> boolean checkUserData(T t, List<T> list) {
        for (T t2 : list) {
            if (t2.equals(t)) {
                if (t2.getClass() == UserVariable.class) {
                    return ((UserVariable) t2).hasSameValue((UserVariable) t);
                }
                return ((UserList) t2).hasSameListSize((UserList) t);
            }
        }
        return false;
    }

    public <T> void restoreUserDataValues(List<T> list, List<T> list2) {
        for (T t : list) {
            for (T t2 : list2) {
                if (t.getClass() == UserVariable.class) {
                    UserVariable userVariable = (UserVariable) t;
                    UserVariable userVariable2 = (UserVariable) t2;
                    if (userVariable.getName().equals(userVariable2.getName())) {
                        userVariable.setValue(userVariable2.getValue());
                    }
                } else {
                    UserList userList = (UserList) t;
                    UserList userList2 = (UserList) t2;
                    if (userList.getName().equals(userList2.getName())) {
                        userList.setValue(userList2.getValue());
                    }
                }
            }
        }
    }

    public List<UserVariable> getUserVariablesCopy() {
        if (this.userVariables == null) {
            this.userVariables = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserVariable userVariable : this.userVariables) {
            arrayList.add(new UserVariable(userVariable.getName(), userVariable.getValue()));
        }
        return arrayList;
    }

    public List<UserVariable> getUserVariables() {
        return this.userVariables;
    }

    public UserVariable getUserVariable(String name2) {
        UserVariable userVariable = this.variableCache.get(name2);
        if (userVariable != null) {
            return userVariable;
        }
        for (UserVariable userVariable2 : this.userVariables) {
            if (userVariable2.getName().equals(name2)) {
                this.variableCache.put(name2, userVariable2);
                return userVariable2;
            }
        }
        return null;
    }

    public boolean addUserVariable(UserVariable userVariable) {
        this.variableCache.put(userVariable.getName(), userVariable);
        return this.userVariables.add(userVariable);
    }

    public List<UserList> getUserListsCopy() {
        if (this.userLists == null) {
            this.userLists = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (UserList userList : this.userLists) {
            arrayList.add(new UserList(userList.getName(), userList.getValue()));
        }
        return arrayList;
    }

    public List<UserList> getUserLists() {
        return this.userLists;
    }

    public UserList getUserList(String name2) {
        for (UserList userList : this.userLists) {
            if (userList.getName().equals(name2)) {
                return userList;
            }
        }
        return null;
    }

    public boolean addUserList(UserList userList) {
        return this.userLists.add(userList);
    }

    public List<PlaySoundBrick> getPlaySoundBricks() {
        ArrayList arrayList = new ArrayList();
        for (Brick brick : getAllBricks()) {
            if (brick instanceof PlaySoundBrick) {
                arrayList.add((PlaySoundBrick) brick);
            }
        }
        return arrayList;
    }

    public void resetUserData() {
        this.variableCache.clear();
        Iterator<UserVariable> it = this.userVariables.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        Iterator<UserList> it2 = this.userLists.iterator();
        while (it2.hasNext()) {
            it2.next().reset();
        }
    }

    public void resetSprite() {
        Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
        addRequiredResources(resourcesSet);
        if (resourcesSet.contains(3)) {
            this.look = new PhysicsLook(this, ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld());
        } else {
            this.look = new Look(this);
        }
        Iterator<LookData> it = this.lookList.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        if (!getLookList().isEmpty()) {
            this.look.setLookData(getLookList().get(0));
        }
        this.variableCache.clear();
        this.penConfiguration = new PenConfiguration();
        this.plot = new Plot();
        this.runningStitch = new RunningStitch();
    }

    public void invalidate() {
        this.idToEventThreadMap = null;
        this.conditionScriptTriggers = null;
        this.penConfiguration = null;
        this.plot = null;
        this.runningStitch = null;
    }

    public static void resolveSpriteReferences(Sprite sprite, Project project) {
        if (sprite == null || project == null) {
            return;
        }
        Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap());
        for (Script script : sprite.getScriptList()) {
            if (script != null) {
                ArrayList arrayList = new ArrayList();
                script.addToFlatList(arrayList);
                if (script.getScriptBrick() != null) {
                    arrayList.add(script.getScriptBrick());
                }
                Iterator<Brick> it = arrayList.iterator();
                while (it.hasNext()) {
                    resolveReferencesRecursive(it.next(), sprite, project, setNewSetFromMap);
                }
            }
        }
    }

    private static void resolveReferencesRecursive(Object obj, Sprite sprite, Project project, Set<Object> visited) {
        if (obj == null || visited.contains(obj)) {
            return;
        }
        visited.add(obj);
        for (Class<?> superclass = obj.getClass(); superclass != null && !superclass.getName().startsWith("java.") && !superclass.getName().startsWith("android."); superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object obj2 = field.get(obj);
                    if (obj2 != null) {
                        if (obj2 instanceof UserVariable) {
                            field.set(obj, findOrCreateUserVariable((UserVariable) obj2, sprite, project));
                        } else if (obj2 instanceof UserList) {
                            field.set(obj, findOrCreateUserList((UserList) obj2, sprite, project));
                        } else if (obj2 instanceof Iterable) {
                            for (Object obj3 : (Iterable) obj2) {
                                if (obj3 != null) {
                                    if ((obj3 instanceof UserVariable) || (obj3 instanceof UserList)) {
                                        resolveReferencesRecursive(obj, sprite, project, visited);
                                    } else {
                                        resolveReferencesRecursive(obj3, sprite, project, visited);
                                    }
                                }
                            }
                        } else if (obj2.getClass().getName().startsWith("org.catrobat.catroid")) {
                            resolveReferencesRecursive(obj2, sprite, project, visited);
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    private static UserVariable findOrCreateUserVariable(UserVariable oldVar, Sprite sprite, Project project) {
        for (UserVariable userVariable : sprite.getUserVariables()) {
            if (userVariable.getName().equals(oldVar.getName())) {
                return userVariable;
            }
        }
        for (UserVariable userVariable2 : project.getUserVariables()) {
            if (userVariable2.getName().equals(oldVar.getName())) {
                return userVariable2;
            }
        }
        for (UserVariable userVariable3 : project.getMultiplayerVariables()) {
            if (userVariable3.getName().equals(oldVar.getName())) {
                return userVariable3;
            }
        }
        sprite.addUserVariable(oldVar);
        return oldVar;
    }

    private static UserList findOrCreateUserList(UserList oldList, Sprite sprite, Project project) {
        for (UserList userList : sprite.getUserLists()) {
            if (userList.getName().equals(oldList.getName())) {
                return userList;
            }
        }
        for (UserList userList2 : project.getUserLists()) {
            if (userList2.getName().equals(oldList.getName())) {
                return userList2;
            }
        }
        sprite.addUserList(oldList);
        return oldList;
    }

    public void initConditionScriptTriggers() {
        this.conditionScriptTriggers.clear();
        for (Script script : this.scriptList) {
            if (script instanceof WhenConditionScript) {
                ConditionScriptTrigger conditionScriptTrigger = new ConditionScriptTrigger(((WhenConditionBrick) script.getScriptBrick()).getFormulaWithBrickField(Brick.BrickField.IF_CONDITION));
                conditionScriptTrigger.updateSceneFirstStart();
                this.conditionScriptTriggers.add(conditionScriptTrigger);
            }
        }
    }

    public void resetConditionScriptTriggers() {
        Iterator<ConditionScriptTrigger> it = this.conditionScriptTriggers.iterator();
        while (it.hasNext()) {
            it.next().resetStartTimeIfSceneRestarted();
        }
    }

    public void initIfConditionBrickTriggers() {
        for (Script script : this.scriptList) {
            if (!script.getBrickList().isEmpty()) {
                Brick brick = script.getBrickList().get(0);
                if (brick instanceof IfThenLogicBeginBrick) {
                    for (Formula formula : ((IfThenLogicBeginBrick) brick).getFormulas()) {
                        if (waitBeforeCheckingCollision(formula)) {
                            formula.sceneFirstStart(true);
                        }
                    }
                } else if (brick instanceof IfLogicBeginBrick) {
                    for (Formula formula2 : ((IfLogicBeginBrick) brick).getFormulas()) {
                        if (waitBeforeCheckingCollision(formula2)) {
                            formula2.sceneFirstStart(true);
                        }
                    }
                }
            }
        }
    }

    boolean waitBeforeCheckingCollision(Formula formula) {
        return formula.getRoot() != null && formula.getRoot().getElementType() == FormulaElement.ElementType.COLLISION_FORMULA;
    }

    void evaluateConditionScriptTriggers() {
        Iterator<ConditionScriptTrigger> it = this.conditionScriptTriggers.iterator();
        while (it.hasNext()) {
            it.next().evaluateAndTriggerActions(this);
        }
    }

    public void initializeEventThreads(int startType) {
        this.idToEventThreadMap.clear();
        Iterator<Script> it = this.scriptList.iterator();
        while (it.hasNext()) {
            createThreadAndAddToEventMap(it.next());
        }
        this.look.fire(new EventWrapper(new EventId(startType), false));
    }

    private void createThreadAndAddToEventMap(Script script) {
        if (script.isCommentedOut()) {
            return;
        }
        if (this.isClone && (script instanceof StartScript)) {
            return;
        }
        this.idToEventThreadMap.put(script.createEventId(this), createSequenceAction(script));
    }

    public ActionFactory getActionFactory() {
        return this.actionFactory;
    }

    public void setActionFactory(ActionFactory actionFactory) {
        this.actionFactory = actionFactory;
    }

    public Sprite convert() {
        Sprite groupItemSprite;
        if (this.convertToSprite) {
            groupItemSprite = new Sprite(this.f519name);
        } else if (this.convertToGroupItemSprite) {
            groupItemSprite = new GroupItemSprite(this.f519name);
        } else {
            Log.d(TAG, "Nothing to convert: if this is not what you wanted have a look at the convert flags.");
            return this;
        }
        Look look = new Look(groupItemSprite);
        groupItemSprite.look = look;
        look.setLookData(this.look.getLookData());
        groupItemSprite.penConfiguration = this.penConfiguration;
        groupItemSprite.plot = this.plot;
        groupItemSprite.runningStitch = this.runningStitch;
        groupItemSprite.lookList = this.lookList;
        groupItemSprite.soundList = this.soundList;
        groupItemSprite.nfcTagList = this.nfcTagList;
        groupItemSprite.scriptList = this.scriptList;
        groupItemSprite.userVariables = this.userVariables;
        groupItemSprite.userLists = this.userLists;
        groupItemSprite.userDefinedBrickList = this.userDefinedBrickList;
        return groupItemSprite;
    }

    public ScriptSequenceAction createSequenceAction(Script script) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(script);
        if (!script.getClass().equals(UserDefinedScript.class)) {
            script.run(this, scriptSequenceAction);
        }
        return scriptSequenceAction;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public String getName() {
        return this.f519name;
    }

    @Override // org.catrobat.catroid.common.Nameable
    public void setName(String name2) {
        this.f519name = name2;
    }

    public void addScript(Script script) {
        if (script == null || this.scriptList.contains(script)) {
            return;
        }
        this.scriptList.add(script);
    }

    public void prependScript(Script script) {
        if (script == null || this.scriptList.contains(script)) {
            return;
        }
        this.scriptList.add(0, script);
    }

    public void addScript(int index, Script script) {
        if (script == null || this.scriptList.contains(script)) {
            return;
        }
        this.scriptList.add(index, script);
    }

    public Script getScript(int index) {
        if (index < 0 || index >= this.scriptList.size()) {
            Log.e(TAG, "getScript() Index out of Scope! scriptList size: " + this.scriptList.size());
            return null;
        }
        return this.scriptList.get(index);
    }

    public int getNumberOfScripts() {
        return this.scriptList.size();
    }

    public int getNumberOfBricks() {
        ArrayList arrayList = new ArrayList();
        Iterator<Script> it = this.scriptList.iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(arrayList);
        }
        return arrayList.size() - this.scriptList.size();
    }

    public int getScriptIndex(Script script) {
        return this.scriptList.indexOf(script);
    }

    public void removeAllScripts() {
        this.scriptList.clear();
    }

    public boolean removeScript(Script script) {
        return this.scriptList.remove(script);
    }

    public List<LookData> getLookList() {
        return this.lookList;
    }

    public List<SoundInfo> getSoundList() {
        return this.soundList;
    }

    public void addRequiredResources(final Brick.ResourcesSet resourcesSet) {
        for (Script script : this.scriptList) {
            if (!script.isCommentedOut()) {
                script.addRequiredResources(resourcesSet);
            }
        }
        Iterator<LookData> it = getLookList().iterator();
        while (it.hasNext()) {
            it.next().addRequiredResources(resourcesSet);
        }
    }

    public List<NfcTagData> getNfcTagList() {
        return this.nfcTagList;
    }

    public String toString() {
        return this.f519name;
    }

    public void rename(String newSpriteName) {
        renameSpriteAndUpdateCollisionFormulas(newSpriteName, ProjectManager.getInstance().getCurrentlyEditedScene());
    }

    public void renameSpriteAndUpdateCollisionFormulas(String newSpriteName, Scene scene) {
        if (hasCollision(scene)) {
            renameSpriteInCollisionFormulas(newSpriteName, scene);
        }
        setName(newSpriteName);
    }

    public boolean hasCollision(Scene scene) {
        Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
        addRequiredResources(resourcesSet);
        if (resourcesSet.contains(19)) {
            return true;
        }
        Iterator<Sprite> it = scene.getSpriteList().iterator();
        while (it.hasNext()) {
            if (it.next().hasToCollideWith(this)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasToCollideWith(Sprite other) {
        for (Script script : getScriptList()) {
            ArrayList arrayList = new ArrayList();
            script.addToFlatList(arrayList);
            for (Brick brick : arrayList) {
                if (brick instanceof FormulaBrick) {
                    Iterator<Formula> it = ((FormulaBrick) brick).getFormulas().iterator();
                    while (it.hasNext()) {
                        if (it.next().containsSpriteInCollision(other.getName())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void renameSpriteInCollisionFormulas(String newName, Scene scene) {
        String name2 = getName();
        Iterator<Sprite> it = scene.getSpriteList().iterator();
        while (it.hasNext()) {
            for (Script script : it.next().getScriptList()) {
                if (script == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                script.addToFlatList(arrayList);
                for (Brick brick : arrayList) {
                    if (brick instanceof FormulaBrick) {
                        Iterator<Formula> it2 = ((FormulaBrick) brick).getFormulas().iterator();
                        while (it2.hasNext()) {
                            it2.next().updateCollisionFormulas(name2, newName, CatroidApplication.getAppContext());
                        }
                    }
                }
            }
        }
    }

    public void createCollisionPolygons() {
        Iterator<LookData> it = getLookList().iterator();
        while (it.hasNext()) {
            it.next().getCollisionInformation().calculate();
        }
    }

    public boolean doesUserBrickAlreadyExist(UserDefinedBrick userDefinedBrick) {
        Iterator<Brick> it = getUserDefinedBrickList().iterator();
        while (it.hasNext()) {
            if (((UserDefinedBrick) it.next()).isUserDefinedBrickDataEqual(userDefinedBrick)) {
                return true;
            }
        }
        return false;
    }

    public void updateUserDataReferences(String oldName, String newName, UserData<?> item) {
        Iterator<Script> it = this.scriptList.iterator();
        while (it.hasNext()) {
            it.next().updateUserDataReferences(oldName, newName, item);
        }
    }

    public void deselectElements(List<UserData<?>> elements) {
        Iterator<Script> it = this.scriptList.iterator();
        while (it.hasNext()) {
            it.next().deselectElements(elements);
        }
    }

    public boolean toBeConverted() {
        return this.convertToSprite || this.convertToGroupItemSprite;
    }

    public void setConvertToSprite(boolean convertToSprite) {
        this.convertToGroupItemSprite = false;
        this.convertToSprite = convertToSprite;
    }

    public void setConvertToGroupItemSprite(boolean convertToGroupItemSprite) {
        this.convertToSprite = false;
        this.convertToGroupItemSprite = convertToGroupItemSprite;
    }

    public boolean isBackgroundSprite() {
        return this.look.getZIndex() == 0;
    }

    public boolean isBackgroundSprite(Context context) {
        return this.f519name.equals(context.getString(R.string.background));
    }

    public Multimap<EventId, ScriptSequenceAction> getIdToEventThreadMap() {
        return this.idToEventThreadMap;
    }

    public int getUnusedPointer() {
        int i = 0;
        while (i < 20 && this.usedTouchPointer.contains(Integer.valueOf(i))) {
            i++;
        }
        this.usedTouchPointer.add(Integer.valueOf(i));
        return i;
    }

    public void releaseUsedPointer(int position) {
        this.usedTouchPointer.removeAll(Collections.singleton(Integer.valueOf(position)));
    }

    public void releaseAllPointers() {
        if (StageActivity.getActiveStageListener() != null) {
            Stage stage = StageActivity.getActiveStageListener().getStage();
            Iterator<Integer> it = this.usedTouchPointer.iterator();
            while (it.hasNext()) {
                stage.touchUp(0, 0, it.next().intValue(), 0);
            }
        }
        this.usedTouchPointer.clear();
    }

    public Brick findBrickInSprite(UUID brickId) {
        for (Script script : this.scriptList) {
            if (script.getScriptId().equals(brickId)) {
                return script.getScriptBrick();
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(brickId);
        Iterator<Script> it = this.scriptList.iterator();
        while (it.hasNext()) {
            List<Brick> listFindBricksInScript = it.next().findBricksInScript(arrayList);
            if (listFindBricksInScript != null && !listFindBricksInScript.isEmpty()) {
                return listFindBricksInScript.get(0);
            }
        }
        return null;
    }

    public List<UUID> removeAllEmptyScriptBricks() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Script script : this.scriptList) {
            if ((script instanceof EmptyScript) && (script.brickList == null || script.brickList.isEmpty())) {
                arrayList2.add(script);
                arrayList.add(script.getScriptId());
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            this.scriptList.remove((Script) it.next());
        }
        return arrayList;
    }

    public void setEmbroideryThreadColor(Color embroideryThreadColor) {
        this.embroideryThreadColor = embroideryThreadColor;
    }

    public Color getEmbroideryThreadColor() {
        return this.embroideryThreadColor;
    }

    public Sprite(Sprite sprite, Scene destinationScene) throws IOException {
        this.look = new Look(this);
        this.penConfiguration = new PenConfiguration();
        this.runningStitch = new RunningStitch();
        this.plot = new Plot();
        this.convertToSprite = false;
        this.convertToGroupItemSprite = false;
        this.idToEventThreadMap = LinkedHashMultimap.create();
        this.conditionScriptTriggers = new HashSet();
        this.usedTouchPointer = new ArrayList();
        this.embroideryThreadColor = Color.BLACK;
        this.variableCache = new HashMap<>();
        this.scriptList = new ArrayList();
        this.lookList = new ArrayList();
        this.soundList = new ArrayList();
        this.nfcTagList = new ArrayList();
        this.userVariables = new ArrayList();
        this.userLists = new ArrayList();
        this.userDefinedBrickList = new ArrayList();
        this.actionFactory = new ActionFactory();
        this.isClone = false;
        this.myOriginal = null;
        this.movedByStepsBrick = false;
        this.isGliding = false;
        this.glidingVelocityX = 0.0f;
        this.glidingVelocityY = 0.0f;
        try {
            copyLooksAndSounds(sprite, destinationScene, false);
            this.scriptList.addAll(sprite.scriptList);
            this.nfcTagList.addAll(sprite.nfcTagList);
            this.userVariables.addAll(sprite.userVariables);
            this.userLists.addAll(sprite.userLists);
            this.userDefinedBrickList.addAll(sprite.userDefinedBrickList);
            sprite.look.copyTo(this.look);
            this.myOriginal = sprite;
            this.f519name = sprite.getName();
            resolveSpriteReferences(this, destinationScene.getProject());
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw e;
        }
    }

    private void copyLooksAndSounds(Sprite sprite, Scene destinationScene, boolean setUniqueName) throws IOException {
        File file = new File(destinationScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        File file2 = new File(destinationScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
        for (LookData lookData : sprite.lookList) {
            File fileCopyFileToDir = StorageOperations.copyFileToDir(lookData.getFile(), file);
            LookData lookDataM4849clone = lookData.clone();
            lookDataM4849clone.setFile(fileCopyFileToDir);
            if (setUniqueName) {
                lookDataM4849clone.setName(new UniqueNameProvider().getUniqueNameInNameables(lookDataM4849clone.getName(), this.lookList));
            }
            this.lookList.add(lookDataM4849clone);
        }
        for (SoundInfo soundInfo : sprite.soundList) {
            File fileCopyFileToDir2 = StorageOperations.copyFileToDir(soundInfo.getFile(), file2);
            SoundInfo soundInfoClone = soundInfo.clone();
            soundInfoClone.setFile(fileCopyFileToDir2);
            if (setUniqueName) {
                soundInfoClone.setName(new UniqueNameProvider().getUniqueNameInNameables(soundInfoClone.getName(), this.soundList));
            }
            this.soundList.add(soundInfoClone);
        }
    }

    public void mergeSprites(Sprite sprite, Scene destinationScene) throws IOException {
        try {
            copyLooksAndSounds(sprite, destinationScene, true);
            this.scriptList.addAll(sprite.scriptList);
            this.nfcTagList.addAll(sprite.nfcTagList);
            for (UserVariable userVariable : sprite.userVariables) {
                if (!this.userVariables.contains(userVariable)) {
                    this.userVariables.add(userVariable);
                }
            }
            for (UserList userList : sprite.userLists) {
                if (!this.userLists.contains(userList)) {
                    this.userLists.add(userList);
                }
            }
            this.userDefinedBrickList.addAll(sprite.userDefinedBrickList);
            resolveSpriteReferences(this, destinationScene.getProject());
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw e;
        }
    }

    public UserDefinedScript getUserDefinedScript(UUID userDefinedBrickId) {
        for (Script script : this.scriptList) {
            if (script instanceof UserDefinedScript) {
                UserDefinedScript userDefinedScript = (UserDefinedScript) script;
                if (userDefinedScript.getUserDefinedBrickID().equals(userDefinedBrickId)) {
                    return userDefinedScript;
                }
            }
        }
        return null;
    }

    public void setGliding(boolean gliding) {
        this.isGliding = gliding;
    }

    public boolean isGliding() {
        return this.isGliding;
    }

    public void setGlidingVelocityX(float velocity) {
        this.glidingVelocityX = velocity;
    }

    public void setGlidingVelocityY(float velocity) {
        this.glidingVelocityY = velocity;
    }

    public float getGlidingVelocityX() {
        return this.glidingVelocityX;
    }

    public float getGlidingVelocityY() {
        return this.glidingVelocityY;
    }
}
