package org.catrobat.catroid.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.ListSelectorBrick;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/UserDataUtil.class
  classes14.dex
 */
/* JADX INFO: compiled from: UserDataUtil.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u001c\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/utils/UserDataUtil;", "", "()V", "containedInListSelector", "", "dateName", "", "renameUserData", "", "item", "Lorg/catrobat/catroid/formulaeditor/UserData;", "name", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UserDataUtil {
    public static final UserDataUtil INSTANCE = new UserDataUtil();

    private UserDataUtil() {
    }

    @JvmStatic
    public static final boolean containedInListSelector(String dateName) {
        Intrinsics.checkNotNullParameter(dateName, "dateName");
        List<Scene> sceneList = ProjectManager.getInstance().getCurrentProject().getSceneList();
        Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
        List<Scene> list = sceneList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            List<Sprite> spriteList = ((Scene) it.next()).getSpriteList();
            Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
            List<Sprite> list2 = spriteList;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    List<Script> scriptList = ((Sprite) it2.next()).getScriptList();
                    Intrinsics.checkNotNullExpressionValue(scriptList, "getScriptList(...)");
                    List<Script> list3 = scriptList;
                    if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                        for (Script script : list3) {
                            ArrayList arrayList = new ArrayList();
                            script.addToFlatList(arrayList);
                            ArrayList arrayList2 = new ArrayList();
                            for (Object obj : arrayList) {
                                if (obj instanceof ListSelectorBrick) {
                                    arrayList2.add(obj);
                                }
                            }
                            ArrayList arrayList3 = arrayList2;
                            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                                Iterator it3 = arrayList3.iterator();
                                while (it3.hasNext()) {
                                    List<UserList> userLists = ((ListSelectorBrick) it3.next()).getUserLists();
                                    if (!(userLists instanceof Collection) || !userLists.isEmpty()) {
                                        Iterator<T> it4 = userLists.iterator();
                                        while (it4.hasNext()) {
                                            if (Intrinsics.areEqual(((UserList) it4.next()).getName(), dateName)) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @JvmStatic
    public static final void renameUserData(UserData<?> item, final String name2) {
        Sprite currentSprite;
        List<UserVariable> userVariables;
        Project currentProject;
        List<UserVariable> userVariables2;
        Sprite currentSprite2;
        List<UserList> userLists;
        Project currentProject2;
        List<UserList> userLists2;
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(name2, "name");
        String name3 = item.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        if (containedInListSelector(name3)) {
            final String name4 = item.getName();
            Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
            Function1 function1 = new Function1<UserData<?>, Unit>() { // from class: org.catrobat.catroid.utils.UserDataUtil$renameUserData$rename$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(UserData<?> userData) {
                    invoke2(userData);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(UserData<?> data) {
                    Intrinsics.checkNotNullParameter(data, "data");
                    if (Intrinsics.areEqual(data.getName(), name4)) {
                        data.setName(name2);
                    }
                }
            };
            ProjectManager projectManager = ProjectManager.getInstance();
            if (projectManager != null && (currentProject2 = projectManager.getCurrentProject()) != null && (userLists2 = currentProject2.getUserLists()) != null) {
                Iterator<T> it = userLists2.iterator();
                while (it.hasNext()) {
                    function1.invoke(it.next());
                }
            }
            ProjectManager projectManager2 = ProjectManager.getInstance();
            if (projectManager2 != null && (currentSprite2 = projectManager2.getCurrentSprite()) != null && (userLists = currentSprite2.getUserLists()) != null) {
                Iterator<T> it2 = userLists.iterator();
                while (it2.hasNext()) {
                    function1.invoke(it2.next());
                }
            }
            ProjectManager projectManager3 = ProjectManager.getInstance();
            if (projectManager3 != null && (currentProject = projectManager3.getCurrentProject()) != null && (userVariables2 = currentProject.getUserVariables()) != null) {
                Iterator<T> it3 = userVariables2.iterator();
                while (it3.hasNext()) {
                    function1.invoke(it3.next());
                }
            }
            ProjectManager projectManager4 = ProjectManager.getInstance();
            if (projectManager4 == null || (currentSprite = projectManager4.getCurrentSprite()) == null || (userVariables = currentSprite.getUserVariables()) == null) {
                return;
            }
            Iterator<T> it4 = userVariables.iterator();
            while (it4.hasNext()) {
                function1.invoke(it4.next());
            }
            return;
        }
        item.setName(name2);
    }
}
