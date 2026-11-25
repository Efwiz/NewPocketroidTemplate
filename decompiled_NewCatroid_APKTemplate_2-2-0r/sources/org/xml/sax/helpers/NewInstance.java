package org.xml.sax.helpers;

/* JADX INFO: loaded from: classes3.dex */
class NewInstance {
    private static final boolean DO_FALLBACK = true;
    static /* synthetic */ Class class$org$xml$sax$helpers$NewInstance;

    NewInstance() {
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static ClassLoader getClassLoader() {
        ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        Class clsClass$ = class$org$xml$sax$helpers$NewInstance;
        if (clsClass$ == null) {
            clsClass$ = class$("org.xml.sax.helpers.NewInstance");
            class$org$xml$sax$helpers$NewInstance = clsClass$;
        }
        return clsClass$.getClassLoader();
    }

    /* JADX WARN: Code duplicated, block: B:3:0x0002  */
    static Object newInstance(ClassLoader classLoader, String str) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class clsClass$;
        Class<?> clsLoadClass;
        if (classLoader == null) {
            clsLoadClass = Class.forName(str);
        } else {
            try {
                clsLoadClass = classLoader.loadClass(str);
            } catch (ClassNotFoundException unused) {
                if (class$org$xml$sax$helpers$NewInstance == null) {
                    clsClass$ = class$("org.xml.sax.helpers.NewInstance");
                    class$org$xml$sax$helpers$NewInstance = clsClass$;
                } else {
                    clsClass$ = class$org$xml$sax$helpers$NewInstance;
                }
                ClassLoader classLoader2 = clsClass$.getClassLoader();
                if (classLoader2 != null) {
                    clsLoadClass = classLoader2.loadClass(str);
                } else {
                    clsLoadClass = Class.forName(str);
                }
            }
        }
        return clsLoadClass.newInstance();
    }
}
