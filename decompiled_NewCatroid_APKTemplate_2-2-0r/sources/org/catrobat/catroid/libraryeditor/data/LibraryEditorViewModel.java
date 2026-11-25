package org.catrobat.catroid.libraryeditor.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.utils.lunoscript.Lexer;
import org.catrobat.catroid.utils.lunoscript.LunoSyntaxError;
import org.catrobat.catroid.utils.lunoscript.Token;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryEditorViewModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u000e\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\rJ\u000e\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\rJ\u0016\u0010\"\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$J\u0016\u0010%\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010&\u001a\u00020$J\u000e\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\rJ\u000e\u0010*\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010+\u001a\u00020\u00192\u0006\u0010,\u001a\u00020-J\u000e\u0010.\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u000201J\u000e\u00102\u001a\u00020\u00192\u0006\u00103\u001a\u00020\rJ=\u00104\u001a\u00020\u0019\"\u0004\b\u0000\u001052\f\u00106\u001a\b\u0012\u0004\u0012\u0002H5072\u0006\u00108\u001a\u0002H52\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u00020(0:H\u0002¢\u0006\u0002\u0010;R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011¨\u0006<"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_lexedTokens", "Landroidx/lifecycle/MutableLiveData;", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "_libraryDraft", "Lorg/catrobat/catroid/libraryeditor/data/LibraryDraft;", "_syntaxError", "Lorg/catrobat/catroid/utils/lunoscript/LunoSyntaxError;", "_toastMessage", "Lorg/catrobat/catroid/libraryeditor/data/Event;", "", "lexedTokens", "Landroidx/lifecycle/LiveData;", "getLexedTokens", "()Landroidx/lifecycle/LiveData;", "libraryDraft", "getLibraryDraft", "syntaxError", "getSyntaxError", "toastMessage", "getToastMessage", "analyzeCode", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "clearCurrentLibrary", "context", "Landroid/content/Context;", "deleteBrick", "brickId", "deleteFormula", "formulaId", "exportToUri", "destinationUri", "Landroid/net/Uri;", "importFromUri", "uri", "isFunctionDefined", "", "functionName", "loadLastSession", "saveBrick", "brick", "Lorg/catrobat/catroid/libraryeditor/data/EditableBrick;", "saveCurrentSession", "saveFormula", "formula", "Lorg/catrobat/catroid/libraryeditor/data/EditableFormula;", "updateCode", "newCode", "updateList", ExifInterface.GPS_DIRECTION_TRUE, SchemaSymbols.ATTVAL_LIST, "", "item", "findPredicate", "Lkotlin/Function1;", "(Ljava/util/List;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryEditorViewModel extends ViewModel {
    private final MutableLiveData<List<Token>> _lexedTokens;
    private final MutableLiveData<LibraryDraft> _libraryDraft;
    private final MutableLiveData<LunoSyntaxError> _syntaxError;
    private final MutableLiveData<Event<String>> _toastMessage;
    private final LiveData<List<Token>> lexedTokens;
    private final LiveData<LibraryDraft> libraryDraft;
    private final LiveData<LunoSyntaxError> syntaxError;
    private final LiveData<Event<String>> toastMessage;

    public LibraryEditorViewModel() {
        MutableLiveData<LibraryDraft> mutableLiveData = new MutableLiveData<>();
        this._libraryDraft = mutableLiveData;
        this.libraryDraft = mutableLiveData;
        MutableLiveData<List<Token>> mutableLiveData2 = new MutableLiveData<>();
        this._lexedTokens = mutableLiveData2;
        this.lexedTokens = mutableLiveData2;
        MutableLiveData<LunoSyntaxError> mutableLiveData3 = new MutableLiveData<>();
        this._syntaxError = mutableLiveData3;
        this.syntaxError = mutableLiveData3;
        MutableLiveData<Event<String>> mutableLiveData4 = new MutableLiveData<>();
        this._toastMessage = mutableLiveData4;
        this.toastMessage = mutableLiveData4;
    }

    public final LiveData<LibraryDraft> getLibraryDraft() {
        return this.libraryDraft;
    }

    public final LiveData<List<Token>> getLexedTokens() {
        return this.lexedTokens;
    }

    public final LiveData<LunoSyntaxError> getSyntaxError() {
        return this.syntaxError;
    }

    public final LiveData<Event<String>> getToastMessage() {
        return this.toastMessage;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$loadLastSession$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$loadLastSession$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LibraryEditorViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$loadLastSession$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41841 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;
        final /* synthetic */ LibraryEditorViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41841(Context context, LibraryEditorViewModel libraryEditorViewModel, Continuation<? super C41841> continuation) {
            super(2, continuation);
            this.$context = context;
            this.this$0 = libraryEditorViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41841(this.$context, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41841) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            LibraryDraft libraryDraftLoadLastSession = LibraryStorageManager.INSTANCE.loadLastSession(this.$context);
            this.this$0._libraryDraft.postValue(libraryDraftLoadLastSession);
            this.this$0.analyzeCode(libraryDraftLoadLastSession.getCode());
            return Unit.INSTANCE;
        }
    }

    public final void loadLastSession(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C41841(context, this, null), 2, null);
    }

    public final void saveCurrentSession(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        LibraryDraft value = this.libraryDraft.getValue();
        if (value != null) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new LibraryEditorViewModel$saveCurrentSession$1$1(context, value, null), 2, null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$clearCurrentLibrary$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$clearCurrentLibrary$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LibraryEditorViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$clearCurrentLibrary$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41791 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;
        final /* synthetic */ LibraryEditorViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41791(Context context, LibraryEditorViewModel libraryEditorViewModel, Continuation<? super C41791> continuation) {
            super(2, continuation);
            this.$context = context;
            this.this$0 = libraryEditorViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41791(this.$context, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41791) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            LibraryStorageManager.INSTANCE.clearSession(this.$context);
            this.this$0.loadLastSession(this.$context);
            this.this$0._toastMessage.postValue(new Event(CatroidApplication.getAppContext().getString(R.string.libs_cleaned)));
            return Unit.INSTANCE;
        }
    }

    public final void clearCurrentLibrary(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C41791(context, this, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$importFromUri$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$importFromUri$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LibraryEditorViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$importFromUri$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41831 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ Uri $uri;
        int label;
        final /* synthetic */ LibraryEditorViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41831(Context context, Uri uri, LibraryEditorViewModel libraryEditorViewModel, Continuation<? super C41831> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$uri = uri;
            this.this$0 = libraryEditorViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41831(this.$context, this.$uri, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41831) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            LibraryDraft libraryDraftM5414import = NewlibImporter.INSTANCE.m5414import(this.$context, this.$uri);
            if (libraryDraftM5414import != null) {
                this.this$0._libraryDraft.postValue(libraryDraftM5414import);
                this.this$0.saveCurrentSession(this.$context);
                this.this$0.analyzeCode(libraryDraftM5414import.getCode());
                this.this$0._toastMessage.postValue(new Event(CatroidApplication.getAppContext().getString(R.string.libs_imported)));
            } else {
                this.this$0._toastMessage.postValue(new Event(CatroidApplication.getAppContext().getString(R.string.libs_importede)));
            }
            return Unit.INSTANCE;
        }
    }

    public final void importFromUri(Context context, Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C41831(context, uri, this, null), 2, null);
    }

    public final void updateCode(String newCode) {
        Intrinsics.checkNotNullParameter(newCode, "newCode");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value == null || Intrinsics.areEqual(value.getCode(), newCode)) {
            return;
        }
        value.setCode(newCode);
        this._libraryDraft.postValue(value);
        analyzeCode(newCode);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$analyzeCode$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$analyzeCode$1, reason: invalid class name */
    /* JADX INFO: compiled from: LibraryEditorViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$analyzeCode$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $code;
        int label;
        final /* synthetic */ LibraryEditorViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, LibraryEditorViewModel libraryEditorViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$code = str;
            this.this$0 = libraryEditorViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$code, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            try {
                this.this$0._lexedTokens.postValue(new Lexer(this.$code).scanTokens());
                this.this$0._syntaxError.postValue(null);
            } catch (LunoSyntaxError e) {
                this.this$0._syntaxError.postValue(e);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void analyzeCode(String code) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getDefault(), null, new AnonymousClass1(code, this, null), 2, null);
    }

    public final boolean isFunctionDefined(String functionName) {
        String code;
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value == null || (code = value.getCode()) == null) {
            return false;
        }
        return StringsKt.contains$default((CharSequence) code, (CharSequence) ("fun " + functionName), false, 2, (Object) null);
    }

    public final void saveFormula(final EditableFormula formula) {
        List<EditableFormula> formulas;
        Intrinsics.checkNotNullParameter(formula, "formula");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value == null || (formulas = value.getFormulas()) == null) {
            return;
        }
        updateList(formulas, formula, new Function1<EditableFormula, Boolean>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$saveFormula$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(EditableFormula it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getId(), formula.getId()));
            }
        });
    }

    public final void deleteFormula(final String formulaId) {
        List<EditableFormula> formulas;
        Intrinsics.checkNotNullParameter(formulaId, "formulaId");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value != null && (formulas = value.getFormulas()) != null) {
            CollectionsKt.removeAll((List) formulas, (Function1) new Function1<EditableFormula, Boolean>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel.deleteFormula.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(EditableFormula it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(it.getId(), formulaId));
                }
            });
        }
        MutableLiveData<LibraryDraft> mutableLiveData = this._libraryDraft;
        mutableLiveData.postValue(mutableLiveData.getValue());
    }

    public final void saveBrick(final EditableBrick brick) {
        List<EditableBrick> bricks;
        Intrinsics.checkNotNullParameter(brick, "brick");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value == null || (bricks = value.getBricks()) == null) {
            return;
        }
        updateList(bricks, brick, new Function1<EditableBrick, Boolean>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$saveBrick$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(EditableBrick it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getId(), brick.getId()));
            }
        });
    }

    public final void deleteBrick(final String brickId) {
        List<EditableBrick> bricks;
        Intrinsics.checkNotNullParameter(brickId, "brickId");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value != null && (bricks = value.getBricks()) != null) {
            CollectionsKt.removeAll((List) bricks, (Function1) new Function1<EditableBrick, Boolean>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel.deleteBrick.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(EditableBrick it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(it.getId(), brickId));
                }
            });
        }
        MutableLiveData<LibraryDraft> mutableLiveData = this._libraryDraft;
        mutableLiveData.postValue(mutableLiveData.getValue());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel$exportToUri$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$exportToUri$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: LibraryEditorViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.libraryeditor.data.LibraryEditorViewModel$exportToUri$1", f = "LibraryEditorViewModel.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C41821 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ Uri $destinationUri;
        final /* synthetic */ LibraryDraft $draft;
        int label;
        final /* synthetic */ LibraryEditorViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41821(Context context, Uri uri, LibraryEditorViewModel libraryEditorViewModel, LibraryDraft libraryDraft, Continuation<? super C41821> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$destinationUri = uri;
            this.this$0 = libraryEditorViewModel;
            this.$draft = libraryDraft;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41821(this.$context, this.$destinationUri, this.this$0, this.$draft, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41821) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            try {
                OutputStream outputStreamOpenOutputStream = this.$context.getContentResolver().openOutputStream(this.$destinationUri);
                if (outputStreamOpenOutputStream != null) {
                    OutputStream outputStream = outputStreamOpenOutputStream;
                    LibraryDraft libraryDraft = this.$draft;
                    try {
                        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
                        try {
                            ZipOutputStream zipOutputStream2 = zipOutputStream;
                            zipOutputStream2.putNextEntry(new ZipEntry("code.txt"));
                            byte[] bytes = libraryDraft.getCode().getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                            zipOutputStream2.write(bytes);
                            zipOutputStream2.closeEntry();
                            zipOutputStream2.putNextEntry(new ZipEntry("formulas.xml"));
                            byte[] bytes2 = XmlGenerator.INSTANCE.generateFormulasXml(libraryDraft.getFormulas()).getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                            zipOutputStream2.write(bytes2);
                            zipOutputStream2.closeEntry();
                            zipOutputStream2.putNextEntry(new ZipEntry("bricks.xml"));
                            byte[] bytes3 = XmlGenerator.INSTANCE.generateBricksXml(libraryDraft.getBricks()).getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
                            zipOutputStream2.write(bytes3);
                            zipOutputStream2.closeEntry();
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(zipOutputStream, null);
                            Unit unit2 = Unit.INSTANCE;
                            CloseableKt.closeFinally(outputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(zipOutputStream, th);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(outputStream, th3);
                            throw th4;
                        }
                    }
                }
                this.this$0._toastMessage.postValue(new Event(CatroidApplication.getAppContext().getString(R.string.libs_exported)));
            } catch (Exception e) {
                Log.e("LibraryExport", "Failed to export library", e);
                this.this$0._toastMessage.postValue(new Event(CatroidApplication.getAppContext().getString(R.string.libs_exportede)));
            }
            return Unit.INSTANCE;
        }
    }

    public final void exportToUri(Context context, Uri destinationUri) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(destinationUri, "destinationUri");
        LibraryDraft value = this._libraryDraft.getValue();
        if (value == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C41821(context, destinationUri, this, value, null), 2, null);
    }

    private final <T> void updateList(List<T> list, T item, Function1<? super T, Boolean> findPredicate) {
        Iterator<T> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (findPredicate.invoke(it.next()).booleanValue()) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            list.set(i, item);
        } else {
            list.add(item);
        }
        MutableLiveData<LibraryDraft> mutableLiveData = this._libraryDraft;
        mutableLiveData.postValue(mutableLiveData.getValue());
    }
}
