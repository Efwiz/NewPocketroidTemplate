package androidx.fragment.app;

import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/fragment.jar:androidx/fragment/app/FragmentManagerNonConfig.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class FragmentManagerNonConfig {
    private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;
    private final Collection<Fragment> mFragments;
    private final Map<String, ViewModelStore> mViewModelStores;

    FragmentManagerNonConfig(Collection<Fragment> collection, Map<String, FragmentManagerNonConfig> map, Map<String, ViewModelStore> map2) {
        this.mFragments = collection;
        this.mChildNonConfigs = map;
        this.mViewModelStores = map2;
    }

    boolean isRetaining(Fragment fragment) {
        Collection<Fragment> collection = this.mFragments;
        if (collection == null) {
            return false;
        }
        return collection.contains(fragment);
    }

    Collection<Fragment> getFragments() {
        return this.mFragments;
    }

    Map<String, FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }

    Map<String, ViewModelStore> getViewModelStores() {
        return this.mViewModelStores;
    }
}
