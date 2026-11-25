package org.eclipse.jdt.internal.compiler.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/* JADX INFO: loaded from: classes14.dex */
public final class Messages {
    private static final String BUNDLE_NAME = "org.eclipse.jdt.internal.compiler.messages";
    private static final String EXTENSION = ".properties";
    public static String abort_againstSourceModel;
    public static String abort_externaAnnotationFile;
    public static String abort_invalidAttribute;
    public static String abort_invalidExceptionAttribute;
    public static String abort_invalidOpcode;
    public static String abort_missingCode;
    public static String accept_cannot;
    public static String ast_missingCode;
    public static String compilation_beginningToCompile;
    public static String compilation_done;
    public static String compilation_internalError;
    public static String compilation_loadBinary;
    public static String compilation_process;
    public static String compilation_processing;
    public static String compilation_request;
    public static String compilation_unit;
    public static String compilation_units;
    public static String compilation_unresolvedProblem;
    public static String compilation_unresolvedProblems;
    public static String compilation_write;
    public static String constant_cannotCastedInto;
    public static String constant_cannotConvertedTo;
    private static String[] nlSuffixes;
    public static String output_isFile;
    public static String output_notValid;
    public static String output_notValidAll;
    public static String parser_corruptedFile;
    public static String parser_endOfConstructor;
    public static String parser_endOfFile;
    public static String parser_endOfInitializer;
    public static String parser_endOfMethod;
    public static String parser_incorrectPath;
    public static String parser_missingFile;
    public static String parser_moveFiles;
    public static String parser_regularParse;
    public static String parser_syntaxRecovery;
    public static String problem_atLine;
    public static String problem_noSourceInformation;

    private static class MessagesProperties extends Properties {
        private static final int MOD_EXPECTED = 9;
        private static final int MOD_MASK = 25;
        private static final long serialVersionUID = 1;
        private final Map fields;

        public MessagesProperties(Field[] fieldArr, String str) {
            int length = fieldArr.length;
            this.fields = new HashMap(length * 2);
            for (int i = 0; i < length; i++) {
                this.fields.put(fieldArr[i].getName(), fieldArr[i]);
            }
        }

        @Override // java.util.Hashtable, java.util.Dictionary, java.util.Map
        public synchronized Object put(Object obj, Object obj2) {
            try {
                Field field = (Field) this.fields.get(obj);
                if (field == null) {
                    return null;
                }
                if ((field.getModifiers() & 25) != 9) {
                    return null;
                }
                field.set(null, obj2);
            } catch (SecurityException | Exception unused) {
            }
            return null;
        }
    }

    private Messages() {
    }

    static {
        initializeMessages(BUNDLE_NAME, Messages.class);
    }

    public static String bind(String str) {
        return bind(str, (Object[]) null);
    }

    public static String bind(String str, Object obj) {
        return bind(str, new Object[]{obj});
    }

    public static String bind(String str, Object obj, Object obj2) {
        return bind(str, new Object[]{obj, obj2});
    }

    public static String bind(String str, Object[] objArr) {
        return MessageFormat.format(str, objArr);
    }

    private static String[] buildVariants(String str) {
        if (nlSuffixes == null) {
            String string = Locale.getDefault().toString();
            ArrayList arrayList = new ArrayList(4);
            while (true) {
                arrayList.add(String.valueOf('_') + string + EXTENSION);
                int iLastIndexOf = string.lastIndexOf(95);
                if (iLastIndexOf == -1) {
                    break;
                }
                string = string.substring(0, iLastIndexOf);
            }
            arrayList.add(EXTENSION);
            nlSuffixes = (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        String strReplace = str.replace('.', '/');
        int length = nlSuffixes.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = String.valueOf(strReplace) + nlSuffixes[i];
        }
        return strArr;
    }

    public static void initializeMessages(String str, Class cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        load(str, cls.getClassLoader(), declaredFields);
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 25) == 9) {
                try {
                    if (field.get(cls) == null) {
                        field.set(null, "Missing message: " + field.getName() + " in: " + str);
                    }
                } catch (IllegalAccessException | IllegalArgumentException unused) {
                }
            }
        }
    }

    public static void load(String str, ClassLoader classLoader, Field[] fieldArr) {
        InputStream resourceAsStream;
        String[] strArrBuildVariants = buildVariants(str);
        int length = strArrBuildVariants.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            if (classLoader == null) {
                resourceAsStream = ClassLoader.getSystemResourceAsStream(strArrBuildVariants[length]);
            } else {
                resourceAsStream = classLoader.getResourceAsStream(strArrBuildVariants[length]);
            }
            if (resourceAsStream != null) {
                try {
                    new MessagesProperties(fieldArr, str).load(resourceAsStream);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    try {
                        resourceAsStream.close();
                    } catch (IOException unused2) {
                    }
                    throw th;
                }
                try {
                    resourceAsStream.close();
                } catch (IOException unused3) {
                }
            }
        }
    }
}
