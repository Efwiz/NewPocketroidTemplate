package org.catrobat.catroid.ui.recyclerview.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$updateAdapterAsync$1$finalItems$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001*\u00020\u0004H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/catrobat/catroid/common/ProjectData;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$finalItems$1", f = "ProjectListFragment.kt", i = {}, l = {899}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectListFragment$updateAdapterAsync$1$finalItems$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ProjectData>>, Object> {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ProjectListFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectListFragment$updateAdapterAsync$1$finalItems$1(ProjectListFragment projectListFragment, Continuation<? super ProjectListFragment$updateAdapterAsync$1$finalItems$1> continuation) {
        super(2, continuation);
        this.this$0 = projectListFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ProjectListFragment$updateAdapterAsync$1$finalItems$1 projectListFragment$updateAdapterAsync$1$finalItems$1 = new ProjectListFragment$updateAdapterAsync$1$finalItems$1(this.this$0, continuation);
        projectListFragment$updateAdapterAsync$1$finalItems$1.L$0 = obj;
        return projectListFragment$updateAdapterAsync$1$finalItems$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<ProjectData>> continuation) {
        return ((ProjectListFragment$updateAdapterAsync$1$finalItems$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        ArrayList arrayListEmptyList;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            File[] fileArrListFiles = FlavoredConstants.DEFAULT_ROOT_DIRECTORY.listFiles();
            if (fileArrListFiles == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else {
                ArrayList arrayList = new ArrayList();
                for (File file : fileArrListFiles) {
                    if (new File(file, Constants.CODE_XML_FILE_NAME).exists()) {
                        arrayList.add(file);
                    }
                }
                arrayListEmptyList = arrayList;
            }
            List list = arrayListEmptyList;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(BuildersKt__Builders_commonKt.async$default(coroutineScope, Dispatchers.getIO(), null, new ProjectListFragment$updateAdapterAsync$1$finalItems$1$deferredList$1$1((File) it.next(), null), 2, null));
            }
            this.label = 1;
            obj = AwaitKt.awaitAll(arrayList2, this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        List mutableList = CollectionsKt.toMutableList((Collection) obj);
        if (this.this$0.adapter.projectsSorted) {
            CollectionsKt.sortWith(mutableList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$finalItems$1$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ProjectListFragment$updateAdapterAsync$1$finalItems$1.invokeSuspend$lambda$2((ProjectData) obj2, (ProjectData) obj3);
                }
            });
        } else {
            CollectionsKt.sortWith(mutableList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1$finalItems$1$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ProjectListFragment$updateAdapterAsync$1$finalItems$1.invokeSuspend$lambda$3((ProjectData) obj2, (ProjectData) obj3);
                }
            });
        }
        return mutableList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int invokeSuspend$lambda$2(ProjectData projectData, ProjectData projectData2) {
        String name2 = projectData.getName();
        String name3 = projectData2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name2.compareTo(name3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int invokeSuspend$lambda$3(ProjectData projectData, ProjectData projectData2) {
        return Intrinsics.compare(projectData2.getLastUsed(), projectData.getLastUsed());
    }
}
