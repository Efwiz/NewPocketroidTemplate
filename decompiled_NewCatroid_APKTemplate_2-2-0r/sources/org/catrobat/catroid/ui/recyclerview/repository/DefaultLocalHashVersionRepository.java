package org.catrobat.catroid.ui.recyclerview.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.IOException;
import java.security.GeneralSecurityException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/repository/DefaultLocalHashVersionRepository.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalHashVersionRepository.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\n\u0010\r\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0016J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0016R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/repository/DefaultLocalHashVersionRepository;", "Lorg/catrobat/catroid/ui/recyclerview/repository/LocalHashVersionRepository;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "encryptedPref", "Landroid/content/SharedPreferences;", "getEncryptedPref", "()Landroid/content/SharedPreferences;", "encryptedPref$delegate", "Lkotlin/Lazy;", "getFeaturedProjectsHashVersion", "", "getProjectsCategoriesHashVersion", "reset", "", "setFeaturedProjectsHashVersion", "hashVersion", "setProjectsCategoriesHashVersion", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultLocalHashVersionRepository implements LocalHashVersionRepository {
    private static final String FEATURED_PROJECTS_HASH_VERSION = "featured_projects_hash_version";
    private static final String PROJECT_CATEGORIES_HASH_VERSION = "project_categories_hash_version";

    /* JADX INFO: renamed from: encryptedPref$delegate, reason: from kotlin metadata */
    private final Lazy encryptedPref;

    public DefaultLocalHashVersionRepository(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.encryptedPref = LazyKt.lazy(new Function0<SharedPreferences>() { // from class: org.catrobat.catroid.ui.recyclerview.repository.DefaultLocalHashVersionRepository$encryptedPref$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences invoke() throws GeneralSecurityException, IOException {
                SharedPreferences sharedPreferencesCreate = EncryptedSharedPreferences.create("hashVersionsSharedPref", MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                Intrinsics.checkNotNullExpressionValue(sharedPreferencesCreate, "create(...)");
                return sharedPreferencesCreate;
            }
        });
    }

    private final SharedPreferences getEncryptedPref() {
        return (SharedPreferences) this.encryptedPref.getValue();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository
    public String getFeaturedProjectsHashVersion() {
        return getEncryptedPref().getString(FEATURED_PROJECTS_HASH_VERSION, null);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository
    public void setFeaturedProjectsHashVersion(String hashVersion) {
        SharedPreferences.Editor editorEdit = getEncryptedPref().edit();
        editorEdit.putString(FEATURED_PROJECTS_HASH_VERSION, hashVersion);
        editorEdit.apply();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository
    public String getProjectsCategoriesHashVersion() {
        return getEncryptedPref().getString(PROJECT_CATEGORIES_HASH_VERSION, null);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository
    public void setProjectsCategoriesHashVersion(String hashVersion) {
        SharedPreferences.Editor editorEdit = getEncryptedPref().edit();
        editorEdit.putString(PROJECT_CATEGORIES_HASH_VERSION, hashVersion);
        editorEdit.apply();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository
    public void reset() {
        SharedPreferences.Editor editorEdit = getEncryptedPref().edit();
        editorEdit.clear();
        editorEdit.apply();
    }
}
