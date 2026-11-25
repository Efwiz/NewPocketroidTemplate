package org.catrobat.catroid.editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.PreferenceManager;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import org.catrobat.catroid.raptor.SceneManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EditorFragment extends AndroidFragmentApplication {
    private EditorListener editorListener;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.editorListener = new EditorListener((EditorActivity) getActivity());
        this.editorListener.setPcMode(PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("pref_pc_mode_enabled", false));
        return initializeForView(this.editorListener);
    }

    public EditorListener getListener() {
        return this.editorListener;
    }

    public SceneManager getSceneManager() {
        EditorListener editorListener = this.editorListener;
        if (editorListener != null) {
            return editorListener.getSceneManager();
        }
        return null;
    }
}
