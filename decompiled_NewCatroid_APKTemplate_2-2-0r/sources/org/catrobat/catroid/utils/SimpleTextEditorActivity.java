package org.catrobat.catroid.utils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwnerKt;
import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/SimpleTextEditorActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "MAX_FILE_SIZE", "", "currentFile", "Ljava/io/File;", "editText", "Landroid/widget/EditText;", "loadFileContent", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", LineReader.MENU, "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "saveFile", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleTextEditorActivity extends AppCompatActivity {
    private final int MAX_FILE_SIZE = 1048576;
    private File currentFile;
    private EditText editText;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_editor);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        View viewFindViewById = findViewById(R.id.editorEditText);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        EditText editText = (EditText) viewFindViewById;
        this.editText = editText;
        File file = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        editText.setHorizontallyScrolling(true);
        String stringExtra = getIntent().getStringExtra("FILE_PATH");
        if (stringExtra == null) {
            Toast.makeText(this, "Ошибка: путь не передан", 0).show();
            finish();
            return;
        }
        this.currentFile = new File(stringExtra);
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            File file2 = this.currentFile;
            if (file2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentFile");
            } else {
                file = file2;
            }
            supportActionBar2.setTitle(file.getName());
        }
        loadFileContent();
    }

    private final void loadFileContent() {
        File file = this.currentFile;
        EditText editText = null;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentFile");
            file = null;
        }
        if (file.exists()) {
            File file2 = this.currentFile;
            if (file2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentFile");
                file2 = null;
            }
            if (file2.length() <= this.MAX_FILE_SIZE) {
                BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
                return;
            }
            EditText editText2 = this.editText;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editText");
                editText2 = null;
            }
            File file3 = this.currentFile;
            if (file3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentFile");
                file3 = null;
            }
            editText2.setText("// Файл слишком большой (" + (file3.length() / ((long) 1024)) + " KB).\n// Редактирование недоступно во избежание зависаний.");
            EditText editText3 = this.editText;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editText");
            } else {
                editText = editText3;
            }
            editText.setEnabled(false);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$loadFileContent$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1, reason: invalid class name */
    /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1", f = "SimpleTextEditorActivity.kt", i = {}, l = {66, 70}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SimpleTextEditorActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    File file = SimpleTextEditorActivity.this.currentFile;
                    if (file == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentFile");
                        file = null;
                    }
                    String text$default = FilesKt.readText$default(file, null, 1, null);
                    this.label = 1;
                    if (BuildersKt.withContext(Dispatchers.getMain(), new C02691(SimpleTextEditorActivity.this, text$default, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1 && i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Exception unused) {
                this.label = 2;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(SimpleTextEditorActivity.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$loadFileContent$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1$1", f = "SimpleTextEditorActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02691 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $text;
            int label;
            final /* synthetic */ SimpleTextEditorActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02691(SimpleTextEditorActivity simpleTextEditorActivity, String str, Continuation<? super C02691> continuation) {
                super(2, continuation);
                this.this$0 = simpleTextEditorActivity;
                this.$text = str;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02691(this.this$0, this.$text, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02691) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    EditText editText = this.this$0.editText;
                    if (editText == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("editText");
                        editText = null;
                    }
                    editText.setText(this.$text);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$loadFileContent$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$loadFileContent$1$2", f = "SimpleTextEditorActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ SimpleTextEditorActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(SimpleTextEditorActivity simpleTextEditorActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = simpleTextEditorActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                Toast.makeText(this.this$0, "Ошибка чтения", 0).show();
                return Unit.INSTANCE;
            }
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItemAdd;
        MenuItem icon;
        if (menu != null && (menuItemAdd = menu.add(0, 1, 0, "Сохранить")) != null && (icon = menuItemAdd.setIcon(R.drawable.baseline_save_alt_24_w)) != null) {
            icon.setShowAsAction(2);
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == 1) {
            saveFile();
            return true;
        }
        if (itemId == 16908332) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void saveFile() {
        if (this.currentFile == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C42111(null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$saveFile$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1", f = "SimpleTextEditorActivity.kt", i = {}, l = {106, 110}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42111 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42111(Continuation<? super C42111> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SimpleTextEditorActivity.this.new C42111(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42111) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    EditText editText = SimpleTextEditorActivity.this.editText;
                    if (editText == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("editText");
                        editText = null;
                    }
                    String string = editText.getText().toString();
                    File file = SimpleTextEditorActivity.this.currentFile;
                    if (file == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentFile");
                        file = null;
                    }
                    FilesKt.writeText$default(file, string, null, 2, null);
                    this.label = 1;
                    if (BuildersKt.withContext(Dispatchers.getMain(), new C02701(SimpleTextEditorActivity.this, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1 && i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Exception e) {
                this.label = 2;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(SimpleTextEditorActivity.this, e, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$saveFile$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1$1", f = "SimpleTextEditorActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02701 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ SimpleTextEditorActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02701(SimpleTextEditorActivity simpleTextEditorActivity, Continuation<? super C02701> continuation) {
                super(2, continuation);
                this.this$0 = simpleTextEditorActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02701(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                Toast.makeText(this.this$0, "Сохранено", 0).show();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/SimpleTextEditorActivity$saveFile$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: SimpleTextEditorActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.utils.SimpleTextEditorActivity$saveFile$1$2", f = "SimpleTextEditorActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ SimpleTextEditorActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(SimpleTextEditorActivity simpleTextEditorActivity, Exception exc, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = simpleTextEditorActivity;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                Toast.makeText(this.this$0, "Ошибка: " + this.$e.getMessage(), 1).show();
                return Unit.INSTANCE;
            }
        }
    }
}
