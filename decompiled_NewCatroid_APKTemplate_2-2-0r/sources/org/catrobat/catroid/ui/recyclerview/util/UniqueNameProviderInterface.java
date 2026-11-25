package org.catrobat.catroid.ui.recyclerview.util;

import java.util.List;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/util/UniqueNameProviderInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface UniqueNameProviderInterface {
    String getUniqueName(String name2, List<String> scope);

    String getUniqueNameInNameables(String name2, List<? extends Nameable> scope);

    boolean isUnique(String newName);
}
