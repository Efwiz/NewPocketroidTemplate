package org.catrobat.catroid.ui.recyclerview.fragment;

import android.util.Log;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.content.backwardcompatibility.ProjectMetaDataParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/common/ProjectData;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1", f = "ProjectListFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ProjectData>, Object> {
    final /* synthetic */ File $projectDir;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1(File file, Continuation<? super ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1> continuation) {
        super(2, continuation);
        this.$projectDir = file;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1(this.$projectDir, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ProjectData> continuation) {
        return ((ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        File file = new File(this.$projectDir, Constants.CODE_XML_FILE_NAME);
        String absolutePath = file.getAbsolutePath();
        ProjectData projectData = (ProjectData) ProjectListFragment.projectDataCache.get(absolutePath);
        if (projectData != null) {
            return projectData;
        }
        try {
            ProjectData projectMetaData = new ProjectMetaDataParser(file).getProjectMetaData();
            ConcurrentHashMap concurrentHashMap = ProjectListFragment.projectDataCache;
            Intrinsics.checkNotNull(absolutePath);
            Intrinsics.checkNotNull(projectMetaData);
            concurrentHashMap.put(absolutePath, projectMetaData);
            return projectMetaData;
        } catch (Exception e) {
            Log.e(ProjectListFragment.INSTANCE.getTAG(), "Could not parse local project " + this.$projectDir, e);
            return new ProjectData(this.$projectDir.getName(), this.$projectDir, file.lastModified(), false);
        }
    }
}
