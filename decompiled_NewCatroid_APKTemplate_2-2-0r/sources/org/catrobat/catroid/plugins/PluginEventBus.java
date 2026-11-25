package org.catrobat.catroid.plugins;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.utils.lunoscript.LunoScriptEngine;
import org.catrobat.catroid.utils.lunoscript.LunoValue;
import org.catrobat.catroid.utils.lunoscript.Token;
import org.catrobat.catroid.utils.lunoscript.TokenType;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginEventBus.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PluginEventBus {
    private static volatile PluginEventBus instance;
    private final Map<String, List<Listener>> listeners = new HashMap();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginEventBus$Listener.class
      classes14.dex
     */
    private static class Listener {
        final LunoValue.Callable callable;
        final LunoScriptEngine engine;

        Listener(LunoScriptEngine engine, LunoValue.Callable callable) {
            this.engine = engine;
            this.callable = callable;
        }
    }

    private PluginEventBus() {
    }

    public static PluginEventBus getInstance() {
        if (instance == null) {
            synchronized (PluginEventBus.class) {
                if (instance == null) {
                    instance = new PluginEventBus();
                }
            }
        }
        return instance;
    }

    public void register(String eventName, LunoScriptEngine engine, LunoValue.Callable callable) {
        synchronized (this.listeners) {
            List<Listener> arrayList = this.listeners.get(eventName);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.listeners.put(eventName, arrayList);
            }
            arrayList.add(new Listener(engine, callable));
        }
    }

    public void dispatch(String eventName, Object... args) {
        synchronized (this.listeners) {
            if (this.listeners.containsKey(eventName)) {
                ArrayList<Listener> arrayList = new ArrayList(this.listeners.get(eventName));
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : args) {
                    arrayList2.add(LunoValue.INSTANCE.fromKotlin(obj));
                }
                for (Listener listener : arrayList) {
                    try {
                        listener.callable.call(listener.engine.getInterpreter(), arrayList2, new Token(TokenType.EOF, "", null, -1, 0));
                    } catch (Exception e) {
                        Log.e("PluginEventBus", "Plugin failed to handle event '" + eventName + FormatHelper.QUOTE, e);
                    }
                }
            }
        }
    }
}
