package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.apkbuild.ApkToolboxManager;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectOptionsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ File $finalApkFile;
    final /* synthetic */ String $keystoreAlias;
    final /* synthetic */ File $keystoreFile;
    final /* synthetic */ String $keystorePass;
    final /* synthetic */ File $unsignedApkFile;
    final /* synthetic */ boolean $useCustomKeystore;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(boolean z, File file, Context context, File file2, File file3, String str, String str2, Continuation<? super ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1> continuation) {
        super(2, continuation);
        this.$useCustomKeystore = z;
        this.$keystoreFile = file;
        this.$context = context;
        this.$unsignedApkFile = file2;
        this.$finalApkFile = file3;
        this.$keystoreAlias = str;
        this.$keystorePass = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1(this.$useCustomKeystore, this.$keystoreFile, this.$context, this.$unsignedApkFile, this.$finalApkFile, this.$keystoreAlias, this.$keystorePass, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return ((ProjectOptionsFragment$executeApkBuildProcess$1$signSuccess$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        boolean zSignApk;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        if (this.$useCustomKeystore && this.$keystoreFile != null) {
            ApkToolboxManager apkToolboxManager = ApkToolboxManager.INSTANCE;
            Context context = this.$context;
            String absolutePath = this.$unsignedApkFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            String absolutePath2 = this.$finalApkFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
            zSignApk = apkToolboxManager.signApk(context, absolutePath, absolutePath2, this.$keystoreFile.getAbsolutePath(), this.$keystoreAlias, this.$keystorePass);
        } else {
            ApkToolboxManager apkToolboxManager2 = ApkToolboxManager.INSTANCE;
            Context context2 = this.$context;
            String absolutePath3 = this.$unsignedApkFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
            String absolutePath4 = this.$finalApkFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath4, "getAbsolutePath(...)");
            zSignApk = apkToolboxManager2.signApk(context2, absolutePath3, absolutePath4, null, null, null);
        }
        return Boxing.boxBoolean(zSignApk);
    }
}
