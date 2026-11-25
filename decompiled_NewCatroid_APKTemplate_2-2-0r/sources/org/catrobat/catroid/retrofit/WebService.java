package org.catrobat.catroid.retrofit;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.retrofit.models.FeaturedProject;
import org.catrobat.catroid.retrofit.models.ProjectsCategoryApi;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/WebService.class
  classes14.dex
 */
/* JADX INFO: compiled from: RetrofitWebServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001JF\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0003\u0010\u0006\u001a\u00020\u00072\b\b\u0003\u0010\b\u001a\u00020\u00072\b\b\u0003\u0010\t\u001a\u00020\u00072\b\b\u0003\u0010\n\u001a\u00020\u000b2\b\b\u0003\u0010\f\u001a\u00020\u000bH'J(\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00040\u00032\b\b\u0003\u0010\u0006\u001a\u00020\u00072\b\b\u0003\u0010\b\u001a\u00020\u0007H'¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/retrofit/WebService;", "", "getFeaturedProjects", "Lretrofit2/Call;", "", "Lorg/catrobat/catroid/retrofit/models/FeaturedProject;", "maxVersion", "", "flavor", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "limit", "", TypedValues.CycleType.S_WAVE_OFFSET, "getProjectCategories", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategoryApi;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface WebService {
    @GET("projects/featured")
    Call<List<FeaturedProject>> getFeaturedProjects(@Query("max_version") String maxVersion, @Query("flavor") String flavor, @Query(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR) String platform, @Query("limit") int limit, @Query(TypedValues.CycleType.S_WAVE_OFFSET) int offset);

    @GET("projects/categories")
    Call<List<ProjectsCategoryApi>> getProjectCategories(@Query("max_version") String maxVersion, @Query("flavor") String flavor);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/WebService$DefaultImpls.class
      classes14.dex
     */
    /* JADX INFO: compiled from: RetrofitWebServer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ Call getFeaturedProjects$default(WebService webService, String str, String str2, String str3, int i, int i2, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getFeaturedProjects");
            }
            if ((i3 & 1) != 0) {
                str = "1.18";
            }
            if ((i3 & 2) != 0) {
                str2 = FlavoredConstants.FLAVOR_NAME;
            }
            String str4 = str2;
            if ((i3 & 4) != 0) {
                str3 = "android";
            }
            String str5 = str3;
            if ((i3 & 8) != 0) {
                i = 20;
            }
            int i4 = i;
            if ((i3 & 16) != 0) {
                i2 = 0;
            }
            return webService.getFeaturedProjects(str, str4, str5, i4, i2);
        }

        public static /* synthetic */ Call getProjectCategories$default(WebService webService, String str, String str2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getProjectCategories");
            }
            if ((i & 1) != 0) {
                str = "1.18";
            }
            if ((i & 2) != 0) {
                str2 = FlavoredConstants.FLAVOR_NAME;
            }
            return webService.getProjectCategories(str, str2);
        }
    }
}
