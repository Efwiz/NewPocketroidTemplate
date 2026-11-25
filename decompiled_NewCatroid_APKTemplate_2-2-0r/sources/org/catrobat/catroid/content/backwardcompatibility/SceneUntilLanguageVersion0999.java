package org.catrobat.catroid.content.backwardcompatibility;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.XStreamFieldKeyOrder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/SceneUntilLanguageVersion0999.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
@XStreamAlias("scene")
@XStreamFieldKeyOrder({"name", "objectList", "data"})
public class SceneUntilLanguageVersion0999 implements Serializable {
    private static final long serialVersionUID = 1;
    private LegacyDataContainer data;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f559name;
    private List<Sprite> objectList = new ArrayList();

    public String getName() {
        return this.f559name;
    }

    public List<Sprite> getSpriteList() {
        return this.objectList;
    }

    public List<UserVariable> getSpriteUserVariables(Sprite sprite) {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.spriteVariables.get(sprite) != null) {
            return this.data.spriteVariables.get(sprite);
        }
        return Collections.emptyList();
    }

    public List<UserList> getSpriteUserLists(Sprite sprite) {
        LegacyDataContainer legacyDataContainer = this.data;
        if (legacyDataContainer != null && legacyDataContainer.spriteListOfLists.get(sprite) != null) {
            return this.data.spriteListOfLists.get(sprite);
        }
        return Collections.emptyList();
    }
}
