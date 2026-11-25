package org.catrobat.catroid.ui.recyclerview.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$updateAdapterAsync$1$quickItems$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u0004H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Ljava/util/ArrayList;", "Lorg/catrobat/catroid/common/ProjectData;", "Lkotlin/collections/ArrayList;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$quickItems$1", f = "ProjectListFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectListFragment$updateAdapterAsync$1$quickItems$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ArrayList<ProjectData>>, Object> {
    int label;
    final /* synthetic */ ProjectListFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectListFragment$updateAdapterAsync$1$quickItems$1(ProjectListFragment projectListFragment, Continuation<? super ProjectListFragment$updateAdapterAsync$1$quickItems$1> continuation) {
        super(2, continuation);
        this.this$0 = projectListFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectListFragment$updateAdapterAsync$1$quickItems$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ArrayList<ProjectData>> continuation) {
        return ((ProjectListFragment$updateAdapterAsync$1$quickItems$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = FlavoredConstants.DEFAULT_ROOT_DIRECTORY.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
                if (file2.exists()) {
                    arrayList.add(new ProjectData(file.getName(), file, file2.lastModified(), false));
                }
            }
        }
        if (this.this$0.adapter.projectsSorted) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$quickItems$1$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ProjectListFragment$updateAdapterAsync$1$quickItems$1.invokeSuspend$lambda$1((ProjectData) obj2, (ProjectData) obj3);
                }
            });
        } else {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$quickItems$1$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ProjectListFragment$updateAdapterAsync$1$quickItems$1.invokeSuspend$lambda$2((ProjectData) obj2, (ProjectData) obj3);
                }
            });
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int invokeSuspend$lambda$1(ProjectData projectData, ProjectData projectData2) {
        String name2 = projectData.getName();
        String name3 = projectData2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name2.compareTo(name3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int invokeSuspend$lambda$2(ProjectData projectData, ProjectData projectData2) {
        return Intrinsics.compare(projectData2.getLastUsed(), projectData.getLastUsed());
    }
}
