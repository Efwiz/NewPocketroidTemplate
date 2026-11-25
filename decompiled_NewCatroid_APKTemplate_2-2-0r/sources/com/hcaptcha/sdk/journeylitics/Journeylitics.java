package com.hcaptcha.sdk.journeylitics;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class Journeylitics {
    private static final long SCROLL_EVENT_MIN_INTERVAL_MS = 250;
    private static Application sApp;
    private static final AtomicBoolean STARTED = new AtomicBoolean(false);
    private static JLConfig sConfig = JLConfig.DEFAULT;
    private static final CopyOnWriteArrayList<JLSink> SINKS = new CopyOnWriteArrayList<>();
    private static final WeakHashMap<View, Boolean> INSTRUMENTED = new WeakHashMap<>();
    private static final WeakHashMap<View, Long> LAST_SCROLL_EVENT_AT = new WeakHashMap<>();
    private static final Application.ActivityLifecycleCallbacks LIFECYCLE_CALLBACKS = new Application.ActivityLifecycleCallbacks() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.1
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (Journeylitics.sConfig.isEnableScreens()) {
                Journeylitics.emit(EventKind.screen, activity.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.SCREEN, activity.getClass().getSimpleName()), new AbstractMap.SimpleEntry(FieldKey.ACTION, "disappear")));
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(final Activity activity) {
            if (Journeylitics.sConfig.isEnableScreens()) {
                Journeylitics.emit(EventKind.screen, activity.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.SCREEN, activity.getClass().getSimpleName()), new AbstractMap.SimpleEntry(FieldKey.ACTION, "appear")));
            }
            ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
            if (viewGroup != null) {
                viewGroup.post(new Runnable() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Journeylitics.instrumentActivityViews(activity);
                    }
                });
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    };

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public static final class ListenerLookup<T> {
        private final T listener;
        private final boolean success;

        private ListenerLookup(T t, boolean z) {
            this.listener = t;
            this.success = z;
        }
    }

    public static void addSink(JLSink jLSink) {
        if (jLSink != null) {
            CopyOnWriteArrayList<JLSink> copyOnWriteArrayList = SINKS;
            if (copyOnWriteArrayList.contains(jLSink)) {
                return;
            }
            copyOnWriteArrayList.add(jLSink);
        }
    }

    public static void emit(EventKind eventKind, String str, Map<String, Object> map) {
        JLEvent jLEvent = new JLEvent(eventKind, str, new HashMap(map));
        Iterator<JLSink> it = SINKS.iterator();
        while (it.hasNext()) {
            try {
                it.next().emit(jLEvent);
            } catch (Exception unused) {
            }
        }
    }

    private static ListenerLookup<CompoundButton.OnCheckedChangeListener> getOnCheckedChangeListener(CompoundButton compoundButton) {
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = CompoundButton.class.getDeclaredField("mOnCheckedChangeListener");
            declaredField.setAccessible(true);
            return new ListenerLookup<>((CompoundButton.OnCheckedChangeListener) declaredField.get(compoundButton), true);
        } catch (Throwable unused) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static ListenerLookup<View.OnClickListener> getOnClickListener(View view) {
        Class<?> cls;
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = View.class.getDeclaredField("mListenerInfo");
            boolean z = true;
            declaredField.setAccessible(true);
            Object obj = declaredField.get(view);
            if (obj == null) {
                return new ListenerLookup<>(anonymousClass1, z);
            }
            try {
                cls = Class.forName("android.view.View$ListenerInfo");
            } catch (ClassNotFoundException unused) {
                cls = obj.getClass();
            }
            Field declaredField2 = cls.getDeclaredField("mOnClickListener");
            declaredField2.setAccessible(true);
            return new ListenerLookup<>((View.OnClickListener) declaredField2.get(obj), z);
        } catch (Throwable unused2) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static ListenerLookup<TextView.OnEditorActionListener> getOnEditorActionListener(EditText editText) {
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = EditText.class.getDeclaredField("mEditorActionListener");
            declaredField.setAccessible(true);
            return new ListenerLookup<>((TextView.OnEditorActionListener) declaredField.get(editText), true);
        } catch (Throwable unused) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static ListenerLookup<View.OnFocusChangeListener> getOnFocusChangeListener(View view) {
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = View.class.getDeclaredField("mOnFocusChangeListener");
            declaredField.setAccessible(true);
            return new ListenerLookup<>((View.OnFocusChangeListener) declaredField.get(view), true);
        } catch (Throwable unused) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static ListenerLookup<SearchView.OnQueryTextListener> getOnQueryTextListener(SearchView searchView) {
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = SearchView.class.getDeclaredField("mOnQueryChangeListener");
            declaredField.setAccessible(true);
            return new ListenerLookup<>((SearchView.OnQueryTextListener) declaredField.get(searchView), true);
        } catch (Throwable unused) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static ListenerLookup<SeekBar.OnSeekBarChangeListener> getOnSeekBarChangeListener(SeekBar seekBar) {
        AnonymousClass1 anonymousClass1 = null;
        try {
            Field declaredField = SeekBar.class.getDeclaredField("mOnSeekBarChangeListener");
            declaredField.setAccessible(true);
            return new ListenerLookup<>((SeekBar.OnSeekBarChangeListener) declaredField.get(seekBar), true);
        } catch (Throwable unused) {
            return new ListenerLookup<>(anonymousClass1, false);
        }
    }

    private static void hookCheckedTextView(CheckedTextView checkedTextView) {
        ListenerLookup<View.OnClickListener> onClickListener = getOnClickListener(checkedTextView);
        if (((ListenerLookup) onClickListener).success) {
            final View.OnClickListener onClickListener2 = (View.OnClickListener) ((ListenerLookup) onClickListener).listener;
            checkedTextView.setOnClickListener(new View.OnClickListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    View.OnClickListener onClickListener3 = onClickListener2;
                    if (onClickListener3 != null) {
                        onClickListener3.onClick(view);
                    }
                    if (Journeylitics.sConfig.isEnableToggles() && (view instanceof CheckedTextView)) {
                        final CheckedTextView checkedTextView2 = (CheckedTextView) view;
                        view.post(new Runnable() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Journeylitics.emit(EventKind.click, checkedTextView2.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(checkedTextView2)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "toggle"), new AbstractMap.SimpleEntry(FieldKey.VALUE, String.valueOf(checkedTextView2.isChecked()))));
                            }
                        });
                    }
                }
            });
        }
    }

    private static void hookClick(View view) {
        ListenerLookup<View.OnClickListener> onClickListener = getOnClickListener(view);
        if (((ListenerLookup) onClickListener).success) {
            final View.OnClickListener onClickListener2 = (View.OnClickListener) ((ListenerLookup) onClickListener).listener;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    View.OnClickListener onClickListener3 = onClickListener2;
                    if (onClickListener3 != null) {
                        onClickListener3.onClick(view2);
                    }
                    if (Journeylitics.sConfig.isEnableClicks()) {
                        Journeylitics.emit(EventKind.click, view2.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(view2))));
                    }
                }
            });
        }
    }

    private static void hookScrollView(final View view) {
        view.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.10
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                if (Journeylitics.sConfig.isEnableScrolls()) {
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    Long l = (Long) Journeylitics.LAST_SCROLL_EVENT_AT.get(view);
                    if (l == null || jUptimeMillis - l.longValue() >= 250) {
                        Journeylitics.LAST_SCROLL_EVENT_AT.put(view, Long.valueOf(jUptimeMillis));
                        Journeylitics.emit(EventKind.drag, view.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(view)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "scroll")));
                    }
                }
            }
        });
    }

    private static void hookSearch(final SearchView searchView) {
        ListenerLookup<SearchView.OnQueryTextListener> onQueryTextListener = getOnQueryTextListener(searchView);
        if (((ListenerLookup) onQueryTextListener).success) {
            final SearchView.OnQueryTextListener onQueryTextListener2 = (SearchView.OnQueryTextListener) ((ListenerLookup) onQueryTextListener).listener;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.6
                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    SearchView.OnQueryTextListener onQueryTextListener3 = onQueryTextListener2;
                    boolean z = onQueryTextListener3 != null && onQueryTextListener3.onQueryTextChange(str);
                    if (Journeylitics.sConfig.isEnableSearch()) {
                        Journeylitics.emit(EventKind.edit, searchView.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(searchView)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "change")));
                    }
                    return z;
                }

                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    SearchView.OnQueryTextListener onQueryTextListener3 = onQueryTextListener2;
                    boolean z = onQueryTextListener3 != null && onQueryTextListener3.onQueryTextSubmit(str);
                    if (Journeylitics.sConfig.isEnableSearch()) {
                        Journeylitics.emit(EventKind.click, searchView.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(searchView)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "submit"), new AbstractMap.SimpleEntry(FieldKey.VALUE, Integer.valueOf(str != null ? str.length() : 0))));
                    }
                    return z;
                }
            });
        }
    }

    private static void hookSeek(final SeekBar seekBar) {
        ListenerLookup<SeekBar.OnSeekBarChangeListener> onSeekBarChangeListener = getOnSeekBarChangeListener(seekBar);
        if (((ListenerLookup) onSeekBarChangeListener).success) {
            final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = (SeekBar.OnSeekBarChangeListener) ((ListenerLookup) onSeekBarChangeListener).listener;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.5
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
                    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener3 = onSeekBarChangeListener2;
                    if (onSeekBarChangeListener3 != null) {
                        onSeekBarChangeListener3.onProgressChanged(seekBar2, i, z);
                    }
                    if (z && Journeylitics.sConfig.isEnableSliders()) {
                        Journeylitics.emit(EventKind.drag, seekBar.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(seekBar)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "change"), new AbstractMap.SimpleEntry(FieldKey.VALUE, Integer.valueOf(i))));
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar2) {
                    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener3 = onSeekBarChangeListener2;
                    if (onSeekBarChangeListener3 != null) {
                        onSeekBarChangeListener3.onStartTrackingTouch(seekBar2);
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar2) {
                    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener3 = onSeekBarChangeListener2;
                    if (onSeekBarChangeListener3 != null) {
                        onSeekBarChangeListener3.onStopTrackingTouch(seekBar2);
                    }
                }
            });
        }
    }

    private static void hookTextInput(final EditText editText) {
        if (sConfig.isEnableTextInputs()) {
            editText.addTextChangedListener(new TextWatcher(editText) { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.7
                private int previousLength;
                final /* synthetic */ EditText val$editText;

                {
                    this.val$editText = editText;
                    this.previousLength = editText.getText() != null ? editText.getText().length() : 0;
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    int length = editable != null ? editable.length() : 0;
                    int i = this.previousLength;
                    if (length != i) {
                        int i2 = length - i;
                        Journeylitics.emit(EventKind.edit, this.val$editText.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(this.val$editText)), new AbstractMap.SimpleEntry(FieldKey.ACTION, i2 > 0 ? ProductAction.ACTION_ADD : ProductAction.ACTION_REMOVE), new AbstractMap.SimpleEntry(FieldKey.VALUE, Integer.valueOf(i2))));
                        this.previousLength = length;
                    }
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });
            ListenerLookup<View.OnFocusChangeListener> onFocusChangeListener = getOnFocusChangeListener(editText);
            if (((ListenerLookup) onFocusChangeListener).success) {
                final View.OnFocusChangeListener onFocusChangeListener2 = (View.OnFocusChangeListener) ((ListenerLookup) onFocusChangeListener).listener;
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.8
                    @Override // android.view.View.OnFocusChangeListener
                    public void onFocusChange(View view, boolean z) {
                        View.OnFocusChangeListener onFocusChangeListener3 = onFocusChangeListener2;
                        if (onFocusChangeListener3 != null) {
                            onFocusChangeListener3.onFocusChange(view, z);
                        }
                        Journeylitics.emit(EventKind.edit, editText.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(editText)), new AbstractMap.SimpleEntry(FieldKey.ACTION, z ? "focus" : "blur")));
                    }
                });
            }
            ListenerLookup<TextView.OnEditorActionListener> onEditorActionListener = getOnEditorActionListener(editText);
            if (((ListenerLookup) onEditorActionListener).success) {
                final TextView.OnEditorActionListener onEditorActionListener2 = (TextView.OnEditorActionListener) ((ListenerLookup) onEditorActionListener).listener;
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.9
                    @Override // android.widget.TextView.OnEditorActionListener
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        TextView.OnEditorActionListener onEditorActionListener3 = onEditorActionListener2;
                        boolean z = onEditorActionListener3 != null && onEditorActionListener3.onEditorAction(textView, i, keyEvent);
                        if (i != 6 && i != 2 && i != 3 && i != 4 && i != 5) {
                            return z;
                        }
                        Journeylitics.emit(EventKind.edit, editText.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(editText)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "submit"), new AbstractMap.SimpleEntry(FieldKey.VALUE, Integer.valueOf(editText.getText() != null ? editText.getText().length() : 0))));
                        return z;
                    }
                });
            }
        }
    }

    private static void hookToggle(CompoundButton compoundButton) {
        ListenerLookup<CompoundButton.OnCheckedChangeListener> onCheckedChangeListener = getOnCheckedChangeListener(compoundButton);
        if (((ListenerLookup) onCheckedChangeListener).success) {
            final CompoundButton.OnCheckedChangeListener onCheckedChangeListener2 = (CompoundButton.OnCheckedChangeListener) ((ListenerLookup) onCheckedChangeListener).listener;
            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.hcaptcha.sdk.journeylitics.Journeylitics.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton2, boolean z) {
                    CompoundButton.OnCheckedChangeListener onCheckedChangeListener3 = onCheckedChangeListener2;
                    if (onCheckedChangeListener3 != null) {
                        onCheckedChangeListener3.onCheckedChanged(compoundButton2, z);
                    }
                    if (Journeylitics.sConfig.isEnableToggles()) {
                        Journeylitics.emit(EventKind.click, compoundButton2.getClass().getSimpleName(), MetaMapHelper.createMetaMap(new AbstractMap.SimpleEntry(FieldKey.ID, Journeylitics.viewIdName(compoundButton2)), new AbstractMap.SimpleEntry(FieldKey.ACTION, "toggle"), new AbstractMap.SimpleEntry(FieldKey.VALUE, String.valueOf(z))));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void instrumentActivityViews(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.content);
        if (viewGroup == null) {
            return;
        }
        traverseAndHook(viewGroup);
    }

    public static void instrumentViews(Activity activity) {
        instrumentActivityViews(activity);
    }

    public static boolean isStarted() {
        return STARTED.get();
    }

    public static void removeSink(JLSink jLSink) {
        SINKS.remove(jLSink);
    }

    public static void start(Activity activity) {
        start(activity, JLConfig.DEFAULT);
    }

    private static void traverseAndHook(View view) {
        Boolean bool = Boolean.TRUE;
        if (bool.equals(INSTRUMENTED.put(view, bool))) {
            return;
        }
        if ((view instanceof Button) || (view instanceof ImageButton)) {
            hookClick(view);
        } else if ((view instanceof EditText) || (view instanceof AutoCompleteTextView) || (view instanceof MultiAutoCompleteTextView)) {
            hookTextInput((EditText) view);
        } else if (view instanceof CheckedTextView) {
            hookCheckedTextView((CheckedTextView) view);
        } else if ((view instanceof TextView) && view.isClickable()) {
            hookClick(view);
        } else if (view instanceof CompoundButton) {
            hookToggle((CompoundButton) view);
        } else if (view instanceof SeekBar) {
            hookSeek((SeekBar) view);
        } else if (view instanceof SearchView) {
            hookSearch((SearchView) view);
        } else if ((view instanceof ScrollView) || (view instanceof HorizontalScrollView)) {
            hookScrollView(view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                traverseAndHook(viewGroup.getChildAt(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String viewIdName(View view) {
        int iIndexOfChild;
        int id2 = view.getId();
        if (id2 != -1) {
            try {
                return view.getResources().getResourceEntryName(id2);
            } catch (Exception unused) {
                return String.valueOf(id2);
            }
        }
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            if (editText.getHint() != null && !editText.getHint().toString().isEmpty()) {
                return editText.getHint().toString();
            }
            if (editText.getTag() != null && !editText.getTag().toString().isEmpty()) {
                return editText.getTag().toString();
            }
            if (editText.getContentDescription() != null && !editText.getContentDescription().toString().isEmpty()) {
                return editText.getContentDescription().toString();
            }
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.getHint() != null && !textView.getHint().toString().isEmpty()) {
                return textView.getHint().toString();
            }
            if (textView.getTag() != null && !textView.getTag().toString().isEmpty()) {
                return textView.getTag().toString();
            }
            if (textView.getContentDescription() != null && !textView.getContentDescription().toString().isEmpty()) {
                return textView.getContentDescription().toString();
            }
        } else if (view instanceof Button) {
            Button button = (Button) view;
            if (button.getText() != null && !button.getText().toString().isEmpty()) {
                return button.getText().toString();
            }
            if (button.getTag() != null && !button.getTag().toString().isEmpty()) {
                return button.getTag().toString();
            }
            if (button.getContentDescription() != null && !button.getContentDescription().toString().isEmpty()) {
                return button.getContentDescription().toString();
            }
        }
        ViewParent parent = view.getParent();
        return (!(parent instanceof ViewGroup) || (iIndexOfChild = ((ViewGroup) parent).indexOfChild(view)) < 0) ? view.getClass().getSimpleName() + "_" + (view.hashCode() % 1000) : view.getClass().getSimpleName() + "_" + iIndexOfChild;
    }

    public static void start(Activity activity, JLConfig jLConfig) {
        if (STARTED.compareAndSet(false, true)) {
            sApp = activity.getApplication();
            sConfig = jLConfig;
            CopyOnWriteArrayList<JLSink> copyOnWriteArrayList = SINKS;
            copyOnWriteArrayList.clear();
            copyOnWriteArrayList.addAll(jLConfig.getSinks());
            sApp.registerActivityLifecycleCallbacks(LIFECYCLE_CALLBACKS);
            instrumentViews(activity);
        }
    }

    public static void emit(EventKind eventKind, String str) {
        emit(eventKind, str, new HashMap());
    }
}
