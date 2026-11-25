package io.github.rosemoe.sora.widget.component;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import io.github.rosemoe.sora.I18nConfig;
import io.github.rosemoe.sora.R;
import io.github.rosemoe.sora.event.ColorSchemeUpdateEvent;
import io.github.rosemoe.sora.event.EditorFocusChangeEvent;
import io.github.rosemoe.sora.event.EditorReleaseEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.HoverEvent;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticDetail;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer;
import io.github.rosemoe.sora.lang.diagnostic.Quickfix;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.base.EditorPopupWindow;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: EditorDiagnosticTooltipWindow.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u00108\u001a\u000209H\u0014J\b\u0010:\u001a\u000209H\u0016J\b\u0010;\u001a\u00020+H\u0016J\b\u0010<\u001a\u00020+H\u0014J\b\u0010=\u001a\u000209H\u0002J\u0010\u0010>\u001a\u0002092\u0006\u0010?\u001a\u00020+H\u0016J\u001c\u0010@\u001a\u0002092\b\u0010A\u001a\u0004\u0018\u00010\f2\b\u0010B\u001a\u0004\u0018\u00010\u0019H\u0014J\u0010\u0010@\u001a\u0002092\u0006\u0010C\u001a\u00020\u0019H\u0014J\b\u0010D\u001a\u000209H\u0014J\b\u0010E\u001a\u000209H\u0014R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\f@BX\u0084\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u00020 X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001c\u0010%\u001a\u0004\u0018\u00010\u0019X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u000e\u0010*\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010,\u001a\n \b*\u0004\u0018\u00010-0-X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010.\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\u000201X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0016\u00104\u001a\n \b*\u0004\u0018\u00010-0-X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u00105\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorDiagnosticTooltipWindow;", "Lio/github/rosemoe/sora/widget/base/EditorPopupWindow;", "Lio/github/rosemoe/sora/widget/component/EditorBuiltinComponent;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "briefMessageText", "Landroid/widget/TextView;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "buffer", "", "<set-?>", "Lio/github/rosemoe/sora/lang/diagnostic/DiagnosticDetail;", "currentDiagnostic", "getCurrentDiagnostic", "()Lio/github/rosemoe/sora/lang/diagnostic/DiagnosticDetail;", "detailMessageText", "diagnosticList", "", "Lio/github/rosemoe/sora/lang/diagnostic/DiagnosticRegion;", "eventManager", "Lio/github/rosemoe/sora/event/EventManager;", "getEventManager", "()Lio/github/rosemoe/sora/event/EventManager;", "hoverPosition", "Lio/github/rosemoe/sora/text/CharPosition;", "lastHoverPos", "Lkotlin/Pair;", "", "locationBuffer", "", "maxHeight", "", "getMaxHeight", "()I", "setMaxHeight", "(I)V", "memorizedPosition", "getMemorizedPosition", "()Lio/github/rosemoe/sora/text/CharPosition;", "setMemorizedPosition", "(Lio/github/rosemoe/sora/text/CharPosition;)V", "menuShown", "", "messagePanel", "Landroid/view/ViewGroup;", "moreActionText", "popupHovered", "popupMenu", "Landroid/widget/PopupMenu;", "getPopupMenu", "()Landroid/widget/PopupMenu;", "quickfixPanel", "quickfixText", "rootView", "Landroid/view/View;", "applyColorScheme", "", "dismiss", "isEnabled", "isSelectionVisible", "registerEditorEvents", "setEnabled", CompilerOptions.ENABLED, "updateDiagnostic", "diagnostic", "position", "pos", "updateWindowPosition", "updateWindowSize", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class EditorDiagnosticTooltipWindow extends EditorPopupWindow implements EditorBuiltinComponent {
    private final TextView briefMessageText;
    private final float[] buffer;
    private DiagnosticDetail currentDiagnostic;
    private final TextView detailMessageText;
    private final List<DiagnosticRegion> diagnosticList;
    private final EventManager eventManager;
    private CharPosition hoverPosition;
    private Pair<Float, Float> lastHoverPos;
    private final int[] locationBuffer;
    private int maxHeight;
    private CharPosition memorizedPosition;
    private boolean menuShown;
    private final ViewGroup messagePanel;
    private final TextView moreActionText;
    private boolean popupHovered;
    private final PopupMenu popupMenu;
    private final ViewGroup quickfixPanel;
    private final TextView quickfixText;
    private final View rootView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorDiagnosticTooltipWindow(final CodeEditor editor) {
        super(editor, 6);
        Intrinsics.checkNotNullParameter(editor, "editor");
        EventManager eventManagerCreateSubEventManager = editor.createSubEventManager();
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager, "createSubEventManager(...)");
        this.eventManager = eventManagerCreateSubEventManager;
        View viewInflate = LayoutInflater.from(editor.getContext()).inflate(R.layout.diagnostic_tooltip_window, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        this.rootView = viewInflate;
        this.briefMessageText = (TextView) viewInflate.findViewById(R.id.diagnostic_tooltip_brief_message);
        this.detailMessageText = (TextView) viewInflate.findViewById(R.id.diagnostic_tooltip_detailed_message);
        TextView textView = (TextView) viewInflate.findViewById(R.id.diagnostic_tooltip_preferred_action);
        this.quickfixText = textView;
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.diagnostic_tooltip_more_actions);
        this.moreActionText = textView2;
        this.messagePanel = (ViewGroup) viewInflate.findViewById(R.id.diagnostic_container_message);
        this.quickfixPanel = (ViewGroup) viewInflate.findViewById(R.id.diagnostic_container_quickfix);
        this.maxHeight = (int) (editor.getDpUnit() * 175);
        this.diagnosticList = new ArrayList();
        this.buffer = new float[2];
        this.locationBuffer = new int[2];
        PopupMenu popupMenu = new PopupMenu(editor.getContext(), textView2);
        this.popupMenu = popupMenu;
        Float fValueOf = Float.valueOf(0.0f);
        this.lastHoverPos = TuplesKt.to(fValueOf, fValueOf);
        super.setContentView(viewInflate);
        getPopup().setAnimationStyle(R.style.diagnostic_popup_animation);
        viewInflate.setClipToOutline(true);
        viewInflate.setOnGenericMotionListener(new View.OnGenericMotionListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnGenericMotionListener
            public final boolean onGenericMotion(View view, MotionEvent motionEvent) {
                return EditorDiagnosticTooltipWindow._init_$lambda$0(this.f$0, view, motionEvent);
            }
        });
        registerEditorEvents();
        getPopup().setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda4
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                EditorDiagnosticTooltipWindow._init_$lambda$1(this.f$0);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorDiagnosticTooltipWindow._init_$lambda$2(this.f$0, view);
            }
        });
        textView2.setText(I18nConfig.getResourceId(R.string.sora_editor_diagnostics_more_actions));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorDiagnosticTooltipWindow._init_$lambda$5(this.f$0, editor, view);
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda7
            @Override // android.widget.PopupMenu.OnDismissListener
            public final void onDismiss(PopupMenu popupMenu2) {
                EditorDiagnosticTooltipWindow._init_$lambda$6(this.f$0, popupMenu2);
            }
        });
        applyColorScheme();
    }

    protected final EventManager getEventManager() {
        return this.eventManager;
    }

    protected final CharPosition getMemorizedPosition() {
        return this.memorizedPosition;
    }

    protected final void setMemorizedPosition(CharPosition charPosition) {
        this.memorizedPosition = charPosition;
    }

    protected final DiagnosticDetail getCurrentDiagnostic() {
        return this.currentDiagnostic;
    }

    protected final int getMaxHeight() {
        return this.maxHeight;
    }

    protected final void setMaxHeight(int i) {
        this.maxHeight = i;
    }

    protected final PopupMenu getPopupMenu() {
        return this.popupMenu;
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public boolean isEnabled() {
        return this.eventManager.isEnabled();
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public void setEnabled(boolean enabled) {
        this.eventManager.setEnabled(enabled);
        if (enabled) {
            return;
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$0(EditorDiagnosticTooltipWindow this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this$0.popupHovered = true;
        } else if (actionMasked == 10) {
            this$0.popupHovered = false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(EditorDiagnosticTooltipWindow this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.currentDiagnostic = null;
        this$0.popupHovered = false;
        this$0.menuShown = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$2(EditorDiagnosticTooltipWindow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DiagnosticDetail diagnosticDetail = this$0.currentDiagnostic;
        List<Quickfix> quickfixes = diagnosticDetail != null ? diagnosticDetail.getQuickfixes() : null;
        List<Quickfix> list = quickfixes;
        if (list == null || list.isEmpty()) {
            return;
        }
        quickfixes.get(0).executeQuickfix();
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$5(final EditorDiagnosticTooltipWindow this$0, CodeEditor editor, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(editor, "$editor");
        DiagnosticDetail diagnosticDetail = this$0.currentDiagnostic;
        final List<Quickfix> quickfixes = diagnosticDetail != null ? diagnosticDetail.getQuickfixes() : null;
        List<Quickfix> list = quickfixes;
        if (list == null || list.isEmpty() || quickfixes.size() <= 1) {
            return;
        }
        Menu menu = this$0.popupMenu.getMenu();
        menu.clear();
        int size = quickfixes.size();
        for (int i = 1; i < size; i++) {
            Quickfix quickfix = quickfixes.get(i);
            Context context = editor.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            menu.add(0, i, 0, quickfix.resolveTitle(context));
        }
        this$0.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda8
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return EditorDiagnosticTooltipWindow.lambda$5$lambda$4(quickfixes, this$0, menuItem);
            }
        });
        this$0.popupMenu.show();
        this$0.menuShown = true;
    }

    static final boolean lambda$5$lambda$4(List list, EditorDiagnosticTooltipWindow this$0, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((Quickfix) list.get(menuItem.getItemId())).executeQuickfix();
        this$0.dismiss();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$6(EditorDiagnosticTooltipWindow this$0, PopupMenu popupMenu) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.menuShown = false;
    }

    private final void registerEditorEvents() {
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeEvent(SelectionChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda9
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$7(this.f$0, (SelectionChangeEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeEvent(ScrollEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda10
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$8(this.f$0, (ScrollEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
        final Runnable runnable = new Runnable() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$11(this.f$0);
            }
        };
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeAlways(HoverEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda12
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$12(this.f$0, runnable, (HoverEvent) event);
            }
        }), "subscribeAlways(...)");
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeEvent(ColorSchemeUpdateEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$13(this.f$0, (ColorSchemeUpdateEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeEvent(EditorFocusChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$14(this.f$0, (EditorFocusChangeEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
        Intrinsics.checkNotNullExpressionValue(this.eventManager.subscribeEvent(EditorReleaseEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow$$ExternalSyntheticLambda3
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                EditorDiagnosticTooltipWindow.registerEditorEvents$lambda$15(this.f$0, (EditorReleaseEvent) event, unsubscribe);
            }
        }), "subscribeEvent(...)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$7(EditorDiagnosticTooltipWindow this$0, SelectionChangeEvent event, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        if (!this$0.isEnabled() || this$0.getEditor().isInMouseMode()) {
            return;
        }
        if (event.isSelected() || (event.getCause() != 3 && event.getCause() != 1)) {
            this$0.updateDiagnostic(null, null);
            return;
        }
        CharPosition left = event.getLeft();
        Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
        this$0.updateDiagnostic(left);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$8(EditorDiagnosticTooltipWindow this$0, ScrollEvent scrollEvent, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(scrollEvent, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        if (this$0.getEditor().isInMouseMode() || this$0.currentDiagnostic == null || !this$0.isShowing()) {
            return;
        }
        if (!this$0.isSelectionVisible()) {
            this$0.dismiss();
        } else {
            this$0.updateWindowPosition();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$11(EditorDiagnosticTooltipWindow this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CharPosition charPosition = this$0.hoverPosition;
        if (!this$0.getPopup().isShowing()) {
            if (charPosition != null) {
                this$0.updateDiagnostic(charPosition);
            }
        } else {
            if (this$0.popupHovered || this$0.menuShown || charPosition == null) {
                return;
            }
            this$0.updateDiagnostic(charPosition);
        }
    }

    static /* synthetic */ void registerEditorEvents$postUpdate$default(EditorDiagnosticTooltipWindow editorDiagnosticTooltipWindow, Runnable runnable, long j, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerEditorEvents$postUpdate");
        }
        if ((i & 4) != 0) {
            j = 1000;
        }
        registerEditorEvents$postUpdate(editorDiagnosticTooltipWindow, runnable, j);
    }

    private static final void registerEditorEvents$postUpdate(EditorDiagnosticTooltipWindow editorDiagnosticTooltipWindow, Runnable runnable, long j) {
        editorDiagnosticTooltipWindow.getEditor().removeCallbacks(runnable);
        editorDiagnosticTooltipWindow.getEditor().postDelayedInLifecycle(runnable, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$12(EditorDiagnosticTooltipWindow this$0, Runnable callback, HoverEvent hoverEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(callback, "$callback");
        if (this$0.getEditor().isInMouseMode()) {
            int action = hoverEvent.getCausingEvent().getAction();
            if (action != 7) {
                if (action == 9) {
                    this$0.getEditor().removeCallbacks(callback);
                    this$0.updateDiagnostic(null, null);
                    registerEditorEvents$lambda$12$updateLastHover(this$0, hoverEvent);
                    return;
                } else {
                    if (action != 10) {
                        return;
                    }
                    this$0.hoverPosition = null;
                    if (this$0.popupHovered || this$0.menuShown) {
                        return;
                    }
                    registerEditorEvents$postUpdate$default(this$0, callback, 0L, 4, null);
                    registerEditorEvents$lambda$12$updateLastHover(this$0, hoverEvent);
                    return;
                }
            }
            if (this$0.popupHovered || this$0.menuShown) {
                return;
            }
            if (!this$0.getEditor().isScreenPointOnText(hoverEvent.getX(), hoverEvent.getY())) {
                this$0.hoverPosition = null;
                registerEditorEvents$postUpdate$default(this$0, callback, 0L, 4, null);
            } else if (Math.abs(hoverEvent.getX() - this$0.lastHoverPos.getFirst().floatValue()) > 20.0f || Math.abs(hoverEvent.getY() - this$0.lastHoverPos.getSecond().floatValue()) > 20.0f) {
                registerEditorEvents$lambda$12$updateLastHover(this$0, hoverEvent);
                long pointPositionOnScreen = this$0.getEditor().getPointPositionOnScreen(hoverEvent.getX(), hoverEvent.getY());
                this$0.hoverPosition = this$0.getEditor().getText().getIndexer().getCharPosition(IntPair.getFirst(pointPositionOnScreen), IntPair.getSecond(pointPositionOnScreen));
                registerEditorEvents$postUpdate$default(this$0, callback, 0L, 4, null);
            }
        }
    }

    private static final void registerEditorEvents$lambda$12$updateLastHover(EditorDiagnosticTooltipWindow editorDiagnosticTooltipWindow, HoverEvent hoverEvent) {
        editorDiagnosticTooltipWindow.lastHoverPos = TuplesKt.to(Float.valueOf(hoverEvent.getX()), Float.valueOf(hoverEvent.getY()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$13(EditorDiagnosticTooltipWindow this$0, ColorSchemeUpdateEvent colorSchemeUpdateEvent, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(colorSchemeUpdateEvent, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        this$0.applyColorScheme();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$14(EditorDiagnosticTooltipWindow this$0, EditorFocusChangeEvent event, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        if (event.getIsGainFocus()) {
            return;
        }
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerEditorEvents$lambda$15(EditorDiagnosticTooltipWindow this$0, EditorReleaseEvent editorReleaseEvent, Unsubscribe unsubscribe) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(editorReleaseEvent, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(unsubscribe, "<anonymous parameter 1>");
        this$0.setEnabled(false);
    }

    @Override // io.github.rosemoe.sora.widget.base.EditorPopupWindow
    public void dismiss() {
        if (isShowing()) {
            Thread.dumpStack();
            super.dismiss();
        }
    }

    protected void updateDiagnostic(CharPosition pos) {
        Intrinsics.checkNotNullParameter(pos, "pos");
        DiagnosticsContainer diagnostics = getEditor().getDiagnostics();
        if (diagnostics != null) {
            diagnostics.queryInRegion(this.diagnosticList, pos.index - 1, pos.index + 1);
            if (!this.diagnosticList.isEmpty()) {
                int i = 0;
                int i2 = this.diagnosticList.get(0).endIndex - this.diagnosticList.get(0).startIndex;
                int size = this.diagnosticList.size();
                for (int i3 = 1; i3 < size; i3++) {
                    int i4 = this.diagnosticList.get(i3).endIndex - this.diagnosticList.get(i3).startIndex;
                    if (i4 < i2) {
                        i2 = i4;
                        i = i3;
                    }
                }
                updateDiagnostic(this.diagnosticList.get(i).detail, pos);
                CodeEditor editor = getEditor();
                Intrinsics.checkNotNullExpressionValue(editor, "getEditor(...)");
                EditorBuiltinComponent component = editor.getComponent(EditorAutoCompletion.class);
                Intrinsics.checkNotNullExpressionValue(component, "getComponent(...)");
                if (!((EditorAutoCompletion) component).isCompletionInProgress()) {
                    show();
                }
            } else {
                updateDiagnostic(null, null);
            }
            this.diagnosticList.clear();
            return;
        }
        updateDiagnostic(null, null);
    }

    protected void applyColorScheme() {
        EditorColorScheme colorScheme = getEditor().getColorScheme();
        Intrinsics.checkNotNullExpressionValue(colorScheme, "getColorScheme(...)");
        this.briefMessageText.setTextColor(colorScheme.getColor(54));
        this.detailMessageText.setTextColor(colorScheme.getColor(55));
        this.quickfixText.setTextColor(colorScheme.getColor(56));
        this.moreActionText.setTextColor(colorScheme.getColor(56));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(getEditor().getDpUnit() * 5);
        gradientDrawable.setColor(colorScheme.getColor(53));
        this.rootView.setBackground(gradientDrawable);
    }

    protected boolean isSelectionVisible() {
        CharPosition charPositionLeft = getEditor().getCursor().left();
        Intrinsics.checkNotNullExpressionValue(charPositionLeft, "left(...)");
        getEditor().getLayout().getCharLayoutOffset(charPositionLeft.line, charPositionLeft.column, this.buffer);
        return this.buffer[0] >= ((float) getEditor().getOffsetY()) && this.buffer[0] - ((float) getEditor().getRowHeight()) <= ((float) (getEditor().getOffsetY() + getEditor().getHeight())) && this.buffer[1] >= ((float) getEditor().getOffsetX()) && this.buffer[1] - 100.0f <= ((float) (getEditor().getOffsetX() + getEditor().getWidth()));
    }

    protected void updateDiagnostic(DiagnosticDetail diagnostic, CharPosition position) {
        if (isEnabled()) {
            if (Intrinsics.areEqual(diagnostic, this.currentDiagnostic)) {
                if (diagnostic == null || getEditor().isInMouseMode()) {
                    return;
                }
                updateWindowPosition();
                return;
            }
            this.currentDiagnostic = diagnostic;
            this.memorizedPosition = position;
            if (diagnostic == null) {
                dismiss();
                return;
            }
            TextView textView = this.briefMessageText;
            CharSequence briefMessage = diagnostic.getBriefMessage();
            if (StringsKt.isBlank(briefMessage)) {
            }
            textView.setText(briefMessage);
            CharSequence detailedMessage = diagnostic.getDetailedMessage();
            if (detailedMessage != null) {
                this.detailMessageText.setText(detailedMessage);
                this.detailMessageText.setVisibility(0);
            } else {
                this.detailMessageText.setVisibility(8);
            }
            List<Quickfix> quickfixes = diagnostic.getQuickfixes();
            List<Quickfix> list = quickfixes;
            if (list == null || list.isEmpty()) {
                this.quickfixPanel.setVisibility(8);
            } else {
                this.quickfixPanel.setVisibility(0);
                TextView textView2 = this.quickfixText;
                Quickfix quickfix = quickfixes.get(0);
                Context context = getEditor().getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                textView2.setText(quickfix.resolveTitle(context));
                this.moreActionText.setVisibility(quickfixes.size() > 1 ? 0 : 8);
            }
            updateWindowSize();
            updateWindowPosition();
        }
    }

    protected void updateWindowSize() {
        int measuredHeight;
        int iCoerceAtMost;
        int width = (int) (((double) getEditor().getWidth()) * 0.9d);
        if (this.quickfixPanel.getVisibility() == 0) {
            this.quickfixPanel.measure(View.MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(114514, Integer.MIN_VALUE));
            measuredHeight = this.quickfixPanel.getMeasuredHeight();
            iCoerceAtMost = RangesKt.coerceAtMost(this.quickfixPanel.getMeasuredWidth(), width);
        } else {
            measuredHeight = 0;
            iCoerceAtMost = 0;
        }
        int iCoerceAtLeast = RangesKt.coerceAtLeast(this.maxHeight - measuredHeight, 1);
        this.messagePanel.getLayoutParams().height = -2;
        this.messagePanel.measure(View.MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(iCoerceAtLeast, Integer.MIN_VALUE));
        int iCoerceAtMost2 = RangesKt.coerceAtMost(this.messagePanel.getMeasuredHeight(), iCoerceAtLeast);
        int iCoerceAtMost3 = RangesKt.coerceAtMost(this.messagePanel.getMeasuredWidth(), width);
        this.messagePanel.getLayoutParams().height = iCoerceAtMost2;
        setSize(Math.max(iCoerceAtMost, iCoerceAtMost3), measuredHeight + iCoerceAtMost2);
    }

    protected void updateWindowPosition() {
        float height;
        CharPosition charPosition = this.memorizedPosition;
        if (charPosition == null) {
            return;
        }
        float charOffsetX = getEditor().getCharOffsetX(charPosition.line, charPosition.column);
        float charOffsetY = getEditor().getCharOffsetY(charPosition.line, charPosition.column) - getEditor().getRowHeight();
        getEditor().getLocationInWindow(this.locationBuffer);
        float f = this.locationBuffer[1] + charOffsetY;
        float height2 = (getEditor().getHeight() - charOffsetY) - getEditor().getRowHeight();
        CodeEditor editor = getEditor();
        Intrinsics.checkNotNullExpressionValue(editor, "getEditor(...)");
        EditorBuiltinComponent component = editor.getComponent(EditorAutoCompletion.class);
        Intrinsics.checkNotNullExpressionValue(component, "getComponent(...)");
        boolean zIsShowing = ((EditorAutoCompletion) component).isShowing();
        if (f > height2 || zIsShowing) {
            height = charOffsetY - getHeight();
        } else {
            height = charOffsetY + (getEditor().getRowHeight() * 1.5f);
        }
        if (zIsShowing && height < 0.0f) {
            dismiss();
        } else {
            setLocationAbsolutely((int) RangesKt.coerceAtLeast(charOffsetX - (getWidth() / 2), 0.0f), (int) height);
        }
    }
}
