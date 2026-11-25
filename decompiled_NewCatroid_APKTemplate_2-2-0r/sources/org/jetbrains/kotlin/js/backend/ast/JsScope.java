package org.jetbrains.kotlin.js.backend.ast;

import com.google.android.gms.cast.MediaTrack;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.js.util.Maps;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JsScope {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Pattern FRESH_NAME_SUFFIX = Pattern.compile("[\\$_]\\d+$");
    private final String description;
    private Map<String, JsName> names;
    private final JsScope parent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6 || i == 11 || i == 13 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6 || i == 11 || i == 13 || i == 16) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "scopeName";
                break;
            case 3:
                objArr[0] = "identifier";
                break;
            case 4:
            case 6:
            case 11:
            case 13:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/js/backend/ast/JsScope";
                break;
            case 5:
            case 7:
                objArr[0] = "suggestedName";
                break;
            case 8:
            case 12:
            case 14:
                objArr[0] = "ident";
                break;
            case 9:
            case 10:
                objArr[0] = "name";
                break;
            case 15:
                objArr[0] = "suggestedIdent";
                break;
            default:
                objArr[0] = MediaTrack.ROLE_DESCRIPTION;
                break;
        }
        if (i == 4) {
            objArr[1] = "declareName";
        } else if (i == 6) {
            objArr[1] = "declareFreshName";
        } else if (i == 11) {
            objArr[1] = "getDescription";
        } else if (i == 13) {
            objArr[1] = "doCreateName";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/js/backend/ast/JsScope";
        } else {
            objArr[1] = "getFreshIdent";
        }
        switch (i) {
            case 2:
                objArr[2] = "innerObjectScope";
                break;
            case 3:
                objArr[2] = "declareName";
                break;
            case 4:
            case 6:
            case 11:
            case 13:
            case 16:
                break;
            case 5:
                objArr[2] = "declareFreshName";
                break;
            case 7:
                objArr[2] = "declareTemporaryName";
                break;
            case 8:
                objArr[2] = "findName";
                break;
            case 9:
                objArr[2] = "hasOwnName";
                break;
            case 10:
                objArr[2] = "hasName";
                break;
            case 12:
                objArr[2] = "doCreateName";
                break;
            case 14:
                objArr[2] = "findOwnName";
                break;
            case 15:
                objArr[2] = "getFreshIdent";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6 && i != 11 && i != 13 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public JsScope(JsScope jsScope, String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.names = Collections.emptyMap();
        this.description = str;
        this.parent = jsScope;
    }

    protected JsScope(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.names = Collections.emptyMap();
        this.description = str;
        this.parent = null;
    }

    public JsScope innerObjectScope(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return new JsObjectScope(this, str);
    }

    public JsName declareName(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        JsName jsNameFindOwnName = findOwnName(str);
        if (jsNameFindOwnName == null) {
            jsNameFindOwnName = doCreateName(str);
        }
        if (jsNameFindOwnName == null) {
            $$$reportNull$$$0(4);
        }
        return jsNameFindOwnName;
    }

    public JsName declareFreshName(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        JsName jsNameDoCreateName = doCreateName(getFreshIdent(str));
        if (jsNameDoCreateName == null) {
            $$$reportNull$$$0(6);
        }
        return jsNameDoCreateName;
    }

    public static JsName declareTemporaryName(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return new JsName(str, true);
    }

    public static JsName declareTemporary() {
        return declareTemporaryName("tmp$");
    }

    public final JsName findName(String str) {
        JsScope jsScope;
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        JsName jsNameFindOwnName = findOwnName(str);
        return (jsNameFindOwnName != null || (jsScope = this.parent) == null) ? jsNameFindOwnName : jsScope.findName(str);
    }

    public boolean hasOwnName(String str) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        return this.names.containsKey(str);
    }

    private boolean hasName(String str) {
        JsScope jsScope;
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return hasOwnName(str) || ((jsScope = this.parent) != null && jsScope.hasName(str));
    }

    public final JsScope getParent() {
        return this.parent;
    }

    public JsProgram getProgram() {
        return this.parent.getProgram();
    }

    public final String toString() {
        if (this.parent != null) {
            return this.description + "->" + this.parent;
        }
        return this.description;
    }

    public void copyOwnNames(JsScope jsScope) {
        if (jsScope.names.isEmpty()) {
            return;
        }
        HashMap map = new HashMap(this.names);
        this.names = map;
        map.putAll(jsScope.names);
    }

    public String getDescription() {
        String str = this.description;
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        return str;
    }

    protected JsName doCreateName(String str) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        JsName jsName = new JsName(str, false);
        this.names = Maps.put(this.names, str, jsName);
        return jsName;
    }

    protected JsName findOwnName(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return this.names.get(str);
    }

    protected String getFreshIdent(String str) {
        int iIntValue;
        char cCharAt;
        String strReplaceAll;
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        Matcher matcher = FRESH_NAME_SUFFIX.matcher(str);
        if (matcher.find()) {
            String strGroup = matcher.group();
            strReplaceAll = matcher.replaceAll("");
            cCharAt = strGroup.charAt(0);
            iIntValue = Integer.valueOf(strGroup.substring(1)).intValue();
        } else {
            iIntValue = 0;
            cCharAt = '_';
            strReplaceAll = str;
        }
        while (hasName(str)) {
            str = strReplaceAll + cCharAt + iIntValue;
            iIntValue++;
        }
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        return str;
    }
}
