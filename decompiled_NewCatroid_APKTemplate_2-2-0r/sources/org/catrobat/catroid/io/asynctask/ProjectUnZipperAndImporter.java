package org.catrobat.catroid.io.asynctask;

import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectUnZipperAndImporter.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0019\u0010\r\u001a\u00020\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0002\u0010\u0011R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectUnZipperAndImporter;", "", "onImportFinished", "Lkotlin/Function1;", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Lkotlin/jvm/functions/Function1;Lkotlinx/coroutines/CoroutineScope;)V", "getOnImportFinished", "()Lkotlin/jvm/functions/Function1;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "unZipAndImportAsync", "files", "", "Ljava/io/File;", "([Ljava/io/File;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectUnZipperAndImporter {
    private final Function1<ImportResult, Unit> onImportFinished;
    private final CoroutineScope scope;

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectUnZipperAndImporter() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ProjectUnZipperAndImporter(Function1<? super ImportResult, Unit> onImportFinished) {
        this(onImportFinished, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(onImportFinished, "onImportFinished");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectUnZipperAndImporter(Function1<? super ImportResult, Unit> onImportFinished, CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(onImportFinished, "onImportFinished");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.onImportFinished = onImportFinished;
        this.scope = scope;
    }

    public /* synthetic */ ProjectUnZipperAndImporter(AnonymousClass1 anonymousClass1, CoroutineScope coroutineScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1<ImportResult, Unit>() { // from class: org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ImportResult it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ImportResult importResult) {
                invoke2(importResult);
                return Unit.INSTANCE;
            }
        } : anonymousClass1, (i & 2) != 0 ? CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()) : coroutineScope);
    }

    public final Function1<ImportResult, Unit> getOnImportFinished() {
        return this.onImportFinished;
    }

    public final CoroutineScope getScope() {
        return this.scope;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectUnZipperAndImporter$unZipAndImportAsync$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter$unZipAndImportAsync$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter$unZipAndImportAsync$1", f = "ProjectUnZipperAndImporter.kt", i = {}, l = {64}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41751 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ File[] $files;
        int label;
        final /* synthetic */ ProjectUnZipperAndImporter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41751(File[] fileArr, ProjectUnZipperAndImporter projectUnZipperAndImporter, Continuation<? super C41751> continuation) {
            super(2, continuation);
            this.$files = fileArr;
            this.this$0 = projectUnZipperAndImporter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41751(this.$files, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41751) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            ImportResult.Failure failureUnzipAndImportProject;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                File file = (File) ArraysKt.firstOrNull(this.$files);
                if (file != null) {
                    failureUnzipAndImportProject = ProjectUnZipperAndImporterKt.unzipAndImportProject(file);
                } else {
                    failureUnzipAndImportProject = ImportResult.Failure.INSTANCE;
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02561(this.this$0, failureUnzipAndImportProject, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectUnZipperAndImporter$unZipAndImportAsync$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter$unZipAndImportAsync$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter$unZipAndImportAsync$1$1", f = "ProjectUnZipperAndImporter.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02561 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ ImportResult $result;
            int label;
            final /* synthetic */ ProjectUnZipperAndImporter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02561(ProjectUnZipperAndImporter projectUnZipperAndImporter, ImportResult importResult, Continuation<? super C02561> continuation) {
                super(2, continuation);
                this.this$0 = projectUnZipperAndImporter;
                this.$result = importResult;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02561(this.this$0, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02561) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.getOnImportFinished().invoke(this.$result);
                return Unit.INSTANCE;
            }
        }
    }

    public final void unZipAndImportAsync(File[] files) {
        Intrinsics.checkNotNullParameter(files, "files");
        BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new C41751(files, this, null), 3, null);
    }
}
