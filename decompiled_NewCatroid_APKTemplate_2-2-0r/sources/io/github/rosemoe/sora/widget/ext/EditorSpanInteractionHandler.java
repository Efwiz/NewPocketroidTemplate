package io.github.rosemoe.sora.widget.ext;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import io.github.rosemoe.sora.event.ClickEvent;
import io.github.rosemoe.sora.event.DoubleClickEvent;
import io.github.rosemoe.sora.event.EditorMotionEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.LongPressEvent;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.span.SpanClickableUrl;
import io.github.rosemoe.sora.lang.styling.span.SpanInteractionInfo;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.RegionResolverKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: EditorSpanInteractionHandler.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J]\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u00150\u00102\u001e\u0010\u0016\u001a\u001a\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00150\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u0015H\u0002J \u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0019H\u0016J \u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0019H\u0016J \u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u0019H\u0016J\u0006\u0010 \u001a\u00020\u0015J\u000e\u0010!\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006#"}, d2 = {"Lio/github/rosemoe/sora/widget/ext/EditorSpanInteractionHandler;", "", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "getEditor", "()Lio/github/rosemoe/sora/widget/CodeEditor;", "eventManager", "Lio/github/rosemoe/sora/event/EventManager;", "getEventManager", "()Lio/github/rosemoe/sora/event/EventManager;", "handleInteractionEvent", "", "event", "Lio/github/rosemoe/sora/event/EditorMotionEvent;", "predicate", "Lkotlin/Function1;", "Lio/github/rosemoe/sora/lang/styling/span/SpanInteractionInfo;", "Lkotlin/ParameterName;", "name", "interactionInfo", "", "handler", "Lkotlin/Function3;", "Lio/github/rosemoe/sora/lang/styling/Span;", "Lio/github/rosemoe/sora/text/TextRange;", "checkCursorRange", "handleSpanClick", "span", "spanRange", "handleSpanDoubleClick", "handleSpanLongClick", "isEnabled", "setEnabled", CompilerOptions.ENABLED, "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class EditorSpanInteractionHandler {
    private final CodeEditor editor;
    private final EventManager eventManager;

    public boolean handleSpanClick(Span span, SpanInteractionInfo interactionInfo, TextRange spanRange) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(interactionInfo, "interactionInfo");
        Intrinsics.checkNotNullParameter(spanRange, "spanRange");
        return false;
    }

    public boolean handleSpanLongClick(Span span, SpanInteractionInfo interactionInfo, TextRange spanRange) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(interactionInfo, "interactionInfo");
        Intrinsics.checkNotNullParameter(spanRange, "spanRange");
        return false;
    }

    public EditorSpanInteractionHandler(CodeEditor editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        this.editor = editor;
        EventManager eventManagerCreateSubEventManager = editor.createSubEventManager();
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager, "createSubEventManager(...)");
        this.eventManager = eventManagerCreateSubEventManager;
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager.subscribeAlways(ClickEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.ext.EditorSpanInteractionHandler$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                EditorSpanInteractionHandler._init_$lambda$0(this.f$0, (ClickEvent) event);
            }
        }), "subscribeAlways(...)");
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager.subscribeAlways(DoubleClickEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.ext.EditorSpanInteractionHandler$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                EditorSpanInteractionHandler._init_$lambda$1(this.f$0, (DoubleClickEvent) event);
            }
        }), "subscribeAlways(...)");
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager.subscribeAlways(LongPressEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.ext.EditorSpanInteractionHandler$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                EditorSpanInteractionHandler._init_$lambda$2(this.f$0, (LongPressEvent) event);
            }
        }), "subscribeAlways(...)");
    }

    public final CodeEditor getEditor() {
        return this.editor;
    }

    public final EventManager getEventManager() {
        return this.eventManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(EditorSpanInteractionHandler this$0, ClickEvent clickEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (!clickEvent.isFromMouse() || (clickEvent.isFromMouse() && this$0.editor.getKeyMetaStates().isCtrlPressed())) {
            Intrinsics.checkNotNull(clickEvent);
            this$0.handleInteractionEvent(clickEvent, EditorSpanInteractionHandler$1$1.INSTANCE, new EditorSpanInteractionHandler$1$2(this$0), !clickEvent.isFromMouse());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$1(EditorSpanInteractionHandler this$0, DoubleClickEvent doubleClickEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(doubleClickEvent);
        this$0.handleInteractionEvent(doubleClickEvent, EditorSpanInteractionHandler$2$1.INSTANCE, new EditorSpanInteractionHandler$2$2(this$0), !doubleClickEvent.isFromMouse());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$2(EditorSpanInteractionHandler this$0, LongPressEvent longPressEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(longPressEvent);
        this$0.handleInteractionEvent(longPressEvent, EditorSpanInteractionHandler$3$1.INSTANCE, new EditorSpanInteractionHandler$3$2(this$0), !longPressEvent.isFromMouse());
    }

    static /* synthetic */ void handleInteractionEvent$default(EditorSpanInteractionHandler editorSpanInteractionHandler, EditorMotionEvent editorMotionEvent, Function1 function1, Function3 function3, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: handleInteractionEvent");
        }
        if ((i & 8) != 0) {
            z = true;
        }
        editorSpanInteractionHandler.handleInteractionEvent(editorMotionEvent, function1, function3, z);
    }

    private final void handleInteractionEvent(EditorMotionEvent event, Function1<? super SpanInteractionInfo, Boolean> predicate, Function3<? super Span, ? super SpanInteractionInfo, ? super TextRange, Boolean> handler, boolean checkCursorRange) {
        SpanInteractionInfo spanInteractionInfo;
        CodeEditor codeEditor = this.editor;
        MotionEvent causingEvent = event.getCausingEvent();
        Intrinsics.checkNotNullExpressionValue(causingEvent, "getCausingEvent(...)");
        long jResolveTouchRegion = RegionResolverKt.resolveTouchRegion(codeEditor, causingEvent);
        Span span = event.getSpan();
        TextRange spanRange = event.getSpanRange();
        if (IntPair.getFirst(jResolveTouchRegion) != 5 || IntPair.getSecond(jResolveTouchRegion) != 0 || span == null || spanRange == null) {
            return;
        }
        if ((!checkCursorRange || spanRange.isPositionInside(this.editor.getCursor().left())) && (spanInteractionInfo = (SpanInteractionInfo) span.getSpanExt(2)) != null && predicate.invoke(spanInteractionInfo).booleanValue() && handler.invoke(span, spanInteractionInfo, spanRange).booleanValue()) {
            event.intercept();
        }
    }

    public boolean handleSpanDoubleClick(Span span, SpanInteractionInfo interactionInfo, TextRange spanRange) {
        Object objM2898constructorimpl;
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(interactionInfo, "interactionInfo");
        Intrinsics.checkNotNullParameter(spanRange, "spanRange");
        if (!(interactionInfo instanceof SpanClickableUrl)) {
            return false;
        }
        String link = ((SpanClickableUrl) interactionInfo).getLink();
        try {
            Result.Companion companion = Result.INSTANCE;
            EditorSpanInteractionHandler editorSpanInteractionHandler = this;
            objM2898constructorimpl = Result.m2898constructorimpl(Uri.parse(link));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        if (!Result.m2905isSuccessimpl(objM2898constructorimpl)) {
            return true;
        }
        this.editor.getContext().startActivity(new Intent("android.intent.action.VIEW", (Uri) objM2898constructorimpl));
        return true;
    }

    public final boolean isEnabled() {
        return this.eventManager.isEnabled();
    }

    public final void setEnabled(boolean enabled) {
        this.eventManager.setEnabled(enabled);
    }
}
