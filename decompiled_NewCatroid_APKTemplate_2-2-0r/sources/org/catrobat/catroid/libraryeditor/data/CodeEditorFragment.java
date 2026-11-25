package org.catrobat.catroid.libraryeditor.data;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentViewModelLazyKt;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Job;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.lunoscript.LunoSyntaxError;
import org.catrobat.catroid.utils.lunoscript.Token;
import org.catrobat.catroid.utils.lunoscript.TokenType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/CodeEditorFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: CodeEditorFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J \u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001bH\u0002J\u0010\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u001bH\u0002J&\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\u001a\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020%2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J\b\u0010.\u001a\u00020\u0014H\u0002J\b\u0010/\u001a\u00020\u0014H\u0002J\b\u00100\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u00061"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/CodeEditorFragment;", "Landroidx/fragment/app/Fragment;", "()V", "editText", "Landroid/widget/EditText;", "ignoreTextChange", "", "lineNumbersView", "Landroid/widget/TextView;", "tab", "", "textChangeJob", "Lkotlinx/coroutines/Job;", "viewModel", "Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "getViewModel", "()Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "applyHighlighting", "", "tokens", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "error", "Lorg/catrobat/catroid/utils/lunoscript/LunoSyntaxError;", "calculateAbsolutePosition", "", "text", Constant.LINE, "position", "getColorForToken", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/utils/lunoscript/TokenType;", "handleAutoIndent", "cursorPosition", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", Promotion.ACTION_VIEW, "setupObservers", "setupTextWatcher", "updateLineNumbers", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeEditorFragment extends Fragment {
    private EditText editText;
    private boolean ignoreTextChange;
    private TextView lineNumbersView;
    private final String tab = "    ";
    private Job textChangeJob;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/CodeEditorFragment$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CodeEditorFragment.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TokenType.values().length];
            try {
                iArr[TokenType.VAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TokenType.FUN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TokenType.CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TokenType.STATIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TokenType.IF.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TokenType.ELSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TokenType.WHILE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[TokenType.FOR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[TokenType.IN.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[TokenType.SWITCH.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[TokenType.CASE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[TokenType.DEFAULT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[TokenType.RETURN.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[TokenType.BREAK.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[TokenType.CONTINUE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[TokenType.THIS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[TokenType.SUPER.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[TokenType.IMPORT.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[TokenType.TRY.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[TokenType.CATCH.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[TokenType.FINALLY.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[TokenType.NUMBER_LITERAL.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[TokenType.FLOAT_LITERAL.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[TokenType.TRUE.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[TokenType.FALSE.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[TokenType.NULL.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[TokenType.STRING_LITERAL.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[TokenType.F_STRING.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[TokenType.IDENTIFIER.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[TokenType.LPAREN.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[TokenType.RPAREN.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[TokenType.LBRACE.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[TokenType.RBRACE.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[TokenType.LBRACKET.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[TokenType.RBRACKET.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[TokenType.PLUS.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr[TokenType.MINUS.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr[TokenType.MULTIPLY.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr[TokenType.DIVIDE.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr[TokenType.MODULO.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr[TokenType.ASSIGN.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr[TokenType.PLUS_ASSIGN.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr[TokenType.MINUS_ASSIGN.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr[TokenType.MULTIPLY_ASSIGN.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr[TokenType.DIVIDE_ASSIGN.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr[TokenType.MODULO_ASSIGN.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr[TokenType.EQ.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr[TokenType.NEQ.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr[TokenType.LT.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr[TokenType.GT.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr[TokenType.LTE.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                iArr[TokenType.GTE.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                iArr[TokenType.AND.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr[TokenType.OR.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr[TokenType.BANG.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr[TokenType.DOT.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr[TokenType.COMMA.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr[TokenType.COLON.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr[TokenType.SEMICOLON.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr[TokenType.ARROW.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr[TokenType.COMMENT.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CodeEditorFragment() {
        final CodeEditorFragment codeEditorFragment = this;
        final Function0 function0 = null;
        this.viewModel = FragmentViewModelLazyKt.createViewModelLazy(codeEditorFragment, Reflection.getOrCreateKotlinClass(LibraryEditorViewModel.class), new Function0<ViewModelStore>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment$special$$inlined$activityViewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                ViewModelStore viewModelStore = codeEditorFragment.requireActivity().getViewModelStore();
                Intrinsics.checkNotNullExpressionValue(viewModelStore, "requireActivity().viewModelStore");
                return viewModelStore;
            }
        }, new Function0<CreationExtras>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment$special$$inlined$activityViewModels$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                if (function1 != null && (creationExtras = (CreationExtras) function1.invoke()) != null) {
                    return creationExtras;
                }
                CreationExtras defaultViewModelCreationExtras = codeEditorFragment.requireActivity().getDefaultViewModelCreationExtras();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelCreationExtras, "requireActivity().defaultViewModelCreationExtras");
                return defaultViewModelCreationExtras;
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment$special$$inlined$activityViewModels$default$3
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                ViewModelProvider.Factory defaultViewModelProviderFactory = codeEditorFragment.requireActivity().getDefaultViewModelProviderFactory();
                Intrinsics.checkNotNullExpressionValue(defaultViewModelProviderFactory, "requireActivity().defaultViewModelProviderFactory");
                return defaultViewModelProviderFactory;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LibraryEditorViewModel getViewModel() {
        return (LibraryEditorViewModel) this.viewModel.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        return inflater.inflate(R.layout.fragment_code_editor, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        View viewFindViewById = view.findViewById(R.id.code_editor_edit_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.editText = (EditText) viewFindViewById;
        View viewFindViewById2 = view.findViewById(R.id.line_numbers_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.lineNumbersView = (TextView) viewFindViewById2;
        EditText editText = this.editText;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        editText.setInputType(655361);
        setupObservers();
        setupTextWatcher();
    }

    private final void setupObservers() {
        getViewModel().getLibraryDraft().observe(getViewLifecycleOwner(), new CodeEditorFragment$sam$androidx_lifecycle_Observer$0(new Function1<LibraryDraft, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment.setupObservers.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LibraryDraft libraryDraft) {
                invoke2(libraryDraft);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LibraryDraft libraryDraft) {
                EditText editText = CodeEditorFragment.this.editText;
                EditText editText2 = null;
                if (editText == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editText");
                    editText = null;
                }
                if (Intrinsics.areEqual(editText.getText().toString(), libraryDraft.getCode())) {
                    return;
                }
                CodeEditorFragment.this.ignoreTextChange = true;
                EditText editText3 = CodeEditorFragment.this.editText;
                if (editText3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("editText");
                } else {
                    editText2 = editText3;
                }
                editText2.setText(libraryDraft.getCode());
                CodeEditorFragment codeEditorFragment = CodeEditorFragment.this;
                List<Token> value = codeEditorFragment.getViewModel().getLexedTokens().getValue();
                if (value == null) {
                    value = CollectionsKt.emptyList();
                }
                codeEditorFragment.applyHighlighting(value, CodeEditorFragment.this.getViewModel().getSyntaxError().getValue());
                CodeEditorFragment.this.updateLineNumbers();
                CodeEditorFragment.this.ignoreTextChange = false;
            }
        }));
        getViewModel().getLexedTokens().observe(getViewLifecycleOwner(), new CodeEditorFragment$sam$androidx_lifecycle_Observer$0(new Function1<List<? extends Token>, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment.setupObservers.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends Token> list) {
                invoke2((List<Token>) list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<Token> list) {
                if (CodeEditorFragment.this.ignoreTextChange) {
                    return;
                }
                CodeEditorFragment codeEditorFragment = CodeEditorFragment.this;
                Intrinsics.checkNotNull(list);
                codeEditorFragment.applyHighlighting(list, CodeEditorFragment.this.getViewModel().getSyntaxError().getValue());
            }
        }));
        getViewModel().getSyntaxError().observe(getViewLifecycleOwner(), new CodeEditorFragment$sam$androidx_lifecycle_Observer$0(new Function1<LunoSyntaxError, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment.setupObservers.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LunoSyntaxError lunoSyntaxError) {
                invoke2(lunoSyntaxError);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LunoSyntaxError lunoSyntaxError) {
                if (CodeEditorFragment.this.ignoreTextChange) {
                    return;
                }
                CodeEditorFragment codeEditorFragment = CodeEditorFragment.this;
                List<Token> value = codeEditorFragment.getViewModel().getLexedTokens().getValue();
                if (value == null) {
                    value = CollectionsKt.emptyList();
                }
                codeEditorFragment.applyHighlighting(value, lunoSyntaxError);
            }
        }));
    }

    private final void setupTextWatcher() {
        EditText editText = this.editText;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment.setupTextWatcher.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!CodeEditorFragment.this.ignoreTextChange && before == 0 && count == 1 && s != null && s.charAt(start) == '\n') {
                    CodeEditorFragment.this.handleAutoIndent(start);
                }
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (CodeEditorFragment.this.ignoreTextChange) {
                    return;
                }
                CodeEditorFragment.this.updateLineNumbers();
                Job job = CodeEditorFragment.this.textChangeJob;
                if (job != null) {
                    Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
                }
                CodeEditorFragment codeEditorFragment = CodeEditorFragment.this;
                codeEditorFragment.textChangeJob = BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(codeEditorFragment), null, null, new CodeEditorFragment$setupTextWatcher$1$afterTextChanged$1(s, CodeEditorFragment.this, null), 3, null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateLineNumbers() {
        EditText editText = this.editText;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        editText.post(new Runnable() { // from class: org.catrobat.catroid.libraryeditor.data.CodeEditorFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CodeEditorFragment.updateLineNumbers$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateLineNumbers$lambda$0(CodeEditorFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.editText;
        TextView textView = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        int lineCount = editText.getLineCount();
        StringBuilder sb = new StringBuilder();
        int i = 1;
        if (1 <= lineCount) {
            while (true) {
                sb.append(i).append("\n");
                if (i == lineCount) {
                    break;
                } else {
                    i++;
                }
            }
        }
        TextView textView2 = this$0.lineNumbersView;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lineNumbersView");
        } else {
            textView = textView2;
        }
        textView.setText(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleAutoIndent(int cursorPosition) {
        EditText editText = this.editText;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        String string = editText.getText().toString();
        String strSubstring = string.substring(StringsKt.lastIndexOf$default((CharSequence) string, '\n', cursorPosition - 1, false, 4, (Object) null) + 1, cursorPosition);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        StringBuilder sb = new StringBuilder();
        int length = strSubstring.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = strSubstring.charAt(i);
            if (cCharAt != ' ' && cCharAt != '\t') {
                break;
            }
            sb.append(cCharAt);
        }
        if (StringsKt.endsWith$default((CharSequence) StringsKt.trim((CharSequence) strSubstring).toString(), '{', false, 2, (Object) null)) {
            sb.append(this.tab);
        }
        if (sb.length() > 0) {
            this.ignoreTextChange = true;
            EditText editText3 = this.editText;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editText");
            } else {
                editText2 = editText3;
            }
            editText2.getText().insert(cursorPosition + 1, sb.toString());
            this.ignoreTextChange = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyHighlighting(List<Token> tokens, LunoSyntaxError error) {
        int iCalculateAbsolutePosition;
        this.ignoreTextChange = true;
        EditText editText = this.editText;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText = null;
        }
        String string = editText.getText().toString();
        String str = string;
        SpannableString spannableString = new SpannableString(str);
        for (Token token : tokens) {
            int colorForToken = getColorForToken(token.getType());
            int iCalculateAbsolutePosition2 = calculateAbsolutePosition(string, token.getLine(), token.getPosition());
            int length = token.getLexeme().length() + iCalculateAbsolutePosition2;
            if (iCalculateAbsolutePosition2 >= 0 && length <= spannableString.length()) {
                spannableString.setSpan(new ForegroundColorSpan(colorForToken), iCalculateAbsolutePosition2, length, 33);
            }
        }
        if (error != null && (iCalculateAbsolutePosition = calculateAbsolutePosition(string, error.getLine(), 0)) >= 0) {
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, '\n', iCalculateAbsolutePosition, false, 4, (Object) null);
            if (iIndexOf$default == -1) {
                iIndexOf$default = string.length();
            }
            spannableString.setSpan(new BackgroundColorSpan(Color.argb(50, 255, 0, 0)), iCalculateAbsolutePosition, iIndexOf$default, 33);
        }
        EditText editText3 = this.editText;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText3 = null;
        }
        int selectionStart = editText3.getSelectionStart();
        EditText editText4 = this.editText;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText4 = null;
        }
        int selectionEnd = editText4.getSelectionEnd();
        EditText editText5 = this.editText;
        if (editText5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
            editText5 = null;
        }
        editText5.setText(spannableString, TextView.BufferType.SPANNABLE);
        EditText editText6 = this.editText;
        if (editText6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editText");
        } else {
            editText2 = editText6;
        }
        editText2.setSelection(selectionStart, selectionEnd);
        this.ignoreTextChange = false;
    }

    private final int calculateAbsolutePosition(String text, int line, int position) {
        int i = 0;
        for (int i2 = 1; i2 < line && i < text.length(); i2++) {
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) text, '\n', i, false, 4, (Object) null);
            if (iIndexOf$default == -1) {
                return -1;
            }
            i = iIndexOf$default + 1;
        }
        int i3 = i + position;
        if (i3 <= text.length()) {
            return i3;
        }
        return -1;
    }

    private final int getColorForToken(TokenType type) {
        switch (WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return Color.parseColor("#CF5717");
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                return Color.parseColor("#8F4CBA");
            case 27:
            case 28:
                return Color.parseColor("#6B9C49");
            case 29:
                return Color.parseColor("#D3D3D3");
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
                return Color.parseColor("#FCCB41");
            case 61:
                return Color.parseColor("#808080");
            default:
                return Color.parseColor("#A9B7C6");
        }
    }
}
