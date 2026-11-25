package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserDefinedScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastMessageBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.PlaySoundAndWaitBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.UserDataBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.content.bricks.UserListBrick;
import org.catrobat.catroid.content.bricks.UserVariableBrickInterface;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserDataWrapper;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/ScriptController.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScriptController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 ]2\u00020\u0001:\u0001]B\u0005¢\u0006\u0002\u0010\u0002J-\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u001c\u001a\u00020\u001dH\u0002¢\u0006\u0002\u0010\u001eJ.\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00122\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'H\u0002J.\u0010(\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00122\f\u0010#\u001a\b\u0012\u0004\u0012\u00020)0$2\u0006\u0010&\u001a\u00020'H\u0002J\"\u0010*\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00122\u0006\u0010&\u001a\u00020'H\u0002J \u0010+\u001a\u00020,2\u000e\u0010-\u001a\n\u0012\u0004\u0012\u00020'\u0018\u00010.2\u0006\u0010/\u001a\u000200H\u0002J,\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\b\u00104\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u0001072\b\u00108\u001a\u0004\u0018\u00010!J\u0010\u00109\u001a\u00020,2\u0006\u0010&\u001a\u00020'H\u0002Jf\u0010:\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u00122R\u0010;\u001aN\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d0\u0011\u0018\u00010\u0011j.\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d0\u0011j\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d`\u0013\u0018\u0001`\u0013H\u0002J\u0018\u0010<\u001a\u00020,2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001c\u001a\u00020=H\u0002J\u0016\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00120.2\u0006\u0010 \u001a\u00020!H\u0002J0\u0010?\u001a\b\u0012\u0004\u0012\u0002H\u00190.\"\u0004\b\u0000\u0010\u00192\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00120.2\f\u0010A\u001a\b\u0012\u0004\u0012\u0002H\u00190.H\u0002J\u001e\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00120.2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u001c\u001a\u00020=H\u0002J\u0016\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00120.2\u0006\u0010 \u001a\u00020!H\u0002J\u001e\u0010D\u001a\b\u0012\u0004\u0012\u00020%0.2\u0006\u0010 \u001a\u00020!2\u0006\u0010&\u001a\u00020'H\u0002J\u0016\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00120.2\u0006\u0010 \u001a\u00020!H\u0002J\u001e\u0010F\u001a\b\u0012\u0004\u0012\u00020)0.2\u0006\u0010 \u001a\u00020!2\u0006\u0010&\u001a\u00020'H\u0002J@\u0010G\u001a\u00020,2\u0006\u0010H\u001a\u00020\u00122&\u0010;\u001a\"\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0011j\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001d\u0018\u0001`\u00132\u0006\u0010I\u001a\u00020\u001dH\u0002J \u0010J\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u00122\u000e\u0010-\u001a\n\u0012\u0004\u0012\u00020'\u0018\u00010.J\u001e\u0010K\u001a\u00020\u00182\u0006\u0010L\u001a\u00020!2\u0006\u0010M\u001a\u0002022\u0006\u00108\u001a\u00020!J\u001e\u0010N\u001a\u00020\u00182\u0006\u0010O\u001a\u00020\u00122\u0006\u0010P\u001a\u0002022\u0006\u00108\u001a\u00020!J2\u0010Q\u001a\u00020\u00182\u0006\u0010R\u001a\u00020\u00122\u0006\u0010P\u001a\u0002022\b\u00104\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u0001072\u0006\u00108\u001a\u00020!J \u0010S\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\u0006\u0010O\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0002J \u0010T\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\u0006\u0010O\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0002J*\u0010U\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010\"\u001a\u00020\u00122\b\u0010V\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001c\u001a\u00020=H\u0002J$\u0010W\u001a\u00020\u00182\u0006\u0010&\u001a\u00020X2\b\u00104\u001a\u0004\u0018\u0001052\b\u00108\u001a\u0004\u0018\u00010!H\u0002J$\u0010Y\u001a\u00020\u00182\u0006\u0010&\u001a\u00020Z2\b\u00104\u001a\u0004\u0018\u0001052\b\u00108\u001a\u0004\u0018\u00010!H\u0002J$\u0010[\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\\2\b\u00104\u001a\u0004\u0018\u0001052\b\u00108\u001a\u0004\u0018\u00010!H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\b\u001a\u0004\b\r\u0010\u000eR*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006^"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/controller/ScriptController;", "", "()V", "backpackListManager", "Lorg/catrobat/catroid/ui/controller/BackpackListManager;", "getBackpackListManager", "()Lorg/catrobat/catroid/ui/controller/BackpackListManager;", "backpackListManager$delegate", "Lkotlin/Lazy;", "lookController", "Lorg/catrobat/catroid/ui/recyclerview/controller/LookController;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "renamedUserLists", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "renamedUserVariables", "soundController", "Lorg/catrobat/catroid/ui/recyclerview/controller/SoundController;", "addUserDataToBackpack", "", ExifInterface.GPS_DIRECTION_TRUE, "groupName", "userData", ModuleXmlParser.TYPE, "", "(Ljava/lang/String;Ljava/lang/Object;I)V", "addUserListToBrick", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "name", "destinationList", "", "Lorg/catrobat/catroid/formulaeditor/UserList;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "addUserVariableToBrick", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "checkForUserData", "checkIfUserDefinedBrickDefinitionIsInBricksToPack", "", "bricksToPack", "", "userDefinedBrick", "Lorg/catrobat/catroid/content/bricks/UserDefinedBrick;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/catrobat/catroid/content/Script;", "scriptToCopy", "destinationProject", "Lorg/catrobat/catroid/content/Project;", "destinationScene", "Lorg/catrobat/catroid/content/Scene;", "destinationSprite", "copyBroadcastMessages", "createInitialHashmap", "map", "formulaBrickContainsUserData", "Lorg/catrobat/catroid/formulaeditor/FormulaElement$ElementType;", "getAllUserDataNames", "getUserDataFromNames", "nameList", "userDataList", "getUserDataNamesForBrick", "getUserListNames", "getUserLists", "getUserVariableNames", "getUserVariables", "isUserDataAlreadyInScript", "userDataName", "variableType", ConfigConstants.CONFIG_PACK_SECTION, "packForSprite", "sourceSprite", "scriptToPack", "unpack", "scriptName", "scriptToUnpack", "unpackForSprite", "spriteName", "unpackUserList", "unpackUserVariable", "updateFormula", "newName", "updateUserData", "Lorg/catrobat/catroid/content/bricks/UserDataBrick;", "updateUserList", "Lorg/catrobat/catroid/content/bricks/UserListBrick;", "updateUserVariable", "Lorg/catrobat/catroid/content/bricks/UserVariableBrickInterface;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptController {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int GLOBAL_USER_VARIABLE = 0;
    public static final int LOCAL_USER_VARIABLE = 1;
    public static final int MULTIPLAYER_USER_VARIABLE = 2;
    private static final String TAG;
    private final LookController lookController = new LookController();
    private final SoundController soundController = new SoundController();

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager = KoinJavaComponent.inject$default(ProjectManager.class, null, null, 6, null);

    /* JADX INFO: renamed from: backpackListManager$delegate, reason: from kotlin metadata */
    private final Lazy backpackListManager = KoinJavaComponent.inject$default(BackpackListManager.class, null, null, 6, null);
    private final HashMap<String, String> renamedUserVariables = new HashMap<>();
    private final HashMap<String, String> renamedUserLists = new HashMap<>();

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    private final BackpackListManager getBackpackListManager() {
        return (BackpackListManager) this.backpackListManager.getValue();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/ScriptController$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ScriptController.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/controller/ScriptController$Companion;", "", "()V", "GLOBAL_USER_VARIABLE", "", "LOCAL_USER_VARIABLE", "MULTIPLAYER_USER_VARIABLE", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ScriptController.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ScriptController", "getSimpleName(...)");
        TAG = "ScriptController";
    }

    public final Script copy(Script scriptToCopy, Project destinationProject, Scene destinationScene, Sprite destinationSprite) throws IOException, CloneNotSupportedException {
        Intrinsics.checkNotNullParameter(scriptToCopy, "scriptToCopy");
        Script scriptClone = scriptToCopy.clone();
        Intrinsics.checkNotNullExpressionValue(scriptClone, "clone(...)");
        ArrayList arrayList = new ArrayList();
        scriptClone.addToFlatList(arrayList);
        for (Brick brick : arrayList) {
            if (brick instanceof SetLookBrick) {
                SetLookBrick setLookBrick = (SetLookBrick) brick;
                LookData look = setLookBrick.getLook();
                setLookBrick.setLook(look != null ? this.lookController.findOrCopy(look, destinationScene, destinationSprite) : null);
            } else if (brick instanceof WhenBackgroundChangesBrick) {
                WhenBackgroundChangesBrick whenBackgroundChangesBrick = (WhenBackgroundChangesBrick) brick;
                LookData look2 = whenBackgroundChangesBrick.getLook();
                whenBackgroundChangesBrick.setLook(look2 != null ? this.lookController.findOrCopy(look2, destinationScene, destinationSprite) : null);
            } else if (brick instanceof PlaySoundBrick) {
                PlaySoundBrick playSoundBrick = (PlaySoundBrick) brick;
                SoundInfo sound = playSoundBrick.getSound();
                playSoundBrick.setSound(sound != null ? this.soundController.findOrCopy(sound, destinationScene, destinationSprite) : null);
            } else if (brick instanceof PlaySoundAndWaitBrick) {
                PlaySoundAndWaitBrick playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                SoundInfo sound2 = playSoundAndWaitBrick.getSound();
                playSoundAndWaitBrick.setSound(sound2 != null ? this.soundController.findOrCopy(sound2, destinationScene, destinationSprite) : null);
            } else if (brick instanceof UserVariableBrickInterface) {
                UserVariableBrickInterface userVariableBrickInterface = (UserVariableBrickInterface) brick;
                if (userVariableBrickInterface.getUserVariable() != null) {
                    updateUserVariable(userVariableBrickInterface, destinationProject, destinationSprite);
                }
            } else if (brick instanceof UserListBrick) {
                UserListBrick userListBrick = (UserListBrick) brick;
                if (userListBrick.getUserList() != null) {
                    updateUserList(userListBrick, destinationProject, destinationSprite);
                }
            } else if (brick instanceof UserDataBrick) {
                updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
            }
        }
        return scriptClone;
    }

    public final void pack(String groupName, List<? extends Brick> bricksToPack) throws CloneNotSupportedException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (bricksToPack != null) {
            for (Brick brick : bricksToPack) {
                if (brick instanceof ScriptBrick) {
                    if (brick instanceof UserDefinedReceiverBrick) {
                        Brick brickClone = ((UserDefinedReceiverBrick) brick).getUserDefinedBrick().clone();
                        Intrinsics.checkNotNull(brickClone, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedBrick");
                        arrayList2.add((UserDefinedBrick) brickClone);
                    }
                    Script scriptClone = brick.getScript().clone();
                    Intrinsics.checkNotNullExpressionValue(scriptClone, "clone(...)");
                    arrayList.add(scriptClone);
                }
                if (brick instanceof UserDefinedBrick) {
                    UserDefinedBrick userDefinedBrick = (UserDefinedBrick) brick;
                    UserDefinedScript userDefinedScript = getProjectManager().getCurrentSprite().getUserDefinedScript(userDefinedBrick.getUserDefinedBrickID());
                    if (!checkIfUserDefinedBrickDefinitionIsInBricksToPack(bricksToPack, userDefinedBrick)) {
                        Intrinsics.checkNotNull(userDefinedScript);
                        arrayList.add(userDefinedScript);
                        Brick brickClone2 = brick.clone();
                        Intrinsics.checkNotNull(brickClone2, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.UserDefinedBrick");
                        arrayList2.add((UserDefinedBrick) brickClone2);
                    }
                }
                Sprite currentSprite = getProjectManager().getCurrentSprite();
                Intrinsics.checkNotNullExpressionValue(currentSprite, "getCurrentSprite(...)");
                checkForUserData(currentSprite, groupName, brick);
            }
        }
        getBackpackListManager().addUserDefinedBrickToBackPack(groupName, arrayList2);
        getBackpackListManager().addScriptToBackPack(groupName, arrayList);
        getBackpackListManager().saveBackpack();
    }

    private final void checkForUserData(Sprite sprite, String groupName, Brick brick) {
        for (UserVariable userVariable : getUserVariables(sprite, brick)) {
            createInitialHashmap(groupName, getBackpackListManager().getBackpack().backpackedUserVariables);
            if (getProjectManager().getCurrentProject().getUserVariables().contains(userVariable)) {
                addUserDataToBackpack(groupName, userVariable, 0);
            } else if (sprite.getUserVariables().contains(userVariable)) {
                addUserDataToBackpack(groupName, userVariable, 1);
            } else if (getProjectManager().getCurrentProject().getMultiplayerVariables().contains(userVariable)) {
                addUserDataToBackpack(groupName, userVariable, 2);
            }
        }
        for (UserList userList : getUserLists(sprite, brick)) {
            createInitialHashmap(groupName, getBackpackListManager().getBackpack().backpackedUserLists);
            if (getProjectManager().getCurrentProject().getUserLists().contains(userList)) {
                addUserDataToBackpack(groupName, userList, 0);
            } else if (sprite.getUserLists().contains(userList)) {
                addUserDataToBackpack(groupName, userList, 1);
            }
        }
    }

    private final List<String> getUserDataNamesForBrick(Brick brick, FormulaElement.ElementType type) {
        ArrayList arrayList = new ArrayList();
        if ((brick instanceof UserVariableBrickInterface) && type == FormulaElement.ElementType.USER_VARIABLE) {
            arrayList.add(((UserVariableBrickInterface) brick).getUserVariable().getName());
        } else if ((brick instanceof UserListBrick) && type == FormulaElement.ElementType.USER_LIST) {
            arrayList.add(((UserListBrick) brick).getUserList().getName());
        } else if (brick instanceof FormulaBrick) {
            Iterator<Formula> it = ((FormulaBrick) brick).getFormulas().iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next().getFormulaTree().getUserDataRecursive(type));
            }
        }
        return arrayList;
    }

    private final List<UserVariable> getUserVariables(Sprite sprite, Brick brick) {
        ArrayList arrayList = new ArrayList();
        if ((brick instanceof FormulaBrick) && formulaBrickContainsUserData(brick, FormulaElement.ElementType.USER_VARIABLE)) {
            List<String> userDataNamesForBrick = getUserDataNamesForBrick(brick, FormulaElement.ElementType.USER_VARIABLE);
            List<UserVariable> userVariables = getProjectManager().getCurrentProject().getUserVariables();
            Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
            arrayList.addAll(getUserDataFromNames(userDataNamesForBrick, userVariables));
            List<UserVariable> multiplayerVariables = getProjectManager().getCurrentProject().getMultiplayerVariables();
            Intrinsics.checkNotNullExpressionValue(multiplayerVariables, "getMultiplayerVariables(...)");
            arrayList.addAll(getUserDataFromNames(userDataNamesForBrick, multiplayerVariables));
            List<UserVariable> userVariables2 = sprite.getUserVariables();
            Intrinsics.checkNotNullExpressionValue(userVariables2, "getUserVariables(...)");
            arrayList.addAll(getUserDataFromNames(userDataNamesForBrick, userVariables2));
        } else if (brick instanceof UserVariableBrickInterface) {
            arrayList.add(((UserVariableBrickInterface) brick).getUserVariable());
        }
        return arrayList;
    }

    private final List<UserList> getUserLists(Sprite sprite, Brick brick) {
        ArrayList arrayList = new ArrayList();
        if ((brick instanceof FormulaBrick) && formulaBrickContainsUserData(brick, FormulaElement.ElementType.USER_LIST)) {
            List<String> userDataNamesForBrick = getUserDataNamesForBrick(brick, FormulaElement.ElementType.USER_LIST);
            List<UserList> userLists = getProjectManager().getCurrentProject().getUserLists();
            Intrinsics.checkNotNullExpressionValue(userLists, "getUserLists(...)");
            arrayList.addAll(getUserDataFromNames(userDataNamesForBrick, userLists));
            List<UserList> userLists2 = sprite.getUserLists();
            Intrinsics.checkNotNullExpressionValue(userLists2, "getUserLists(...)");
            arrayList.addAll(getUserDataFromNames(userDataNamesForBrick, userLists2));
        } else if (brick instanceof UserListBrick) {
            arrayList.add(((UserListBrick) brick).getUserList());
        }
        return arrayList;
    }

    private final <T> List<T> getUserDataFromNames(List<String> nameList, List<? extends T> userDataList) {
        ArrayList arrayList = new ArrayList();
        for (T t : userDataList) {
            for (String str : nameList) {
                if ((t instanceof UserVariable) && Intrinsics.areEqual(((UserVariable) t).getName(), str)) {
                    arrayList.add(t);
                } else if ((t instanceof UserList) && Intrinsics.areEqual(((UserList) t).getName(), str)) {
                    arrayList.add(t);
                }
            }
        }
        return arrayList;
    }

    private final boolean formulaBrickContainsUserData(Brick brick, FormulaElement.ElementType type) {
        Intrinsics.checkNotNull(brick, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.FormulaBrick");
        Iterator<Formula> it = ((FormulaBrick) brick).getFormulas().iterator();
        while (it.hasNext()) {
            if (it.next().getFormulaTree().containsElement(type)) {
                return true;
            }
        }
        return false;
    }

    private final void createInitialHashmap(String groupName, HashMap<String, HashMap<String, Integer>> map) {
        if (map == null || map.containsKey(groupName)) {
            return;
        }
        map.put(groupName, new HashMap<>());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <T> void addUserDataToBackpack(String groupName, T userData, int type) {
        HashMap<String, Integer> map = new HashMap<>();
        String str = new String();
        if (userData instanceof UserVariable) {
            map = getBackpackListManager().getBackpack().backpackedUserVariables.get(groupName);
            str = ((UserVariable) userData).getName();
            Intrinsics.checkNotNullExpressionValue(str, "getName(...)");
        } else if (userData instanceof UserList) {
            map = getBackpackListManager().getBackpack().backpackedUserLists.get(groupName);
            str = ((UserList) userData).getName();
            Intrinsics.checkNotNullExpressionValue(str, "getName(...)");
        }
        if (isUserDataAlreadyInScript(str, map, type) || map == null) {
            return;
        }
        map.put(str, Integer.valueOf(type));
    }

    private final boolean isUserDataAlreadyInScript(String userDataName, HashMap<String, Integer> map, int variableType) {
        Integer num;
        return (map == null || (num = map.get(userDataName)) == null || num.intValue() != variableType) ? false : true;
    }

    private final boolean checkIfUserDefinedBrickDefinitionIsInBricksToPack(List<? extends Brick> bricksToPack, UserDefinedBrick userDefinedBrick) {
        if (bricksToPack == null) {
            return false;
        }
        for (Brick brick : bricksToPack) {
            if ((brick instanceof UserDefinedReceiverBrick) && Intrinsics.areEqual(((UserDefinedReceiverBrick) brick).getUserDefinedBrick().getUserDefinedBrickID(), userDefinedBrick.getUserDefinedBrickID())) {
                return true;
            }
        }
        return false;
    }

    public final void packForSprite(Sprite sourceSprite, Script scriptToPack, Sprite destinationSprite) throws IOException, CloneNotSupportedException {
        Intrinsics.checkNotNullParameter(sourceSprite, "sourceSprite");
        Intrinsics.checkNotNullParameter(scriptToPack, "scriptToPack");
        Intrinsics.checkNotNullParameter(destinationSprite, "destinationSprite");
        Script scriptClone = scriptToPack.clone();
        Intrinsics.checkNotNullExpressionValue(scriptClone, "clone(...)");
        ArrayList arrayList = new ArrayList();
        scriptClone.addToFlatList(arrayList);
        for (Brick brick : arrayList) {
            if (brick instanceof SetLookBrick) {
                SetLookBrick setLookBrick = (SetLookBrick) brick;
                LookData look = setLookBrick.getLook();
                setLookBrick.setLook(look != null ? this.lookController.packForSprite(look, destinationSprite) : null);
            } else if (brick instanceof WhenBackgroundChangesBrick) {
                WhenBackgroundChangesBrick whenBackgroundChangesBrick = (WhenBackgroundChangesBrick) brick;
                LookData look2 = whenBackgroundChangesBrick.getLook();
                whenBackgroundChangesBrick.setLook(look2 != null ? this.lookController.packForSprite(look2, destinationSprite) : null);
            } else if (brick instanceof PlaySoundBrick) {
                PlaySoundBrick playSoundBrick = (PlaySoundBrick) brick;
                SoundInfo sound = playSoundBrick.getSound();
                playSoundBrick.setSound(sound != null ? this.soundController.packForSprite(sound, destinationSprite) : null);
            } else if (brick instanceof PlaySoundAndWaitBrick) {
                PlaySoundAndWaitBrick playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                SoundInfo sound2 = playSoundAndWaitBrick.getSound();
                playSoundAndWaitBrick.setSound(sound2 != null ? this.soundController.packForSprite(sound2, destinationSprite) : null);
            }
            checkForUserData(sourceSprite, destinationSprite.getName(), brick);
        }
        destinationSprite.getScriptList().add(scriptClone);
    }

    public final void unpack(String scriptName, Script scriptToUnpack, Sprite destinationSprite) throws CloneNotSupportedException {
        Intrinsics.checkNotNullParameter(scriptName, "scriptName");
        Intrinsics.checkNotNullParameter(scriptToUnpack, "scriptToUnpack");
        Intrinsics.checkNotNullParameter(destinationSprite, "destinationSprite");
        Script scriptClone = scriptToUnpack.clone();
        Intrinsics.checkNotNullExpressionValue(scriptClone, "clone(...)");
        ScriptBrick scriptBrick = scriptClone.getScriptBrick();
        Intrinsics.checkNotNullExpressionValue(scriptBrick, "getScriptBrick(...)");
        copyBroadcastMessages(scriptBrick);
        for (Brick brick : scriptClone.getBrickList()) {
            if (getProjectManager().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brick.getClass())) {
                Log.e(TAG, "CANNOT insert bricks into ChromeCast project");
                return;
            }
            Sprite currentSprite = getProjectManager().getCurrentSprite();
            Intrinsics.checkNotNullExpressionValue(currentSprite, "getCurrentSprite(...)");
            Intrinsics.checkNotNull(brick);
            unpackUserVariable(currentSprite, scriptName, brick);
            Sprite currentSprite2 = getProjectManager().getCurrentSprite();
            Intrinsics.checkNotNullExpressionValue(currentSprite2, "getCurrentSprite(...)");
            unpackUserList(currentSprite2, scriptName, brick);
            copyBroadcastMessages(brick);
        }
        destinationSprite.getScriptList().add(scriptClone);
        this.renamedUserVariables.clear();
        this.renamedUserLists.clear();
    }

    private final void unpackUserVariable(Sprite sprite, String scriptName, Brick brick) {
        for (String str : getUserDataNamesForBrick(brick, FormulaElement.ElementType.USER_VARIABLE)) {
            HashMap<String, Integer> map = getBackpackListManager().getBackpack().backpackedUserVariables.get(scriptName);
            Integer num = map != null ? map.get(str) : null;
            List<UserVariable> userVariables = getProjectManager().getCurrentProject().getUserVariables();
            Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
            if (num != null && num.intValue() == 1) {
                userVariables = sprite.getUserVariables();
                Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
            } else if (num != null && num.intValue() == 2) {
                userVariables = getProjectManager().getCurrentProject().getMultiplayerVariables();
                Intrinsics.checkNotNullExpressionValue(userVariables, "getMultiplayerVariables(...)");
            }
            addUserVariableToBrick(sprite, str, userVariables, brick);
        }
    }

    private final void unpackUserList(Sprite sprite, String scriptName, Brick brick) {
        List<UserList> userLists;
        for (String str : getUserDataNamesForBrick(brick, FormulaElement.ElementType.USER_LIST)) {
            HashMap<String, Integer> map = getBackpackListManager().getBackpack().backpackedUserLists.get(scriptName);
            Integer num = map != null ? map.get(str) : null;
            if (num != null && num.intValue() == 0) {
                userLists = getProjectManager().getCurrentProject().getUserLists();
                Intrinsics.checkNotNull(userLists);
            } else {
                userLists = sprite.getUserLists();
                Intrinsics.checkNotNull(userLists);
            }
            addUserListToBrick(sprite, str, userLists, brick);
        }
    }

    private final void addUserVariableToBrick(Sprite sprite, String name2, List<UserVariable> destinationList, Brick brick) {
        Object next;
        if (this.renamedUserVariables.containsKey(name2)) {
            if (brick instanceof UserVariableBrickInterface) {
                UserVariableBrickInterface userVariableBrickInterface = (UserVariableBrickInterface) brick;
                Iterator<T> it = destinationList.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((UserVariable) next).getName(), this.renamedUserVariables.get(name2)));
                userVariableBrickInterface.setUserVariable((UserVariable) next);
            }
            updateFormula(brick, name2, this.renamedUserVariables.get(name2), FormulaElement.ElementType.USER_VARIABLE);
            return;
        }
        List<UserVariable> list = destinationList;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(((UserVariable) it2.next()).getName(), name2)) {
                    return;
                }
            }
        }
        String uniqueName = new UniqueNameProvider().getUniqueName(name2, getAllUserDataNames(sprite));
        if (!Intrinsics.areEqual(uniqueName, name2)) {
            HashMap<String, String> map = this.renamedUserVariables;
            Intrinsics.checkNotNull(uniqueName);
            map.put(name2, uniqueName);
        }
        UserVariable userVariable = new UserVariable(uniqueName);
        destinationList.add(userVariable);
        if (brick instanceof UserVariableBrickInterface) {
            ((UserVariableBrickInterface) brick).setUserVariable(userVariable);
        }
        updateFormula(brick, name2, uniqueName, FormulaElement.ElementType.USER_VARIABLE);
    }

    private final void addUserListToBrick(Sprite sprite, String name2, List<UserList> destinationList, Brick brick) {
        Object next;
        if (this.renamedUserLists.containsKey(name2)) {
            if (brick instanceof UserListBrick) {
                UserListBrick userListBrick = (UserListBrick) brick;
                Iterator<T> it = destinationList.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((UserList) next).getName(), this.renamedUserLists.get(name2)));
                userListBrick.setUserList((UserList) next);
            }
            updateFormula(brick, name2, this.renamedUserLists.get(name2), FormulaElement.ElementType.USER_LIST);
            return;
        }
        List<UserList> list = destinationList;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(((UserList) it2.next()).getName(), name2)) {
                    return;
                }
            }
        }
        String uniqueName = new UniqueNameProvider().getUniqueName(name2, getAllUserDataNames(sprite));
        if (!Intrinsics.areEqual(uniqueName, name2)) {
            HashMap<String, String> map = this.renamedUserLists;
            Intrinsics.checkNotNull(uniqueName);
            map.put(name2, uniqueName);
        }
        UserList userList = new UserList(uniqueName);
        destinationList.add(userList);
        if (brick instanceof UserListBrick) {
            ((UserListBrick) brick).setUserList(userList);
        }
        updateFormula(brick, name2, uniqueName, FormulaElement.ElementType.USER_LIST);
    }

    private final void updateFormula(Brick brick, String name2, String newName, FormulaElement.ElementType type) {
        if (brick instanceof FormulaBrick) {
            List<Formula> formulas = ((FormulaBrick) brick).getFormulas();
            Intrinsics.checkNotNullExpressionValue(formulas, "getFormulas(...)");
            Iterator<T> it = formulas.iterator();
            while (it.hasNext()) {
                ((Formula) it.next()).getFormulaTree().getRoot().updateElementByName(name2, newName, type);
            }
        }
    }

    private final List<String> getAllUserDataNames(Sprite sprite) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getUserVariableNames(sprite));
        arrayList.addAll(getUserListNames(sprite));
        return arrayList;
    }

    private final List<String> getUserVariableNames(Sprite sprite) {
        ArrayList arrayList = new ArrayList();
        Iterator<UserVariable> it = getProjectManager().getCurrentProject().getUserVariables().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        Iterator<UserVariable> it2 = sprite.getUserVariables().iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().getName());
        }
        Iterator<UserVariable> it3 = getProjectManager().getCurrentProject().getMultiplayerVariables().iterator();
        while (it3.hasNext()) {
            arrayList.add(it3.next().getName());
        }
        return arrayList;
    }

    private final List<String> getUserListNames(Sprite sprite) {
        ArrayList arrayList = new ArrayList();
        Iterator<UserList> it = getProjectManager().getCurrentProject().getUserLists().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        Iterator<UserList> it2 = sprite.getUserLists().iterator();
        while (it2.hasNext()) {
            arrayList.add(it2.next().getName());
        }
        return arrayList;
    }

    private final boolean copyBroadcastMessages(Brick brick) {
        if (!(brick instanceof BroadcastMessageBrick)) {
            return false;
        }
        return getProjectManager().getCurrentProject().getBroadcastMessageContainer().addBroadcastMessage(((BroadcastMessageBrick) brick).getBroadcastMessage());
    }

    /* JADX WARN: Code duplicated, block: B:16:0x006a  */
    /* JADX WARN: Code duplicated, block: B:18:0x006e  */
    /* JADX WARN: Code duplicated, block: B:20:0x0077  */
    /* JADX WARN: Code duplicated, block: B:21:0x0085  */
    /* JADX WARN: Code duplicated, block: B:23:0x0089  */
    /* JADX WARN: Code duplicated, block: B:25:0x0092  */
    /* JADX WARN: Code duplicated, block: B:26:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:28:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:30:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:31:0x00bb  */
    /* JADX WARN: Code duplicated, block: B:33:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c5 A[SYNTHETIC] */
    public final void unpackForSprite(String spriteName, Script scriptToUnpack, Project destinationProject, Scene destinationScene, Sprite destinationSprite) throws IOException, CloneNotSupportedException {
        PlaySoundAndWaitBrick playSoundAndWaitBrick;
        PlaySoundBrick playSoundBrick;
        WhenBackgroundChangesBrick whenBackgroundChangesBrick;
        Intrinsics.checkNotNullParameter(spriteName, "spriteName");
        Intrinsics.checkNotNullParameter(scriptToUnpack, "scriptToUnpack");
        Intrinsics.checkNotNullParameter(destinationSprite, "destinationSprite");
        Script scriptClone = scriptToUnpack.clone();
        Intrinsics.checkNotNullExpressionValue(scriptClone, "clone(...)");
        for (Brick brick : scriptClone.getBrickList()) {
            if (getProjectManager().getCurrentProject().isCastProject() && CastManager.unsupportedBricks.contains(brick.getClass())) {
                Log.e(TAG, "CANNOT insert bricks into ChromeCast project");
                return;
            }
            if (brick instanceof SetLookBrick) {
                SetLookBrick setLookBrick = (SetLookBrick) brick;
                if (setLookBrick.getLook() != null) {
                    setLookBrick.setLook(this.lookController.unpackForSprite(setLookBrick.getLook(), destinationScene, destinationSprite));
                } else if (brick instanceof WhenBackgroundChangesBrick) {
                    whenBackgroundChangesBrick = (WhenBackgroundChangesBrick) brick;
                    if (whenBackgroundChangesBrick.getLook() != null) {
                        whenBackgroundChangesBrick.setLook(this.lookController.unpackForSprite(whenBackgroundChangesBrick.getLook(), destinationScene, destinationSprite));
                    } else if (brick instanceof PlaySoundBrick) {
                        playSoundBrick = (PlaySoundBrick) brick;
                        if (playSoundBrick.getSound() != null) {
                            playSoundBrick.setSound(this.soundController.unpackForSprite(playSoundBrick.getSound(), destinationScene, destinationSprite));
                        } else if (brick instanceof PlaySoundAndWaitBrick) {
                            playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                            if (playSoundAndWaitBrick.getSound() != null) {
                                playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                            } else if (brick instanceof UserDataBrick) {
                                updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                            }
                        } else if (brick instanceof UserDataBrick) {
                            updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                        }
                    } else if (brick instanceof PlaySoundAndWaitBrick) {
                        playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                        if (playSoundAndWaitBrick.getSound() != null) {
                            playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                        } else if (brick instanceof UserDataBrick) {
                            updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                        }
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof PlaySoundBrick) {
                    playSoundBrick = (PlaySoundBrick) brick;
                    if (playSoundBrick.getSound() != null) {
                        playSoundBrick.setSound(this.soundController.unpackForSprite(playSoundBrick.getSound(), destinationScene, destinationSprite));
                    } else if (brick instanceof PlaySoundAndWaitBrick) {
                        playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                        if (playSoundAndWaitBrick.getSound() != null) {
                            playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                        } else if (brick instanceof UserDataBrick) {
                            updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                        }
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof PlaySoundAndWaitBrick) {
                    playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                    if (playSoundAndWaitBrick.getSound() != null) {
                        playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof UserDataBrick) {
                    updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                }
            } else if (brick instanceof WhenBackgroundChangesBrick) {
                whenBackgroundChangesBrick = (WhenBackgroundChangesBrick) brick;
                if (whenBackgroundChangesBrick.getLook() != null) {
                    whenBackgroundChangesBrick.setLook(this.lookController.unpackForSprite(whenBackgroundChangesBrick.getLook(), destinationScene, destinationSprite));
                } else if (brick instanceof PlaySoundBrick) {
                    playSoundBrick = (PlaySoundBrick) brick;
                    if (playSoundBrick.getSound() != null) {
                        playSoundBrick.setSound(this.soundController.unpackForSprite(playSoundBrick.getSound(), destinationScene, destinationSprite));
                    } else if (brick instanceof PlaySoundAndWaitBrick) {
                        playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                        if (playSoundAndWaitBrick.getSound() != null) {
                            playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                        } else if (brick instanceof UserDataBrick) {
                            updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                        }
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof PlaySoundAndWaitBrick) {
                    playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                    if (playSoundAndWaitBrick.getSound() != null) {
                        playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof UserDataBrick) {
                    updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                }
            } else if (brick instanceof PlaySoundBrick) {
                playSoundBrick = (PlaySoundBrick) brick;
                if (playSoundBrick.getSound() != null) {
                    playSoundBrick.setSound(this.soundController.unpackForSprite(playSoundBrick.getSound(), destinationScene, destinationSprite));
                } else if (brick instanceof PlaySoundAndWaitBrick) {
                    playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                    if (playSoundAndWaitBrick.getSound() != null) {
                        playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                    } else if (brick instanceof UserDataBrick) {
                        updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                    }
                } else if (brick instanceof UserDataBrick) {
                    updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                }
            } else if (brick instanceof PlaySoundAndWaitBrick) {
                playSoundAndWaitBrick = (PlaySoundAndWaitBrick) brick;
                if (playSoundAndWaitBrick.getSound() != null) {
                    playSoundAndWaitBrick.setSound(this.soundController.unpackForSprite(playSoundAndWaitBrick.getSound(), destinationScene, destinationSprite));
                } else if (brick instanceof UserDataBrick) {
                    updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
                }
            } else if (brick instanceof UserDataBrick) {
                updateUserData((UserDataBrick) brick, destinationProject, destinationSprite);
            }
            Intrinsics.checkNotNull(brick);
            unpackUserVariable(destinationSprite, spriteName, brick);
            unpackUserList(destinationSprite, spriteName, brick);
        }
        destinationSprite.getScriptList().add(scriptClone);
        this.renamedUserVariables.clear();
        this.renamedUserLists.clear();
    }

    private final void updateUserData(UserDataBrick brick, Project destinationProject, Sprite destinationSprite) {
        UserData userVariable;
        for (Map.Entry<Brick.BrickData, UserData> entry : brick.getUserDataMap().entrySet()) {
            UserData value = entry.getValue();
            Scope scope = destinationSprite != null ? new Scope(destinationProject, destinationSprite, null) : null;
            if (Brick.BrickData.isUserList(entry.getKey())) {
                userVariable = UserDataWrapper.getUserList(value != null ? value.getName() : null, scope);
            } else {
                userVariable = UserDataWrapper.getUserVariable(value != null ? value.getName() : null, scope);
            }
            entry.setValue(userVariable);
        }
    }

    private final void updateUserList(UserListBrick brick, Project destinationProject, Sprite destinationSprite) {
        UserList userList = brick.getUserList();
        brick.setUserList(UserDataWrapper.getUserList(userList != null ? userList.getName() : null, destinationSprite != null ? new Scope(destinationProject, destinationSprite, null) : null));
    }

    private final void updateUserVariable(UserVariableBrickInterface brick, Project destinationProject, Sprite destinationSprite) {
        UserVariable userVariable = brick.getUserVariable();
        brick.setUserVariable(UserDataWrapper.getUserVariable(userVariable != null ? userVariable.getName() : null, destinationSprite != null ? new Scope(destinationProject, destinationSprite, null) : null));
    }
}
