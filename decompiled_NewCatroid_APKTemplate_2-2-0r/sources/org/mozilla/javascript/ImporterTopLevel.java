package org.mozilla.javascript;

import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes4.dex */
public class ImporterTopLevel extends TopLevel {
    private static final Object IMPORTER_TAG = "Importer";
    private static final int Id_constructor = 1;
    private static final int Id_importClass = 2;
    private static final int Id_importPackage = 3;
    private static final int MAX_PROTOTYPE_ID = 3;
    private static final long serialVersionUID = -9095380847465315412L;
    private ObjArray importedPackages;
    private boolean topScopeFlag;

    public ImporterTopLevel() {
        this.importedPackages = new ObjArray();
    }

    public ImporterTopLevel(Context context) {
        this(context, false);
    }

    public ImporterTopLevel(Context context, boolean z) {
        this.importedPackages = new ObjArray();
        initStandardObjects(context, z);
    }

    @Override // org.mozilla.javascript.TopLevel, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return this.topScopeFlag ? "global" : "JavaImporter";
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        new ImporterTopLevel().exportAsJSClass(3, scriptable, z);
    }

    public void initStandardObjects(Context context, boolean z) {
        context.initStandardObjects(this, z);
        this.topScopeFlag = true;
        IdFunctionObject idFunctionObjectExportAsJSClass = exportAsJSClass(3, this, false);
        if (z) {
            idFunctionObjectExportAsJSClass.sealObject();
        }
        delete(JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public boolean has(String str, Scriptable scriptable) {
        return super.has(str, scriptable) || getPackageProperty(str, scriptable) != NOT_FOUND;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object get(String str, Scriptable scriptable) {
        Object obj = super.get(str, scriptable);
        return obj != NOT_FOUND ? obj : getPackageProperty(str, scriptable);
    }

    private Object getPackageProperty(String str, Scriptable scriptable) {
        Object[] array;
        Object obj = NOT_FOUND;
        synchronized (this.importedPackages) {
            array = this.importedPackages.toArray();
        }
        for (Object obj2 : array) {
            Object pkgProperty = ((NativeJavaPackage) obj2).getPkgProperty(str, scriptable, false);
            if (pkgProperty != null && !(pkgProperty instanceof NativeJavaPackage)) {
                if (obj != NOT_FOUND) {
                    throw Context.reportRuntimeError2("msg.ambig.import", obj.toString(), pkgProperty.toString());
                }
                obj = pkgProperty;
            }
        }
        return obj;
    }

    @Deprecated
    public void importPackage(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        js_importPackage(objArr);
    }

    private Object js_construct(Scriptable scriptable, Object[] objArr) {
        ImporterTopLevel importerTopLevel = new ImporterTopLevel();
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            if (obj instanceof NativeJavaClass) {
                importerTopLevel.importClass((NativeJavaClass) obj);
            } else if (obj instanceof NativeJavaPackage) {
                importerTopLevel.importPackage((NativeJavaPackage) obj);
            } else {
                throw Context.reportRuntimeError1("msg.not.class.not.pkg", Context.toString(obj));
            }
        }
        importerTopLevel.setParentScope(scriptable);
        importerTopLevel.setPrototype(this);
        return importerTopLevel;
    }

    private Object js_importClass(Object[] objArr) {
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            if (!(obj instanceof NativeJavaClass)) {
                throw Context.reportRuntimeError1("msg.not.class", Context.toString(obj));
            }
            importClass((NativeJavaClass) obj);
        }
        return Undefined.instance;
    }

    private Object js_importPackage(Object[] objArr) {
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            if (!(obj instanceof NativeJavaPackage)) {
                throw Context.reportRuntimeError1("msg.not.pkg", Context.toString(obj));
            }
            importPackage((NativeJavaPackage) obj);
        }
        return Undefined.instance;
    }

    private void importPackage(NativeJavaPackage nativeJavaPackage) {
        if (nativeJavaPackage == null) {
            return;
        }
        synchronized (this.importedPackages) {
            for (int i = 0; i != this.importedPackages.size(); i++) {
                if (nativeJavaPackage.equals(this.importedPackages.get(i))) {
                    return;
                }
            }
            this.importedPackages.add(nativeJavaPackage);
        }
    }

    private void importClass(NativeJavaClass nativeJavaClass) {
        String name2 = nativeJavaClass.getClassObject().getName();
        String strSubstring = name2.substring(name2.lastIndexOf(46) + 1);
        Object obj = get(strSubstring, this);
        if (obj != NOT_FOUND && obj != nativeJavaClass) {
            throw Context.reportRuntimeError1("msg.prop.defined", strSubstring);
        }
        put(strSubstring, this, nativeJavaClass);
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i) {
        String str;
        int i2 = 1;
        if (i == 1) {
            i2 = 0;
            str = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
        } else if (i == 2) {
            str = "importClass";
        } else if (i == 3) {
            str = "importPackage";
        } else {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        initPrototypeMethod(IMPORTER_TAG, i, str, i2);
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(IMPORTER_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == 1) {
            return js_construct(scriptable, objArr);
        }
        if (iMethodId == 2) {
            return realThis(scriptable2, idFunctionObject).js_importClass(objArr);
        }
        if (iMethodId == 3) {
            return realThis(scriptable2, idFunctionObject).js_importPackage(objArr);
        }
        throw new IllegalArgumentException(String.valueOf(iMethodId));
    }

    private ImporterTopLevel realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (this.topScopeFlag) {
            return this;
        }
        if (!(scriptable instanceof ImporterTopLevel)) {
            throw incompatibleCallError(idFunctionObject);
        }
        return (ImporterTopLevel) scriptable;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0025  */
    @Override // org.mozilla.javascript.IdScriptableObject
    protected int findPrototypeId(String str) {
        String str2;
        int i;
        int length = str.length();
        if (length == 11) {
            char cCharAt = str.charAt(0);
            if (cCharAt == 'c') {
                str2 = JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
                i = 1;
            } else if (cCharAt == 'i') {
                str2 = "importClass";
                i = 2;
            } else {
                str2 = null;
                i = 0;
            }
        } else if (length == 13) {
            str2 = "importPackage";
            i = 3;
        } else {
            str2 = null;
            i = 0;
        }
        if (str2 == null || str2 == str || str2.equals(str)) {
            return i;
        }
        return 0;
    }
}
