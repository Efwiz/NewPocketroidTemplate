package io.github.rosemoe.sora.widget.component;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.github.rosemoe.sora.event.CreateContextMenuEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: EditorContextMenuCreator.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001:\u0004\u001a\u001b\u001c\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0017\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\f0\u0010¢\u0006\u0002\b\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001e"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator;", "Lio/github/rosemoe/sora/widget/component/EditorBuiltinComponent;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "(Lio/github/rosemoe/sora/widget/CodeEditor;)V", "getEditor", "()Lio/github/rosemoe/sora/widget/CodeEditor;", "eventManager", "Lio/github/rosemoe/sora/event/EventManager;", "getEventManager", "()Lio/github/rosemoe/sora/event/EventManager;", "buildMenu", "", LineReader.MENU, "Landroid/view/ContextMenu;", "builder", "Lkotlin/Function1;", "Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$ContextMenuBuilder;", "Lkotlin/ExtensionFunctionType;", "isEnabled", "", "onCreateContextMenu", "event", "Lio/github/rosemoe/sora/event/CreateContextMenuEvent;", "setEnabled", CompilerOptions.ENABLED, "ContextMenuBuilder", "MenuBuilder", "MenuItemBuilder", "SubMenuBuilder", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class EditorContextMenuCreator implements EditorBuiltinComponent {
    private final CodeEditor editor;
    private final EventManager eventManager;

    public EditorContextMenuCreator(CodeEditor editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        this.editor = editor;
        EventManager eventManagerCreateSubEventManager = editor.createSubEventManager();
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager, "createSubEventManager(...)");
        this.eventManager = eventManagerCreateSubEventManager;
        Intrinsics.checkNotNullExpressionValue(eventManagerCreateSubEventManager.subscribeAlways(CreateContextMenuEvent.class, new EventManager.NoUnsubscribeReceiver() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventManager.NoUnsubscribeReceiver
            public final void onReceive(Event event) {
                this.f$0.onCreateContextMenu((CreateContextMenuEvent) event);
            }
        }), "subscribeAlways(...)");
    }

    public final CodeEditor getEditor() {
        return this.editor;
    }

    public final EventManager getEventManager() {
        return this.eventManager;
    }

    public void onCreateContextMenu(CreateContextMenuEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        buildMenu(event.getMenu(), new Function1<ContextMenuBuilder, Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ContextMenuBuilder contextMenuBuilder) {
                invoke2(contextMenuBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ContextMenuBuilder buildMenu) {
                Intrinsics.checkNotNullParameter(buildMenu, "$this$buildMenu");
                final EditorContextMenuCreator editorContextMenuCreator = EditorContextMenuCreator.this;
                buildMenu.item(new Function1<MenuItemBuilder, Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(MenuItemBuilder menuItemBuilder) {
                        invoke2(menuItemBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(MenuItemBuilder item) {
                        Intrinsics.checkNotNullParameter(item, "$this$item");
                        item.setTitleRes(R.string.copy);
                        item.setEnabled(editorContextMenuCreator.getEditor().isTextSelected());
                        final EditorContextMenuCreator editorContextMenuCreator2 = editorContextMenuCreator;
                        item.onClick(new Function0<Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.1.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                editorContextMenuCreator2.getEditor().copyText();
                            }
                        });
                    }
                });
                final EditorContextMenuCreator editorContextMenuCreator2 = EditorContextMenuCreator.this;
                buildMenu.item(new Function1<MenuItemBuilder, Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(MenuItemBuilder menuItemBuilder) {
                        invoke2(menuItemBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(MenuItemBuilder item) {
                        Intrinsics.checkNotNullParameter(item, "$this$item");
                        item.setTitleRes(R.string.cut);
                        item.setEnabled(editorContextMenuCreator2.getEditor().isTextSelected());
                        final EditorContextMenuCreator editorContextMenuCreator3 = editorContextMenuCreator2;
                        item.onClick(new Function0<Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.2.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                editorContextMenuCreator3.getEditor().cutText();
                            }
                        });
                    }
                });
                final EditorContextMenuCreator editorContextMenuCreator3 = EditorContextMenuCreator.this;
                buildMenu.item(new Function1<MenuItemBuilder, Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(MenuItemBuilder menuItemBuilder) {
                        invoke2(menuItemBuilder);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(MenuItemBuilder item) {
                        Intrinsics.checkNotNullParameter(item, "$this$item");
                        item.setTitleRes(R.string.paste);
                        item.setEnabled(editorContextMenuCreator3.getEditor().hasClip());
                        final EditorContextMenuCreator editorContextMenuCreator4 = editorContextMenuCreator3;
                        item.onClick(new Function0<Unit>() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.onCreateContextMenu.1.3.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                editorContextMenuCreator4.getEditor().pasteText();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public void setEnabled(boolean enabled) {
        this.eventManager.setEnabled(enabled);
    }

    /* JADX INFO: compiled from: EditorContextMenuCreator.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000e\u001a\u00020\u000fH\u0010¢\u0006\u0002\b\u0010J\u001f\u0010\u0011\u001a\u00020\u000f2\u0017\u0010\u0012\u001a\u0013\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u0013¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u00020\u000f2\u0017\u0010\u0012\u001a\u0013\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u000f0\u0013¢\u0006\u0002\b\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$MenuBuilder;", "", "context", "Landroid/content/Context;", LineReader.MENU, "Landroid/view/Menu;", "(Landroid/content/Context;Landroid/view/Menu;)V", "getContext", "()Landroid/content/Context;", FirebaseAnalytics.Param.ITEMS, "", "Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$MenuItemBuilder;", "getMenu", "()Landroid/view/Menu;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "build$editor_release", "item", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "subMenu", "Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$SubMenuBuilder;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class MenuBuilder {
        private final Context context;
        private final List<MenuItemBuilder> items;
        private final Menu menu;

        public MenuBuilder(Context context, Menu menu) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(menu, "menu");
            this.context = context;
            this.menu = menu;
            this.items = new ArrayList();
        }

        public final Context getContext() {
            return this.context;
        }

        public final Menu getMenu() {
            return this.menu;
        }

        public final void item(Function1<? super MenuItemBuilder, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            List<MenuItemBuilder> list = this.items;
            MenuItemBuilder menuItemBuilder = new MenuItemBuilder(this.context);
            builder.invoke(menuItemBuilder);
            list.add(menuItemBuilder);
        }

        public final void subMenu(Function1<? super SubMenuBuilder, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            List<MenuItemBuilder> list = this.items;
            SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.context);
            builder.invoke(subMenuBuilder);
            list.add(subMenuBuilder);
        }

        public void build$editor_release() {
            Iterator<T> it = this.items.iterator();
            while (it.hasNext()) {
                ((MenuItemBuilder) it.next()).build$editor_release(this.menu);
            }
        }
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public boolean isEnabled() {
        return this.eventManager.isEnabled();
    }

    /* JADX INFO: compiled from: EditorContextMenuCreator.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u0016\u001a\u00020\u0017H\u0010¢\u0006\u0002\b\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0019"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$ContextMenuBuilder;", "Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$MenuBuilder;", "context", "Landroid/content/Context;", "contextMenu", "Landroid/view/ContextMenu;", "(Landroid/content/Context;Landroid/view/ContextMenu;)V", "getContextMenu", "()Landroid/view/ContextMenu;", "headerTitle", "", "getHeaderTitle", "()Ljava/lang/CharSequence;", "setHeaderTitle", "(Ljava/lang/CharSequence;)V", "value", "", "headerTitleRes", "getHeaderTitleRes", "()I", "setHeaderTitleRes", "(I)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "build$editor_release", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class ContextMenuBuilder extends MenuBuilder {
        private final ContextMenu contextMenu;
        private CharSequence headerTitle;
        private int headerTitleRes;

        public final ContextMenu getContextMenu() {
            return this.contextMenu;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ContextMenuBuilder(Context context, ContextMenu contextMenu) {
            super(context, contextMenu);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(contextMenu, "contextMenu");
            this.contextMenu = contextMenu;
        }

        public final CharSequence getHeaderTitle() {
            return this.headerTitle;
        }

        public final void setHeaderTitle(CharSequence charSequence) {
            this.headerTitle = charSequence;
        }

        public final int getHeaderTitleRes() {
            return this.headerTitleRes;
        }

        public final void setHeaderTitleRes(int i) {
            this.headerTitle = getContext().getString(i);
        }

        @Override // io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.MenuBuilder
        public void build$editor_release() {
            super.build$editor_release();
            CharSequence charSequence = this.headerTitle;
            if (charSequence != null) {
                this.contextMenu.setHeaderTitle(charSequence);
            }
        }
    }

    /* JADX INFO: compiled from: EditorContextMenuCreator.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0010¢\u0006\u0002\b\u0018J\u001f\u0010\u0019\u001a\u00020\u00152\u0017\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00150\u001b¢\u0006\u0002\b\u001cJ\u001f\u0010\u001d\u001a\u00020\u00152\u0017\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00150\u001b¢\u0006\u0002\b\u001cR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR$\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$SubMenuBuilder;", "Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$MenuItemBuilder;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "headerTitle", "", "getHeaderTitle", "()Ljava/lang/CharSequence;", "setHeaderTitle", "(Ljava/lang/CharSequence;)V", "value", "", "headerTitleRes", "getHeaderTitleRes", "()I", "setHeaderTitleRes", "(I)V", FirebaseAnalytics.Param.ITEMS, "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", LineReader.MENU, "Landroid/view/Menu;", "build$editor_release", "item", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "subMenu", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class SubMenuBuilder extends MenuItemBuilder {
        private CharSequence headerTitle;
        private int headerTitleRes;
        private final List<MenuItemBuilder> items;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SubMenuBuilder(Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.items = new ArrayList();
        }

        public final CharSequence getHeaderTitle() {
            return this.headerTitle;
        }

        public final void setHeaderTitle(CharSequence charSequence) {
            this.headerTitle = charSequence;
        }

        public final int getHeaderTitleRes() {
            return this.headerTitleRes;
        }

        public final void setHeaderTitleRes(int i) {
            this.headerTitle = getContext().getString(i);
        }

        public final void item(Function1<? super MenuItemBuilder, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            List<MenuItemBuilder> list = this.items;
            MenuItemBuilder menuItemBuilder = new MenuItemBuilder(getContext());
            builder.invoke(menuItemBuilder);
            list.add(menuItemBuilder);
        }

        public final void subMenu(Function1<? super SubMenuBuilder, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            List<MenuItemBuilder> list = this.items;
            SubMenuBuilder subMenuBuilder = new SubMenuBuilder(getContext());
            builder.invoke(subMenuBuilder);
            list.add(subMenuBuilder);
        }

        @Override // io.github.rosemoe.sora.widget.component.EditorContextMenuCreator.MenuItemBuilder
        public void build$editor_release(Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            SubMenu subMenuAddSubMenu = menu.addSubMenu(getGroupId(), getItemId(), getOrder(), getTitle());
            if (getIconRes() != 0) {
                subMenuAddSubMenu.setIcon(getIconRes());
            } else if (getIcon() != null) {
                subMenuAddSubMenu.setIcon(getIcon());
            }
            CharSequence charSequence = this.headerTitle;
            if (charSequence != null) {
                subMenuAddSubMenu.setHeaderTitle(charSequence);
            }
            for (MenuItemBuilder menuItemBuilder : this.items) {
                Intrinsics.checkNotNull(subMenuAddSubMenu);
                menuItemBuilder.build$editor_release(subMenuAddSubMenu);
            }
        }
    }

    /* JADX INFO: compiled from: EditorContextMenuCreator.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0010¢\u0006\u0002\b5J\u0014\u0010\u001e\u001a\u0002022\f\u00106\u001a\b\u0012\u0004\u0012\u00020207R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\fR\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\n\"\u0004\b\u001d\u0010\fR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\n\"\u0004\b&\u0010\fR\u001a\u0010'\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R$\u0010.\u001a\u00020\b2\u0006\u0010-\u001a\u00020\b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\n\"\u0004\b0\u0010\f¨\u00068"}, d2 = {"Lio/github/rosemoe/sora/widget/component/EditorContextMenuCreator$MenuItemBuilder;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "groupId", "", "getGroupId", "()I", "setGroupId", "(I)V", "icon", "Landroid/graphics/drawable/Drawable;", "getIcon", "()Landroid/graphics/drawable/Drawable;", "setIcon", "(Landroid/graphics/drawable/Drawable;)V", "iconRes", "getIconRes", "setIconRes", "isEnabled", "", "()Z", "setEnabled", "(Z)V", "itemId", "getItemId", "setItemId", "onClick", "Landroid/view/MenuItem$OnMenuItemClickListener;", "getOnClick", "()Landroid/view/MenuItem$OnMenuItemClickListener;", "setOnClick", "(Landroid/view/MenuItem$OnMenuItemClickListener;)V", "order", "getOrder", "setOrder", "title", "", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "value", "titleRes", "getTitleRes", "setTitleRes", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", LineReader.MENU, "Landroid/view/Menu;", "build$editor_release", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lkotlin/Function0;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class MenuItemBuilder {
        private final Context context;
        private int groupId;
        private Drawable icon;
        private int iconRes;
        private boolean isEnabled;
        private int itemId;
        private MenuItem.OnMenuItemClickListener onClick;
        private int order;
        private String title;
        private int titleRes;

        public MenuItemBuilder(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.context = context;
            this.title = "";
            this.isEnabled = true;
        }

        public final Context getContext() {
            return this.context;
        }

        public final int getGroupId() {
            return this.groupId;
        }

        public final void setGroupId(int i) {
            this.groupId = i;
        }

        public final int getItemId() {
            return this.itemId;
        }

        public final void setItemId(int i) {
            this.itemId = i;
        }

        public final int getOrder() {
            return this.order;
        }

        public final void setOrder(int i) {
            this.order = i;
        }

        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.title = str;
        }

        /* JADX INFO: renamed from: isEnabled, reason: from getter */
        public final boolean getIsEnabled() {
            return this.isEnabled;
        }

        public final void setEnabled(boolean z) {
            this.isEnabled = z;
        }

        public final Drawable getIcon() {
            return this.icon;
        }

        public final void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public final int getIconRes() {
            return this.iconRes;
        }

        public final void setIconRes(int i) {
            this.iconRes = i;
        }

        public final int getTitleRes() {
            return this.titleRes;
        }

        public final void setTitleRes(int i) {
            String string = this.context.getString(i);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            this.title = string;
        }

        public final MenuItem.OnMenuItemClickListener getOnClick() {
            return this.onClick;
        }

        public final void setOnClick(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            this.onClick = onMenuItemClickListener;
        }

        public final void onClick(final Function0<Unit> listener) {
            Intrinsics.checkNotNullParameter(listener, "listener");
            this.onClick = new MenuItem.OnMenuItemClickListener() { // from class: io.github.rosemoe.sora.widget.component.EditorContextMenuCreator$MenuItemBuilder$$ExternalSyntheticLambda0
                @Override // android.view.MenuItem.OnMenuItemClickListener
                public final boolean onMenuItemClick(MenuItem menuItem) {
                    return EditorContextMenuCreator.MenuItemBuilder.onClick$lambda$0(listener, menuItem);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean onClick$lambda$0(Function0 listener, MenuItem it) {
            Intrinsics.checkNotNullParameter(listener, "$listener");
            Intrinsics.checkNotNullParameter(it, "it");
            listener.invoke();
            return true;
        }

        public void build$editor_release(Menu menu) {
            Intrinsics.checkNotNullParameter(menu, "menu");
            MenuItem onMenuItemClickListener = menu.add(this.groupId, this.itemId, this.order, this.title).setEnabled(this.isEnabled).setOnMenuItemClickListener(this.onClick);
            int i = this.iconRes;
            if (i != 0) {
                onMenuItemClickListener.setIcon(i);
                return;
            }
            Drawable drawable = this.icon;
            if (drawable != null) {
                onMenuItemClickListener.setIcon(drawable);
            }
        }
    }

    public final void buildMenu(ContextMenu menu, Function1<? super ContextMenuBuilder, Unit> builder) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Context context = this.editor.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        ContextMenuBuilder contextMenuBuilder = new ContextMenuBuilder(context, menu);
        builder.invoke(contextMenuBuilder);
        contextMenuBuilder.build$editor_release();
    }
}
