package org.catrobat.catroid.ui.recyclerview.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/util/UniqueNameProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UniqueNameProvider implements UniqueNameProviderInterface {
    private List<String> scope;

    @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
    public String getUniqueName(String name2, List<String> scope) {
        this.scope = scope;
        if (isUnique(name2)) {
            return name2;
        }
        Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(name2);
        int i = 1;
        if (matcher.find()) {
            name2 = name2.replace(matcher.group(0), "").trim();
            i = Integer.parseInt(matcher.group(1));
        }
        while (i < Integer.MAX_VALUE) {
            String str = name2 + " (" + i + Constant.AFTER_QUTO;
            if (isUnique(str)) {
                return str;
            }
            i++;
        }
        return name2;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
    public boolean isUnique(String newName) {
        return !this.scope.contains(newName);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.util.UniqueNameProviderInterface
    public String getUniqueNameInNameables(String name2, List<? extends Nameable> scope) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends Nameable> it = scope.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return getUniqueName(name2, arrayList);
    }
}
