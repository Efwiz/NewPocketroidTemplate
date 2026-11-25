package org.catrobat.catroid.content.backwardcompatibility;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/LegacyDataContainer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LegacyDataContainer implements Serializable {
    private static final long serialVersionUID = 1;

    @XStreamAlias("programListOfLists")
    public List<UserList> projectLists;

    @XStreamAlias("programVariableList")
    public List<UserVariable> projectVariables;

    @XStreamAlias("objectVariableList")
    public Map<Sprite, List<UserVariable>> spriteVariables = new HashMap();

    @XStreamAlias("objectListOfList")
    public Map<Sprite, List<UserList>> spriteListOfLists = new HashMap();
}
