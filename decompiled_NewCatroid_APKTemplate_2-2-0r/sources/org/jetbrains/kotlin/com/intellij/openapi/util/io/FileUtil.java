package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apk.UncompressedFiles;
import gnu.trove.TObjectHashingStrategy;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.UtilBundle;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.JBTreeTraverser;
import org.jetbrains.kotlin.com.intellij.util.io.URLUtil;
import org.jetbrains.kotlin.com.intellij.util.text.CaseInsensitiveStringHashingStrategy;

/* JADX INFO: loaded from: classes3.dex */
public class FileUtil extends FileUtilRt {
    public static final String ASYNC_DELETE_EXTENSION = ".__del__";

    @Deprecated
    public static final TObjectHashingStrategy<File> FILE_HASHING_STRATEGY;
    private static final Logger LOG;

    @Deprecated
    public static final TObjectHashingStrategy<String> PATH_HASHING_STRATEGY;
    public static final int REGEX_PATTERN_FLAGS;
    private static final FileUtilRt.SymlinkResolver SYMLINK_RESOLVER;

    /* JADX WARN: Code duplicated, block: B:124:0x0241  */
    /* JADX WARN: Code duplicated, block: B:12:0x0023 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 139 && i != 140 && i != 203 && i != 204 && i != 249 && i != 250) {
            switch (i) {
                default:
                    switch (i) {
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 1:
                case 33:
                case 35:
                case 38:
                case 40:
                case 42:
                case 45:
                case 49:
                case 83:
                case 85:
                case 89:
                case 94:
                case 102:
                case 104:
                case 106:
                case 108:
                case 110:
                case 112:
                case 118:
                case 120:
                case 132:
                case 161:
                case 169:
                case 172:
                case 177:
                case 179:
                case 182:
                case 184:
                case 187:
                case 190:
                case 192:
                case 194:
                case 197:
                case 200:
                case 206:
                case 211:
                case 213:
                case 215:
                case 218:
                case 220:
                case 222:
                case 224:
                case 226:
                case XKeySym.XK_adiaeresis /* 228 */:
                case XKeySym.XK_ae /* 230 */:
                case XKeySym.XK_egrave /* 232 */:
                case XKeySym.XK_ecircumflex /* 234 */:
                case XKeySym.XK_igrave /* 236 */:
                case XKeySym.XK_icircumflex /* 238 */:
                case 240:
                case 242:
                case 252:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 139 && i != 140 && i != 203 && i != 204 && i != 249 && i != 250) {
            switch (i) {
                case 1:
                case 33:
                case 35:
                case 38:
                case 40:
                case 42:
                case 45:
                case 49:
                case 83:
                case 85:
                case 89:
                case 94:
                case 102:
                case 104:
                case 106:
                case 108:
                case 110:
                case 112:
                case 118:
                case 120:
                case 132:
                case 161:
                case 169:
                case 172:
                case 177:
                case 179:
                case 182:
                case 184:
                case 187:
                case 190:
                case 192:
                case 194:
                case 197:
                case 200:
                case 206:
                case 211:
                case 213:
                case 215:
                case 218:
                case 220:
                case 222:
                case 224:
                case 226:
                case XKeySym.XK_adiaeresis /* 228 */:
                case XKeySym.XK_ae /* 230 */:
                case XKeySym.XK_egrave /* 232 */:
                case XKeySym.XK_ecircumflex /* 234 */:
                case XKeySym.XK_igrave /* 236 */:
                case XKeySym.XK_icircumflex /* 238 */:
                case 240:
                case 242:
                case 252:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 33:
            case 35:
            case 38:
            case 40:
            case 42:
            case 45:
            case 49:
            case 83:
            case 85:
            case 89:
            case 94:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 118:
            case 120:
            case 132:
            case 139:
            case 140:
            case 161:
            case 169:
            case 172:
            case 177:
            case 179:
            case 182:
            case 184:
            case 187:
            case 190:
            case 192:
            case 194:
            case 197:
            case 200:
            case 203:
            case 204:
            case 206:
            case 211:
            case 213:
            case 215:
            case 218:
            case 220:
            case 222:
            case 224:
            case 226:
            case XKeySym.XK_adiaeresis /* 228 */:
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_ecircumflex /* 234 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 240:
            case 242:
            case 249:
            case 250:
            case 252:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtil";
                break;
            case 2:
            case 4:
                objArr[0] = "basePath";
                break;
            case 3:
            case 5:
            case 103:
            case 105:
                objArr[0] = "filePath";
                break;
            case 6:
            case 13:
            case 15:
            case 17:
            case 19:
            case 51:
            case 53:
            case 107:
            case 109:
            case 119:
            case 121:
            case 142:
            case 143:
            case 173:
            case 174:
            case 175:
            case 176:
            case 208:
            case XKeySym.XK_ccedilla /* 231 */:
            case XKeySym.XK_eacute /* 233 */:
            case XKeySym.XK_ntilde /* 241 */:
            case 247:
                objArr[0] = "path";
                break;
            case 7:
            case 9:
            case 11:
                objArr[0] = "ancestor";
                break;
            case 8:
            case 10:
            case 12:
            case 31:
            case 32:
            case 43:
            case 46:
            case 50:
            case 52:
            case 54:
            case 55:
            case 62:
            case 82:
            case 141:
            case 144:
            case 146:
            case 148:
            case 150:
            case 153:
            case 155:
            case 157:
            case 159:
            case 207:
            case 209:
            case 210:
            case 212:
            case 214:
            case 216:
            case 219:
            case 221:
            case 223:
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_aring /* 229 */:
            case 245:
            case 246:
            case 248:
            case 251:
                objArr[0] = "file";
                break;
            case 14:
            case 16:
            case 18:
            case 20:
            case 181:
            case 183:
            case 186:
            case 189:
            case 191:
            case 193:
            case 196:
            case 199:
            case 202:
                objArr[0] = "prefix";
                break;
            case 29:
                objArr[0] = "f1";
                break;
            case 30:
                objArr[0] = "f2";
                break;
            case 34:
            case 36:
            case 41:
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_idiaeresis /* 239 */:
                objArr[0] = "stream";
                break;
            case 37:
            case 39:
            case 225:
            case XKeySym.XK_ediaeresis /* 235 */:
                objArr[0] = "reader";
                break;
            case 44:
            case 129:
                objArr[0] = "files";
                break;
            case 47:
                objArr[0] = "originalFileName";
                break;
            case 48:
                objArr[0] = "parent";
                break;
            case 56:
            case 58:
            case 60:
                objArr[0] = "fromFile";
                break;
            case 57:
            case 59:
            case 61:
                objArr[0] = "toFile";
                break;
            case 63:
            case 65:
            case 67:
                objArr[0] = "inputStream";
                break;
            case 64:
            case 66:
            case 68:
                objArr[0] = "outputStream";
                break;
            case 69:
            case 71:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 70:
            case 72:
                objArr[0] = "to";
                break;
            case 73:
            case 75:
            case 77:
            case 79:
            case 133:
                objArr[0] = "fromDir";
                break;
            case 74:
            case 76:
            case 78:
            case 80:
            case 134:
                objArr[0] = "toDir";
                break;
            case 81:
            case 168:
            case 171:
            case 185:
            case 188:
            case 195:
            case 198:
            case 201:
                objArr[0] = "dir";
                break;
            case 84:
            case 135:
            case 136:
            case 137:
                objArr[0] = "name";
                break;
            case 86:
            case 90:
                objArr[0] = "aParentFolder";
                break;
            case 87:
            case 91:
                objArr[0] = "aFilePrefix";
                break;
            case 88:
            case 92:
                objArr[0] = "aExtension";
                break;
            case 93:
            case 101:
                objArr[0] = "condition";
                break;
            case 95:
            case 98:
                objArr[0] = "parentFolder";
                break;
            case 96:
            case 99:
                objArr[0] = "filePrefix";
                break;
            case 97:
            case 100:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 111:
                objArr[0] = "urlString";
                break;
            case 113:
            case 115:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 114:
                objArr[0] = "newName";
                break;
            case 116:
                objArr[0] = "target";
                break;
            case 117:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 122:
                objArr[0] = "normalizedPath";
                break;
            case 123:
            case 127:
            case 162:
            case 164:
            case 243:
                objArr[0] = "root";
                break;
            case 124:
            case 128:
            case 167:
            case 170:
                objArr[0] = "pattern";
                break;
            case 125:
                objArr[0] = "outFiles";
                break;
            case 126:
                objArr[0] = "absoluteRoot";
                break;
            case 130:
            case 131:
                objArr[0] = "antPattern";
                break;
            case 138:
                objArr[0] = "replacement";
                break;
            case 145:
            case 147:
            case 149:
            case 151:
            case 154:
            case 156:
            case 158:
            case 160:
                objArr[0] = "text";
                break;
            case 152:
                objArr[0] = "charset";
                break;
            case 163:
            case 165:
            case 244:
                objArr[0] = "processor";
                break;
            case 166:
                objArr[0] = UncompressedFiles.NAME_paths;
                break;
            case 178:
                objArr[0] = "defaultFiles";
                break;
            case 180:
                objArr[0] = "marker";
                break;
            case 205:
                objArr[0] = "tempPath";
                break;
            case 217:
                objArr[0] = "encoding";
                break;
            default:
                objArr[0] = "parts";
                break;
        }
        if (i == 139 || i == 140) {
            objArr[1] = "sanitizeFileName";
        } else if (i == 203) {
            objArr[1] = "createTempFile";
        } else if (i == 204) {
            objArr[1] = "getTempDirectory";
        } else if (i != 249 && i != 250) {
            switch (i) {
                case 1:
                    objArr[1] = "join";
                    break;
                case 33:
                    objArr[1] = "loadFileBytes";
                    break;
                case 35:
                    objArr[1] = "loadFirstAndClose";
                    break;
                case 38:
                    objArr[1] = "loadTextAndClose";
                    break;
                case 40:
                    objArr[1] = "adaptiveLoadText";
                    break;
                case 42:
                    objArr[1] = "adaptiveLoadBytes";
                    break;
                case 45:
                    objArr[1] = "asyncDelete";
                    break;
                case 49:
                    objArr[1] = "getTempFile";
                    break;
                case 83:
                case 85:
                    objArr[1] = "getNameWithoutExtension";
                    break;
                case 89:
                case 94:
                    objArr[1] = "createSequentFileName";
                    break;
                case 102:
                    objArr[1] = "findSequentFile";
                    break;
                case 104:
                    objArr[1] = "toSystemDependentName";
                    break;
                case 106:
                    objArr[1] = "toSystemIndependentName";
                    break;
                case 108:
                    objArr[1] = "normalize";
                    break;
                case 110:
                    objArr[1] = "normalizeTail";
                    break;
                case 112:
                    objArr[1] = "unquote";
                    break;
                case 118:
                    objArr[1] = "getExtension";
                    break;
                case 120:
                    objArr[1] = "resolveShortWindowsName";
                    break;
                case 132:
                    objArr[1] = "convertAntToRegexp";
                    break;
                case 161:
                    objArr[1] = "fileTraverser";
                    break;
                case 169:
                    objArr[1] = "findFilesByMask";
                    break;
                case 172:
                    objArr[1] = "findFilesOrDirsByMask";
                    break;
                case 177:
                    objArr[1] = "expandUserHome";
                    break;
                case 179:
                    objArr[1] = "notNullize";
                    break;
                case 182:
                case 184:
                case 187:
                case 190:
                    objArr[1] = "createTempDirectory";
                    break;
                case 192:
                case 194:
                case 197:
                case 200:
                    objArr[1] = "createTempFile";
                    break;
                case 206:
                    objArr[1] = "generateRandomTemporaryPath";
                    break;
                case 211:
                case 213:
                case 215:
                case 218:
                case 220:
                    objArr[1] = "loadFile";
                    break;
                case 222:
                case 224:
                    objArr[1] = "loadFileText";
                    break;
                case 226:
                    objArr[1] = "loadText";
                    break;
                case XKeySym.XK_adiaeresis /* 228 */:
                case XKeySym.XK_ae /* 230 */:
                case XKeySym.XK_egrave /* 232 */:
                case XKeySym.XK_ecircumflex /* 234 */:
                case XKeySym.XK_igrave /* 236 */:
                    objArr[1] = "loadLines";
                    break;
                case XKeySym.XK_icircumflex /* 238 */:
                case 240:
                    objArr[1] = "loadBytes";
                    break;
                case 242:
                    objArr[1] = "splitPath";
                    break;
                case 252:
                    objArr[1] = "fileToUri";
                    break;
                default:
                    switch (i) {
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                            objArr[1] = "startsWith";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtil";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getUrl";
        }
        switch (i) {
            case 1:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 33:
            case 35:
            case 38:
            case 40:
            case 42:
            case 45:
            case 49:
            case 83:
            case 85:
            case 89:
            case 94:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 118:
            case 120:
            case 132:
            case 139:
            case 140:
            case 161:
            case 169:
            case 172:
            case 177:
            case 179:
            case 182:
            case 184:
            case 187:
            case 190:
            case 192:
            case 194:
            case 197:
            case 200:
            case 203:
            case 204:
            case 206:
            case 211:
            case 213:
            case 215:
            case 218:
            case 220:
            case 222:
            case 224:
            case 226:
            case XKeySym.XK_adiaeresis /* 228 */:
            case XKeySym.XK_ae /* 230 */:
            case XKeySym.XK_egrave /* 232 */:
            case XKeySym.XK_ecircumflex /* 234 */:
            case XKeySym.XK_igrave /* 236 */:
            case XKeySym.XK_icircumflex /* 238 */:
            case 240:
            case 242:
            case 249:
            case 250:
            case 252:
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "getRelativePath";
                break;
            case 6:
                objArr[2] = "isAbsolute";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "isAncestor";
                break;
            case 11:
            case 12:
                objArr[2] = "isAncestorThreeState";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "startsWith";
                break;
            case 29:
            case 30:
                objArr[2] = "findAncestor";
                break;
            case 31:
                objArr[2] = "getParentFile";
                break;
            case 32:
                objArr[2] = "loadFileBytes";
                break;
            case 34:
                objArr[2] = "loadFirstAndClose";
                break;
            case 36:
            case 37:
                objArr[2] = "loadTextAndClose";
                break;
            case 39:
                objArr[2] = "adaptiveLoadText";
                break;
            case 41:
                objArr[2] = "adaptiveLoadBytes";
                break;
            case 43:
            case 44:
                objArr[2] = "asyncDelete";
                break;
            case 46:
                objArr[2] = "renameToTempFileOrDelete";
                break;
            case 47:
            case 48:
                objArr[2] = "getTempFile";
                break;
            case 50:
            case 51:
                objArr[2] = "delete";
                break;
            case 52:
                objArr[2] = "createParentDirs";
                break;
            case 53:
                objArr[2] = "createDirectory";
                break;
            case 54:
                objArr[2] = "createIfDoesntExist";
                break;
            case 55:
                objArr[2] = "ensureCanCreateFile";
                break;
            case 56:
            case 57:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                objArr[2] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 58:
            case 59:
                objArr[2] = "copyContent";
                break;
            case 60:
            case 61:
                objArr[2] = "performCopy";
                break;
            case 62:
                objArr[2] = "openOutputStream";
                break;
            case 69:
            case 70:
            case 71:
            case 72:
                objArr[2] = "copyFileOrDir";
                break;
            case 73:
            case 74:
            case 77:
            case 78:
            case 79:
            case 80:
                objArr[2] = "copyDir";
                break;
            case 75:
            case 76:
                objArr[2] = "copyDirContent";
                break;
            case 81:
                objArr[2] = "ensureExists";
                break;
            case 82:
            case 84:
                objArr[2] = "getNameWithoutExtension";
                break;
            case 86:
            case 87:
            case 88:
            case 90:
            case 91:
            case 92:
            case 93:
                objArr[2] = "createSequentFileName";
                break;
            case 95:
            case 96:
            case 97:
                objArr[2] = "findSequentNonexistentFile";
                break;
            case 98:
            case 99:
            case 100:
            case 101:
                objArr[2] = "findSequentFile";
                break;
            case 103:
                objArr[2] = "toSystemDependentName";
                break;
            case 105:
                objArr[2] = "toSystemIndependentName";
                break;
            case 107:
                objArr[2] = "normalize";
                break;
            case 109:
                objArr[2] = "normalizeTail";
                break;
            case 111:
                objArr[2] = "unquote";
                break;
            case 113:
            case 114:
            case 115:
            case 116:
                objArr[2] = "rename";
                break;
            case 117:
                objArr[2] = "getExtension";
                break;
            case 119:
                objArr[2] = "resolveShortWindowsName";
                break;
            case 121:
                objArr[2] = "containsWindowsShortName";
                break;
            case 122:
                objArr[2] = "extractRootPath";
                break;
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
                objArr[2] = "collectMatchedFiles";
                break;
            case 130:
            case 131:
                objArr[2] = "convertAntToRegexp";
                break;
            case 133:
            case 134:
                objArr[2] = "moveDirWithContent";
                break;
            case 135:
            case 136:
            case 137:
            case 138:
                objArr[2] = "sanitizeFileName";
                break;
            case 141:
                objArr[2] = "canExecute";
                break;
            case 142:
                objArr[2] = "canWrite";
                break;
            case 143:
                objArr[2] = "setReadOnlyAttribute";
                break;
            case 144:
            case 145:
                objArr[2] = "appendToFile";
                break;
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
                objArr[2] = "writeToFile";
                break;
            case 162:
            case 163:
            case 164:
            case 165:
                objArr[2] = "processFilesRecursively";
                break;
            case 166:
                objArr[2] = "findFirstThatExist";
                break;
            case 167:
            case 168:
                objArr[2] = "findFilesByMask";
                break;
            case 170:
            case 171:
                objArr[2] = "findFilesOrDirsByMask";
                break;
            case 173:
                objArr[2] = "isAbsolutePlatformIndependent";
                break;
            case 174:
                objArr[2] = "isUnixAbsolutePath";
                break;
            case 175:
                objArr[2] = "isWindowsAbsolutePath";
                break;
            case 176:
                objArr[2] = "expandUserHome";
                break;
            case 178:
                objArr[2] = "notNullize";
                break;
            case 180:
                objArr[2] = "isHashBangLine";
                break;
            case 181:
            case 183:
            case 185:
            case 186:
            case 188:
            case 189:
                objArr[2] = "createTempDirectory";
                break;
            case 191:
            case 193:
            case 195:
            case 196:
            case 198:
            case 199:
            case 201:
            case 202:
                objArr[2] = "createTempFile";
                break;
            case 205:
                objArr[2] = "resetCanonicalTempPathCache";
                break;
            case 207:
                objArr[2] = "setExecutable";
                break;
            case 208:
            case 209:
                objArr[2] = "loadFileOrNull";
                break;
            case 210:
            case 212:
            case 214:
            case 216:
            case 217:
            case 219:
                objArr[2] = "loadFile";
                break;
            case 221:
            case 223:
                objArr[2] = "loadFileText";
                break;
            case 225:
                objArr[2] = "loadText";
                break;
            case XKeySym.XK_atilde /* 227 */:
            case XKeySym.XK_aring /* 229 */:
            case XKeySym.XK_ccedilla /* 231 */:
            case XKeySym.XK_eacute /* 233 */:
            case XKeySym.XK_ediaeresis /* 235 */:
                objArr[2] = "loadLines";
                break;
            case XKeySym.XK_iacute /* 237 */:
            case XKeySym.XK_idiaeresis /* 239 */:
                objArr[2] = "loadBytes";
                break;
            case XKeySym.XK_ntilde /* 241 */:
                objArr[2] = "splitPath";
                break;
            case 243:
            case 244:
                objArr[2] = "visitFiles";
                break;
            case 245:
            case 246:
                objArr[2] = "deleteWithRenaming";
                break;
            case 247:
                objArr[2] = "isFileSystemCaseSensitive";
                break;
            case 248:
                objArr[2] = "getUrl";
                break;
            case 251:
                objArr[2] = "fileToUri";
                break;
            default:
                objArr[2] = "join";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 139 && i != 140 && i != 203 && i != 204 && i != 249 && i != 250) {
            switch (i) {
                case 1:
                case 33:
                case 35:
                case 38:
                case 40:
                case 42:
                case 45:
                case 49:
                case 83:
                case 85:
                case 89:
                case 94:
                case 102:
                case 104:
                case 106:
                case 108:
                case 110:
                case 112:
                case 118:
                case 120:
                case 132:
                case 161:
                case 169:
                case 172:
                case 177:
                case 179:
                case 182:
                case 184:
                case 187:
                case 190:
                case 192:
                case 194:
                case 197:
                case 200:
                case 206:
                case 211:
                case 213:
                case 215:
                case 218:
                case 220:
                case 222:
                case 224:
                case 226:
                case XKeySym.XK_adiaeresis /* 228 */:
                case XKeySym.XK_ae /* 230 */:
                case XKeySym.XK_egrave /* 232 */:
                case XKeySym.XK_ecircumflex /* 234 */:
                case XKeySym.XK_igrave /* 236 */:
                case XKeySym.XK_icircumflex /* 238 */:
                case 240:
                case 242:
                case 252:
                    break;
                default:
                    switch (i) {
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        TObjectHashingStrategy<String> tObjectHashingStrategy;
        REGEX_PATTERN_FLAGS = SystemInfoRt.isFileSystemCaseSensitive ? 0 : 2;
        if (SystemInfoRt.isFileSystemCaseSensitive) {
            tObjectHashingStrategy = TObjectHashingStrategy.CANONICAL;
        } else {
            tObjectHashingStrategy = CaseInsensitiveStringHashingStrategy.INSTANCE;
        }
        PATH_HASHING_STRATEGY = tObjectHashingStrategy;
        FILE_HASHING_STRATEGY = new TObjectHashingStrategy<File>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil.1
            @Override // gnu.trove.TObjectHashingStrategy
            public int computeHashCode(File file) {
                return FileUtil.fileHashCode(file);
            }

            @Override // gnu.trove.TObjectHashingStrategy, gnu.trove.Equality
            public boolean equals(File file, File file2) {
                return FileUtil.filesEqual(file, file2);
            }
        };
        LOG = Logger.getInstance((Class<?>) FileUtil.class);
        SYMLINK_RESOLVER = new FileUtilRt.SymlinkResolver() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
                if (i == 1 || i == 2) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtil$2";
                } else {
                    objArr[0] = "path";
                }
                if (i == 1 || i == 2) {
                    objArr[1] = "resolveSymlinksAndCanonicalize";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileUtil$2";
                }
                if (i != 1 && i != 2) {
                    if (i != 3) {
                        objArr[2] = "resolveSymlinksAndCanonicalize";
                    } else {
                        objArr[2] = "isSymlink";
                    }
                }
                String str2 = String.format(str, objArr);
                if (i != 1 && i != 2) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.SymlinkResolver
            public String resolveSymlinksAndCanonicalize(String str, char c, boolean z) {
                if (str == null) {
                    $$$reportNull$$$0(0);
                }
                try {
                    String strReplace = new File(str).getCanonicalPath().replace(c, '/');
                    if (strReplace == null) {
                        $$$reportNull$$$0(1);
                    }
                    return strReplace;
                } catch (IOException unused) {
                    String canonicalPath = FileUtil.toCanonicalPath(str, c, z, false);
                    if (canonicalPath == null) {
                        $$$reportNull$$$0(2);
                    }
                    return canonicalPath;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt.SymlinkResolver
            public boolean isSymlink(CharSequence charSequence) {
                if (charSequence == null) {
                    $$$reportNull$$$0(3);
                }
                return FileSystemUtil.isSymLink(new File(charSequence.toString()));
            }
        };
    }

    public static String join(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
        String strJoin = String.join(File.separator, strArr);
        if (strJoin == null) {
            $$$reportNull$$$0(1);
        }
        return strJoin;
    }

    public static String getRelativePath(File file, File file2) {
        return FileUtilRt.getRelativePath(file, file2);
    }

    public static String getRelativePath(String str, String str2, char c) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(3);
        }
        return FileUtilRt.getRelativePath(str, str2, c);
    }

    public static String getRelativePath(String str, String str2, char c, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (str2 == null) {
            $$$reportNull$$$0(5);
        }
        return FileUtilRt.getRelativePath(str, str2, c, z);
    }

    public static boolean isAbsolute(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return !str.isEmpty() && new File(str).isAbsolute();
    }

    public static boolean exists(String str) {
        return str != null && new File(str).exists();
    }

    public static boolean isAncestor(File file, File file2, boolean z) {
        if (file == null) {
            $$$reportNull$$$0(7);
        }
        if (file2 == null) {
            $$$reportNull$$$0(8);
        }
        return isAncestor(file.getPath(), file2.getPath(), z);
    }

    public static boolean isAncestor(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        return !ThreeState.NO.equals(isAncestorThreeState(str, str2, z));
    }

    public static ThreeState isAncestorThreeState(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        if (str2 == null) {
            $$$reportNull$$$0(12);
        }
        return startsWith(toCanonicalPath(str2), toCanonicalPath(str), z, SystemInfoRt.isFileSystemCaseSensitive, true);
    }

    public static boolean startsWith(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        if (str2 == null) {
            $$$reportNull$$$0(14);
        }
        return startsWith(str, str2, SystemInfoRt.isFileSystemCaseSensitive);
    }

    public static boolean startsWith(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (str2 == null) {
            $$$reportNull$$$0(16);
        }
        return startsWith(str, str2, z, false);
    }

    public static boolean startsWith(String str, String str2, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (str2 == null) {
            $$$reportNull$$$0(18);
        }
        return !ThreeState.NO.equals(startsWith(str, str2, z2, z, false));
    }

    private static ThreeState startsWith(String str, String str2, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (str2 == null) {
            $$$reportNull$$$0(20);
        }
        int length = str.length();
        int length2 = str2.length();
        if (length2 == 0) {
            ThreeState threeState = length == 0 ? ThreeState.YES : ThreeState.UNSURE;
            if (threeState == null) {
                $$$reportNull$$$0(21);
            }
            return threeState;
        }
        if (length2 > length) {
            ThreeState threeState2 = ThreeState.NO;
            if (threeState2 == null) {
                $$$reportNull$$$0(22);
            }
            return threeState2;
        }
        if (!str.regionMatches(!z2, 0, str2, 0, length2)) {
            ThreeState threeState3 = ThreeState.NO;
            if (threeState3 == null) {
                $$$reportNull$$$0(23);
            }
            return threeState3;
        }
        if (length == length2) {
            ThreeState threeState4 = z ? ThreeState.NO : ThreeState.YES;
            if (threeState4 == null) {
                $$$reportNull$$$0(24);
            }
            return threeState4;
        }
        int i = length2 - 1;
        char cCharAt = str2.charAt(i);
        if (cCharAt == '/' || cCharAt == File.separatorChar) {
            length2 = i;
        }
        char cCharAt2 = str.charAt(length2);
        if (cCharAt2 != '/' && cCharAt2 != File.separatorChar) {
            ThreeState threeState5 = ThreeState.NO;
            if (threeState5 == null) {
                $$$reportNull$$$0(28);
            }
            return threeState5;
        }
        if (!z3) {
            ThreeState threeState6 = ThreeState.YES;
            if (threeState6 == null) {
                $$$reportNull$$$0(25);
            }
            return threeState6;
        }
        if (length2 == length - 1) {
            ThreeState threeState7 = ThreeState.YES;
            if (threeState7 == null) {
                $$$reportNull$$$0(26);
            }
            return threeState7;
        }
        int i2 = length2 + 1;
        int iIndexOf = str.indexOf(cCharAt2, i2);
        if (iIndexOf == -1) {
            iIndexOf = str.indexOf(cCharAt2 == '/' ? 92 : 47, i2);
        }
        ThreeState threeState8 = iIndexOf == -1 ? ThreeState.YES : ThreeState.UNSURE;
        if (threeState8 == null) {
            $$$reportNull$$$0(27);
        }
        return threeState8;
    }

    public static File findAncestor(File file, File file2) {
        if (file == null) {
            $$$reportNull$$$0(29);
        }
        if (file2 == null) {
            $$$reportNull$$$0(30);
        }
        while (file != null && !isAncestor(file, file2, false)) {
            file = file.getParentFile();
        }
        return file;
    }

    public static File getParentFile(File file) {
        if (file == null) {
            $$$reportNull$$$0(31);
        }
        return FileUtilRt.getParentFile(file);
    }

    public static byte[] loadFileBytes(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(32);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            long length = file.length();
            if (length < 0) {
                throw new IOException("File length reported negative, probably doesn't exist");
            }
            if (isTooLarge(length)) {
                throw new FileTooBigException("Attempt to load '" + file + "' in memory buffer, file length is " + length + " bytes.");
            }
            byte[] bArrLoadBytes = loadBytes(fileInputStream, (int) length);
            fileInputStream.close();
            if (bArrLoadBytes == null) {
                $$$reportNull$$$0(33);
            }
            return bArrLoadBytes;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] loadFirstAndClose(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(34);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            copy(inputStream, i, (OutputStream) byteArrayOutputStream);
            inputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArray == null) {
                $$$reportNull$$$0(35);
            }
            return byteArray;
        } catch (Throwable th) {
            inputStream.close();
            throw th;
        }
    }

    public static String loadTextAndClose(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(36);
        }
        return loadTextAndClose(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static String loadTextAndClose(Reader reader) throws IOException {
        if (reader == null) {
            $$$reportNull$$$0(37);
        }
        try {
            return new String(adaptiveLoadText(reader));
        } finally {
            reader.close();
        }
    }

    public static char[] adaptiveLoadText(Reader reader) throws IOException {
        int length;
        if (reader == null) {
            $$$reportNull$$$0(39);
        }
        char[] cArr = new char[4096];
        ArrayList<char[]> arrayList = null;
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = reader.read(cArr, i, cArr.length - i);
            if (i3 <= 0) {
                char[] cArr2 = new char[i2];
                if (arrayList != null) {
                    length = i2;
                    for (char[] cArr3 : arrayList) {
                        System.arraycopy(cArr3, 0, cArr2, i2 - length, cArr3.length);
                        length -= cArr3.length;
                    }
                } else {
                    length = i2;
                }
                System.arraycopy(cArr, 0, cArr2, i2 - length, length);
                return cArr2;
            }
            i += i3;
            if (i2 > 10485760) {
                throw new FileTooBigException("File too big " + reader);
            }
            i2 += i3;
            if (i == cArr.length) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(cArr);
                cArr = new char[Math.min(1048576, cArr.length * 2)];
                i = 0;
            }
        }
    }

    public static byte[] adaptiveLoadBytes(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(41);
        }
        byte[] bArr = new byte[8192];
        ArrayList<byte[]> arrayList = null;
        int i = 0;
        int length = 0;
        while (true) {
            int i2 = inputStream.read(bArr, i, bArr.length - i);
            if (i2 <= 0) {
                byte[] bArrNewByteArray = ArrayUtil.newByteArray(length);
                if (arrayList != null) {
                    for (byte[] bArr2 : arrayList) {
                        System.arraycopy(bArr2, 0, bArrNewByteArray, bArrNewByteArray.length - length, bArr2.length);
                        length -= bArr2.length;
                    }
                }
                System.arraycopy(bArr, 0, bArrNewByteArray, bArrNewByteArray.length - length, length);
                if (bArrNewByteArray == null) {
                    $$$reportNull$$$0(42);
                }
                return bArrNewByteArray;
            }
            i += i2;
            if (length > 10485760) {
                throw new FileTooBigException("File too big " + inputStream);
            }
            length += i2;
            if (i == bArr.length) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(bArr);
                bArr = new byte[Math.min(1048576, bArr.length * 2)];
                i = 0;
            }
        }
    }

    public static Future<Void> asyncDelete(File file) {
        if (file == null) {
            $$$reportNull$$$0(43);
        }
        return asyncDelete(Collections.singleton(file));
    }

    public static Future<Void> asyncDelete(Collection<? extends File> collection) {
        if (collection == null) {
            $$$reportNull$$$0(44);
        }
        final ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = collection.iterator();
        while (it.hasNext()) {
            File fileRenameToTempFileOrDelete = renameToTempFileOrDelete(it.next());
            if (fileRenameToTempFileOrDelete != null) {
                arrayList.add(fileRenameToTempFileOrDelete);
            }
        }
        Future<Void> futureCompletedFuture = arrayList.isEmpty() ? CompletableFuture.completedFuture(null) : AppExecutorUtil.getAppExecutorService().submit(new Callable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return FileUtil.lambda$asyncDelete$0(arrayList);
            }
        });
        if (futureCompletedFuture == null) {
            $$$reportNull$$$0(45);
        }
        return futureCompletedFuture;
    }

    static /* synthetic */ Void lambda$asyncDelete$0(List list) throws Exception {
        Thread threadCurrentThread = Thread.currentThread();
        int priority = threadCurrentThread.getPriority();
        threadCurrentThread.setPriority(1);
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                delete((File) it.next());
            }
            threadCurrentThread.setPriority(priority);
            return null;
        } catch (Throwable th) {
            threadCurrentThread.setPriority(priority);
            throw th;
        }
    }

    private static File renameToTempFileOrDelete(File file) {
        if (file == null) {
            $$$reportNull$$$0(46);
        }
        String tempDirectory = getTempDirectory();
        if (SystemInfoRt.isWindows ? tempDirectory.substring(0, 2).equalsIgnoreCase(file.getAbsolutePath().substring(0, 2)) : true) {
            File tempFile = getTempFile(file.getName(), tempDirectory);
            if (file.renameTo(tempFile)) {
                return tempFile;
            }
        }
        delete(file);
        return null;
    }

    private static File getTempFile(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(47);
        }
        if (str2 == null) {
            $$$reportNull$$$0(48);
        }
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() % 1000);
        while (true) {
            File file = new File(str2, "___" + str + iCurrentTimeMillis + ASYNC_DELETE_EXTENSION);
            if (!file.exists()) {
                return file;
            }
            iCurrentTimeMillis++;
        }
    }

    public static boolean delete(File file) {
        if (file == null) {
            $$$reportNull$$$0(50);
        }
        return FileUtilRt.delete(file);
    }

    public static void delete(Path path) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(51);
        }
        FileUtilRt.deleteRecursivelyNIO(path, null);
    }

    public static boolean createParentDirs(File file) {
        if (file == null) {
            $$$reportNull$$$0(52);
        }
        return FileUtilRt.createParentDirs(file);
    }

    public static boolean createDirectory(File file) {
        if (file == null) {
            $$$reportNull$$$0(53);
        }
        return FileUtilRt.createDirectory(file);
    }

    public static boolean createIfDoesntExist(File file) {
        if (file == null) {
            $$$reportNull$$$0(54);
        }
        return FileUtilRt.createIfNotExists(file);
    }

    public static boolean ensureCanCreateFile(File file) {
        if (file == null) {
            $$$reportNull$$$0(55);
        }
        return FileUtilRt.ensureCanCreateFile(file);
    }

    public static void copy(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(56);
        }
        if (file2 == null) {
            $$$reportNull$$$0(57);
        }
        performCopy(file, file2, true);
    }

    public static void copyContent(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(58);
        }
        if (file2 == null) {
            $$$reportNull$$$0(59);
        }
        performCopy(file, file2, false);
    }

    private static void performCopy(File file, File file2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(60);
        }
        if (file2 == null) {
            $$$reportNull$$$0(61);
        }
        if (filesEqual(file, file2)) {
            return;
        }
        try {
            FileOutputStream fileOutputStreamOpenOutputStream = openOutputStream(file2);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    copy(fileInputStream, fileOutputStreamOpenOutputStream);
                    fileInputStream.close();
                    if (fileOutputStreamOpenOutputStream != null) {
                        fileOutputStreamOpenOutputStream.close();
                    }
                    if (z) {
                        long jLastModified = file.lastModified();
                        if (jLastModified < 0) {
                            LOG.warn("Invalid timestamp " + jLastModified + " of '" + file + FormatHelper.QUOTE);
                        } else if (!file2.setLastModified(jLastModified)) {
                            LOG.warn("Unable to set timestamp " + jLastModified + " to '" + file2 + FormatHelper.QUOTE);
                        }
                    }
                    if (SystemInfoRt.isUnix && file.canExecute()) {
                        FileSystemUtil.clonePermissionsToExecute(file.getPath(), file2.getPath());
                    }
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (fileOutputStreamOpenOutputStream != null) {
                    try {
                        fileOutputStreamOpenOutputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        } catch (IOException e) {
            throw new IOException(String.format("Couldn't copy [%s] to [%s]", file, file2), e);
        }
    }

    private static FileOutputStream openOutputStream(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(62);
        }
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            if (file.getParentFile() == null) {
                throw new IOException("Parent file is null for " + file.getPath(), e);
            }
            createParentDirs(file);
            return new FileOutputStream(file);
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(63);
        }
        if (outputStream == null) {
            $$$reportNull$$$0(64);
        }
        FileUtilRt.copy(inputStream, outputStream);
    }

    public static void copy(InputStream inputStream, int i, OutputStream outputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(65);
        }
        if (outputStream == null) {
            $$$reportNull$$$0(66);
        }
        copy(inputStream, i, outputStream);
    }

    public static void copy(InputStream inputStream, long j, OutputStream outputStream) throws IOException {
        int i;
        if (inputStream == null) {
            $$$reportNull$$$0(67);
        }
        if (outputStream == null) {
            $$$reportNull$$$0(68);
        }
        byte[] bArr = new byte[8192];
        while (j > 0 && (i = inputStream.read(bArr, 0, (int) Math.min(8192, j))) >= 0) {
            j -= (long) i;
            outputStream.write(bArr, 0, i);
        }
    }

    public static void copyFileOrDir(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(69);
        }
        if (file2 == null) {
            $$$reportNull$$$0(70);
        }
        copyFileOrDir(file, file2, file.isDirectory());
    }

    public static void copyFileOrDir(File file, File file2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(71);
        }
        if (file2 == null) {
            $$$reportNull$$$0(72);
        }
        if (z) {
            copyDir(file, file2);
        } else {
            copy(file, file2);
        }
    }

    public static void copyDir(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(73);
        }
        if (file2 == null) {
            $$$reportNull$$$0(74);
        }
        copyDir(file, file2, true);
    }

    public static void copyDirContent(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(75);
        }
        if (file2 == null) {
            $$$reportNull$$$0(76);
        }
        for (File file3 : (File[]) ObjectUtils.notNull(file.listFiles(), ArrayUtilRt.EMPTY_FILE_ARRAY)) {
            copyFileOrDir(file3, new File(file2, file3.getName()));
        }
    }

    public static void copyDir(File file, File file2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(77);
        }
        if (file2 == null) {
            $$$reportNull$$$0(78);
        }
        copyDir(file, file2, z ? null : new FileFilter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil$$ExternalSyntheticLambda2
            @Override // java.io.FileFilter
            public final boolean accept(File file3) {
                return FileUtil.lambda$copyDir$1(file3);
            }
        });
    }

    static /* synthetic */ boolean lambda$copyDir$1(File file) {
        return !StringUtil.startsWithChar(file.getName(), '.');
    }

    public static void copyDir(File file, File file2, FileFilter fileFilter) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(79);
        }
        if (file2 == null) {
            $$$reportNull$$$0(80);
        }
        ensureExists(file2);
        if (isAncestor(file, file2, true)) {
            LOG.error(file.getAbsolutePath() + " is ancestor of " + file2 + ". Can't copy to itself.");
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            throw new IOException(UtilBundle.message("exception.directory.is.invalid", file.getPath()));
        }
        if (!file.canRead()) {
            throw new IOException(UtilBundle.message("exception.directory.is.not.readable", file.getPath()));
        }
        for (File file3 : fileArrListFiles) {
            if (fileFilter == null || fileFilter.accept(file3)) {
                if (file3.isDirectory()) {
                    copyDir(file3, new File(file2, file3.getName()), fileFilter);
                } else {
                    copy(file3, new File(file2, file3.getName()));
                }
            }
        }
    }

    public static void ensureExists(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(81);
        }
        if (!file.exists() && !file.mkdirs()) {
            throw new IOException(UtilBundle.message("exception.directory.can.not.create", file.getPath()));
        }
    }

    public static String getNameWithoutExtension(File file) {
        if (file == null) {
            $$$reportNull$$$0(82);
        }
        String nameWithoutExtension = FileUtilRt.getNameWithoutExtension(file.getName());
        if (nameWithoutExtension == null) {
            $$$reportNull$$$0(83);
        }
        return nameWithoutExtension;
    }

    public static String getNameWithoutExtension(String str) {
        if (str == null) {
            $$$reportNull$$$0(84);
        }
        String nameWithoutExtension = FileUtilRt.getNameWithoutExtension(str);
        if (nameWithoutExtension == null) {
            $$$reportNull$$$0(85);
        }
        return nameWithoutExtension;
    }

    public static String createSequentFileName(File file, String str, String str2) {
        if (file == null) {
            $$$reportNull$$$0(86);
        }
        if (str == null) {
            $$$reportNull$$$0(87);
        }
        if (str2 == null) {
            $$$reportNull$$$0(88);
        }
        String name2 = findSequentNonexistentFile(file, str, str2).getName();
        if (name2 == null) {
            $$$reportNull$$$0(89);
        }
        return name2;
    }

    public static String createSequentFileName(File file, String str, String str2, Predicate<? super File> predicate) {
        if (file == null) {
            $$$reportNull$$$0(90);
        }
        if (str == null) {
            $$$reportNull$$$0(91);
        }
        if (str2 == null) {
            $$$reportNull$$$0(92);
        }
        if (predicate == null) {
            $$$reportNull$$$0(93);
        }
        String name2 = findSequentFile(file, str, str2, predicate).getName();
        if (name2 == null) {
            $$$reportNull$$$0(94);
        }
        return name2;
    }

    public static File findSequentNonexistentFile(File file, String str, String str2) {
        if (file == null) {
            $$$reportNull$$$0(95);
        }
        if (str == null) {
            $$$reportNull$$$0(96);
        }
        if (str2 == null) {
            $$$reportNull$$$0(97);
        }
        return findSequentFile(file, str, str2, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return FileUtil.lambda$findSequentNonexistentFile$2((File) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$findSequentNonexistentFile$2(File file) {
        return !file.exists();
    }

    public static File findSequentFile(File file, String str, String str2, Predicate<? super File> predicate) {
        if (file == null) {
            $$$reportNull$$$0(98);
        }
        if (str == null) {
            $$$reportNull$$$0(99);
        }
        if (str2 == null) {
            $$$reportNull$$$0(100);
        }
        if (predicate == null) {
            $$$reportNull$$$0(101);
        }
        String str3 = str2.isEmpty() ? "" : "." + str2;
        File file2 = new File(file, str + str3);
        int i = 0;
        while (!predicate.test(file2)) {
            i++;
            file2 = new File(file, str + i + str3);
        }
        return file2;
    }

    public static String toSystemDependentName(String str) {
        if (str == null) {
            $$$reportNull$$$0(103);
        }
        String systemDependentName = FileUtilRt.toSystemDependentName(str);
        if (systemDependentName == null) {
            $$$reportNull$$$0(104);
        }
        return systemDependentName;
    }

    public static String toSystemIndependentName(String str) {
        if (str == null) {
            $$$reportNull$$$0(105);
        }
        String systemIndependentName = FileUtilRt.toSystemIndependentName(str);
        if (systemIndependentName == null) {
            $$$reportNull$$$0(106);
        }
        return systemIndependentName;
    }

    public static String toCanonicalPath(String str) {
        return toCanonicalPath(str, File.separatorChar, true);
    }

    public static String toCanonicalPath(String str, boolean z) {
        return toCanonicalPath(str, File.separatorChar, true, z);
    }

    public static String toCanonicalPath(String str, char c) {
        return toCanonicalPath(str, c, true);
    }

    public static String toCanonicalUriPath(String str) {
        return toCanonicalPath(str, '/', false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toCanonicalPath(String str, char c, boolean z, boolean z2) {
        return toCanonicalPath(str, c, z, z2 ? SYMLINK_RESOLVER : null);
    }

    public static String normalize(String str) {
        int i;
        boolean z;
        if (str == null) {
            $$$reportNull$$$0(107);
        }
        if (!SystemInfoRt.isWindows) {
            i = 0;
            z = false;
        } else if (str.startsWith("//")) {
            i = 2;
            z = true;
        } else {
            if (str.startsWith("\\\\")) {
                return normalizeTail(0, str, false);
            }
            i = 0;
            z = false;
        }
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/') {
                if (z) {
                    return normalizeTail(i, str, true);
                }
                z = true;
            } else {
                if (cCharAt == '\\') {
                    return normalizeTail(i, str, z);
                }
                z = false;
            }
            i++;
        }
        if (str == null) {
            $$$reportNull$$$0(108);
        }
        return str;
    }

    private static String normalizeTail(int i, String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(109);
        }
        StringBuilder sb = new StringBuilder(str.length());
        sb.append((CharSequence) str, 0, i);
        if (i == 0 && SystemInfoRt.isWindows && (str.startsWith("//") || str.startsWith("\\\\"))) {
            sb.append("//");
            i = 2;
            z = true;
        }
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/' || cCharAt == '\\') {
                if (!z) {
                    sb.append('/');
                }
                z = true;
            } else {
                sb.append(cCharAt);
                z = false;
            }
            i++;
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(110);
        }
        return string;
    }

    public static String unquote(String str) {
        if (str == null) {
            $$$reportNull$$$0(111);
        }
        String strUnescapePercentSequences = URLUtil.unescapePercentSequences(str.replace('/', File.separatorChar));
        if (strUnescapePercentSequences == null) {
            $$$reportNull$$$0(112);
        }
        return strUnescapePercentSequences;
    }

    public static boolean rename(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(113);
        }
        if (str == null) {
            $$$reportNull$$$0(114);
        }
        File file2 = new File(file.getParent(), str);
        if (!SystemInfoRt.isFileSystemCaseSensitive && str.equalsIgnoreCase(file.getName())) {
            File fileCreateTempFile = createTempFile(file.getParentFile(), file.getName(), ".tmp", false, false);
            return file.renameTo(fileCreateTempFile) && fileCreateTempFile.renameTo(file2);
        }
        return file.renameTo(file2);
    }

    public static void rename(File file, File file2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(115);
        }
        if (file2 == null) {
            $$$reportNull$$$0(116);
        }
        if (!file.renameTo(file2) && file.exists()) {
            copy(file, file2);
            delete(file);
        }
    }

    public static boolean filesEqual(File file, File file2) {
        return pathsEqual(file == null ? null : file.getPath(), file2 != null ? file2.getPath() : null);
    }

    public static boolean pathsEqual(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        String canonicalPath = toCanonicalPath(str);
        String canonicalPath2 = toCanonicalPath(str2);
        return SystemInfoRt.isFileSystemCaseSensitive ? canonicalPath.equals(canonicalPath2) : canonicalPath.equalsIgnoreCase(canonicalPath2);
    }

    public static boolean namesEqual(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return SystemInfoRt.isFileSystemCaseSensitive ? str.equals(str2) : str.equalsIgnoreCase(str2);
    }

    public static int compareFiles(File file, File file2) {
        return comparePaths(file == null ? null : file.getPath(), file2 != null ? file2.getPath() : null);
    }

    public static int comparePaths(String str, String str2) {
        return OSAgnosticPathUtil.COMPARATOR.compare(str, str2);
    }

    public static int fileHashCode(File file) {
        return FileUtilRt.pathHashCode(file == null ? null : file.getPath());
    }

    public static int pathHashCode(String str) {
        return FileUtilRt.pathHashCode(str);
    }

    @Deprecated
    public static String getExtension(String str) {
        if (str == null) {
            $$$reportNull$$$0(117);
        }
        String lowerCase = Strings.toLowerCase(FileUtilRt.getExtension(str));
        if (lowerCase == null) {
            $$$reportNull$$$0(118);
        }
        return lowerCase;
    }

    public static String resolveShortWindowsName(String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(119);
        }
        try {
            if (SystemInfoRt.isWindows && containsWindowsShortName(str)) {
                str = Paths.get(str, new String[0]).toRealPath(LinkOption.NOFOLLOW_LINKS).toString();
            }
            if (str == null) {
                $$$reportNull$$$0(120);
            }
            return str;
        } catch (InvalidPathException e) {
            throw new IOException(e);
        }
    }

    public static boolean containsWindowsShortName(String str) {
        if (str == null) {
            $$$reportNull$$$0(121);
        }
        if (str.indexOf(126) < 0) {
            return false;
        }
        String systemIndependentName = toSystemIndependentName(str);
        int i = 0;
        while (i < systemIndependentName.length()) {
            int iIndexOf = systemIndependentName.indexOf(47, i);
            if (iIndexOf < 0) {
                iIndexOf = systemIndependentName.length();
            }
            int iLastIndexOf = systemIndependentName.lastIndexOf(46, iIndexOf);
            if (iLastIndexOf < i) {
                iLastIndexOf = iIndexOf;
            }
            int i2 = iLastIndexOf - i;
            if (i2 > 2 && i2 <= 8 && (iIndexOf - iLastIndexOf) - 1 <= 3 && systemIndependentName.charAt(iLastIndexOf - 2) == '~' && Character.isDigit(systemIndependentName.charAt(iLastIndexOf - 1))) {
                return true;
            }
            i = iIndexOf + 1;
        }
        return false;
    }

    public static String extractRootPath(String str) {
        int iIndexOf;
        if (str == null) {
            $$$reportNull$$$0(122);
        }
        if (SystemInfoRt.isWindows) {
            if (str.length() >= 2 && str.charAt(1) == ':') {
                return StringUtil.toUpperCase(str.substring(0, 2));
            }
            if (str.startsWith("//") && (iIndexOf = str.indexOf(47, 2)) > 2) {
                int i = iIndexOf + 1;
                int iIndexOf2 = str.indexOf(47, i);
                if (iIndexOf2 > i) {
                    return str.substring(0, iIndexOf2);
                }
                if (iIndexOf2 < 0) {
                    return str;
                }
            }
        } else if (StringUtil.startsWithChar(str, '/')) {
            return "/";
        }
        int iIndexOf3 = str.indexOf("://");
        if (iIndexOf3 != -1) {
            return str.substring(0, iIndexOf3 + "://".length());
        }
        return null;
    }

    public static void collectMatchedFiles(File file, Pattern pattern, List<? super File> list) {
        if (file == null) {
            $$$reportNull$$$0(123);
        }
        if (pattern == null) {
            $$$reportNull$$$0(124);
        }
        if (list == null) {
            $$$reportNull$$$0(125);
        }
        collectMatchedFiles(file, file, pattern, list);
    }

    private static void collectMatchedFiles(File file, File file2, Pattern pattern, List<? super File> list) {
        if (file == null) {
            $$$reportNull$$$0(126);
        }
        if (file2 == null) {
            $$$reportNull$$$0(127);
        }
        if (pattern == null) {
            $$$reportNull$$$0(128);
        }
        if (list == null) {
            $$$reportNull$$$0(129);
        }
        File[] fileArrListFiles = file2.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file3 : fileArrListFiles) {
            if (file3.isFile()) {
                String relativePath = getRelativePath(file, file3);
                if (relativePath != null && pattern.matcher(toSystemIndependentName(relativePath)).matches()) {
                    list.add(file3);
                }
            } else {
                collectMatchedFiles(file, file3, pattern, list);
            }
        }
    }

    public static String convertAntToRegexp(String str) {
        if (str == null) {
            $$$reportNull$$$0(130);
        }
        return convertAntToRegexp(str, true);
    }

    public static String convertAntToRegexp(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(131);
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        int i = 0;
        boolean z3 = true;
        for (int i2 = (z && (StringUtil.startsWithChar(str, '/') || StringUtil.startsWithChar(str, '\\'))) ? 1 : 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '*') {
                i++;
            } else {
                boolean z4 = z3 && i == 2 && (cCharAt == '/' || cCharAt == '\\');
                boolean z5 = i > 0;
                boolean z6 = cCharAt == '/' || cCharAt == '\\';
                if (z4) {
                    sb.append("(?:[^/]+/)*?");
                } else {
                    if (z5) {
                        sb.append("[^/]*?");
                    }
                    if (cCharAt == '(' || cCharAt == ')' || cCharAt == '[' || cCharAt == ']' || cCharAt == '^' || cCharAt == '$' || cCharAt == '.' || cCharAt == '{' || cCharAt == '}' || cCharAt == '+' || cCharAt == '|') {
                        sb.append('\\').append(cCharAt);
                    } else if (cCharAt == '?') {
                        sb.append("[^/]{1}");
                    } else if (cCharAt == '\\') {
                        sb.append('/');
                    } else {
                        sb.append(cCharAt);
                    }
                }
                i = 0;
                z3 = z6;
            }
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '/') {
            z2 = true;
        }
        if ((i == 0 && z2) || (z3 && i == 2)) {
            if (z2) {
                sb.setLength(sb.length() - 1);
            }
            if (sb.length() == 0) {
                sb.append(".*");
            } else {
                sb.append("(?:$|/.+)");
            }
        } else if (i > 0) {
            sb.append("[^/]*?");
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(132);
        }
        return string;
    }

    public static boolean moveDirWithContent(File file, File file2) {
        if (file == null) {
            $$$reportNull$$$0(133);
        }
        if (file2 == null) {
            $$$reportNull$$$0(134);
        }
        if (!file2.exists()) {
            return file.renameTo(file2);
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z = true;
        for (File file3 : fileArrListFiles) {
            z = z && file3.renameTo(new File(file2, file3.getName()));
        }
        file.delete();
        return z;
    }

    public static String sanitizeFileName(String str) {
        if (str == null) {
            $$$reportNull$$$0(135);
        }
        return sanitizeFileName(str, true);
    }

    public static String sanitizeFileName(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(136);
        }
        return sanitizeFileName(str, z, "_");
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0042  */
    public static String sanitizeFileName(String str, boolean z, String str2) {
        boolean z2;
        if (str == null) {
            $$$reportNull$$$0(137);
        }
        if (str2 == null) {
            $$$reportNull$$$0(138);
        }
        int length = str.length();
        StringBuilder sb = null;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt <= 0 || cCharAt >= 255) {
                z2 = false;
            } else {
                if (z) {
                    if (!Character.isLetterOrDigit(cCharAt) && cCharAt != '_') {
                        z2 = true;
                    }
                } else if (!Character.isJavaIdentifierPart(cCharAt) && cCharAt != ' ' && cCharAt != '@' && cCharAt != '-') {
                    z2 = true;
                }
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            if (i < i2) {
                sb.append((CharSequence) str, i, i2);
            }
            if (z2) {
                sb.append(str2);
            }
            i = i2 + 1;
        }
        if (sb == null) {
            if (str == null) {
                $$$reportNull$$$0(139);
            }
            return str;
        }
        if (i < length) {
            sb.append((CharSequence) str, i, length);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(140);
        }
        return string;
    }

    public static boolean canExecute(File file) {
        if (file == null) {
            $$$reportNull$$$0(141);
        }
        return file.canExecute();
    }

    public static boolean canWrite(String str) {
        if (str == null) {
            $$$reportNull$$$0(142);
        }
        FileAttributes attributes = FileSystemUtil.getAttributes(str);
        return attributes != null && attributes.isWritable();
    }

    public static void setReadOnlyAttribute(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(143);
        }
        boolean z2 = !z;
        if (new File(str).setWritable(z2, false) || canWrite(str) == z2) {
            return;
        }
        LOG.warn("Can't set writable attribute of '" + str + "' to '" + z + FormatHelper.QUOTE);
    }

    public static void appendToFile(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(144);
        }
        if (str == null) {
            $$$reportNull$$$0(145);
        }
        writeToFile(file, str.getBytes(StandardCharsets.UTF_8), true);
    }

    public static void writeToFile(File file, byte[] bArr) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(146);
        }
        if (bArr == null) {
            $$$reportNull$$$0(147);
        }
        writeToFile(file, bArr, false);
    }

    public static void writeToFile(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(148);
        }
        if (str == null) {
            $$$reportNull$$$0(149);
        }
        writeToFile(file, str, false);
    }

    public static void writeToFile(File file, String str, Charset charset) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(150);
        }
        if (str == null) {
            $$$reportNull$$$0(151);
        }
        if (charset == null) {
            $$$reportNull$$$0(152);
        }
        writeToFile(file, str.getBytes(charset));
    }

    public static void writeToFile(File file, String str, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(153);
        }
        if (str == null) {
            $$$reportNull$$$0(154);
        }
        writeToFile(file, str.getBytes(StandardCharsets.UTF_8), z);
    }

    public static void writeToFile(File file, byte[] bArr, int i, int i2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(155);
        }
        if (bArr == null) {
            $$$reportNull$$$0(156);
        }
        writeToFile(file, bArr, i, i2, false);
    }

    public static void writeToFile(File file, byte[] bArr, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(157);
        }
        if (bArr == null) {
            $$$reportNull$$$0(158);
        }
        writeToFile(file, bArr, 0, bArr.length, z);
    }

    private static void writeToFile(File file, byte[] bArr, int i, int i2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(159);
        }
        if (bArr == null) {
            $$$reportNull$$$0(160);
        }
        createParentDirs(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file, z);
        try {
            fileOutputStream.write(bArr, i, i2);
            fileOutputStream.close();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Lazy {
        private static final JBTreeTraverser<File> FILE_TRAVERSER = JBTreeTraverser.from(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil$Lazy$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return FileUtil.Lazy.lambda$static$0((File) obj);
            }
        });

        static /* synthetic */ Iterable lambda$static$0(File file) {
            return file == null ? Collections.emptySet() : JBIterable.of((Object[]) file.listFiles());
        }
    }

    public static JBTreeTraverser<File> fileTraverser(File file) {
        JBTreeTraverser<File> jBTreeTraverser = (JBTreeTraverser) Lazy.FILE_TRAVERSER.withRoot(file);
        if (jBTreeTraverser == null) {
            $$$reportNull$$$0(161);
        }
        return jBTreeTraverser;
    }

    public static boolean processFilesRecursively(File file, Processor<? super File> processor) {
        if (file == null) {
            $$$reportNull$$$0(162);
        }
        if (processor == null) {
            $$$reportNull$$$0(163);
        }
        return fileTraverser(file).bfsTraversal().processEach(processor);
    }

    @Deprecated
    public static boolean processFilesRecursively(File file, Processor<? super File> processor, Processor<? super File> processor2) {
        if (file == null) {
            $$$reportNull$$$0(164);
        }
        if (processor == null) {
            $$$reportNull$$$0(165);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(file);
        while (!linkedList.isEmpty()) {
            File file2 = (File) linkedList.removeFirst();
            if (!processor.process(file2)) {
                return false;
            }
            if (processor2 == null || (file2.isDirectory() && processor2.process(file2))) {
                File[] fileArrListFiles = file2.listFiles();
                if (fileArrListFiles != null) {
                    ContainerUtil.addAll(linkedList, fileArrListFiles);
                }
            }
        }
        return true;
    }

    public static File findFirstThatExist(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(166);
        }
        for (String str : strArr) {
            if (!Strings.isEmptyOrSpaces(str)) {
                File file = new File(toSystemDependentName(str));
                if (file.exists()) {
                    return file;
                }
            }
        }
        return null;
    }

    public static List<File> findFilesByMask(Pattern pattern, File file) {
        if (pattern == null) {
            $$$reportNull$$$0(167);
        }
        if (file == null) {
            $$$reportNull$$$0(168);
        }
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    arrayList.addAll(findFilesByMask(pattern, file2));
                } else if (pattern.matcher(file2.getName()).matches()) {
                    arrayList.add(file2);
                }
            }
        }
        return arrayList;
    }

    public static List<File> findFilesOrDirsByMask(Pattern pattern, File file) {
        if (pattern == null) {
            $$$reportNull$$$0(170);
        }
        if (file == null) {
            $$$reportNull$$$0(171);
        }
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (pattern.matcher(file2.getName()).matches()) {
                    arrayList.add(file2);
                }
                if (file2.isDirectory()) {
                    arrayList.addAll(findFilesOrDirsByMask(pattern, file2));
                }
            }
        }
        return arrayList;
    }

    public static String findFileInProvidedPath(String str, String... strArr) {
        if (Strings.isEmpty(str)) {
            return "";
        }
        File file = new File(str);
        if (file.exists() && ArrayUtil.indexOf(strArr, file.getName()) >= 0) {
            return toSystemDependentName(file.getPath());
        }
        if (file.isDirectory()) {
            for (String str2 : strArr) {
                File file2 = new File(file, str2);
                if (str2.equals(file2.getName()) && file2.exists()) {
                    return toSystemDependentName(file2.getPath());
                }
            }
        }
        File parentFile = file.getParentFile();
        if (parentFile == null || !parentFile.exists()) {
            return null;
        }
        for (String str3 : strArr) {
            File file3 = new File(parentFile, str3);
            if (str3.equals(file3.getName()) && file3.exists()) {
                return toSystemDependentName(file3.getPath());
            }
        }
        return null;
    }

    @Deprecated
    public static boolean isAbsolutePlatformIndependent(String str) {
        if (str == null) {
            $$$reportNull$$$0(173);
        }
        return isUnixAbsolutePath(str) || isWindowsAbsolutePath(str);
    }

    @Deprecated
    public static boolean isUnixAbsolutePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(174);
        }
        return str.startsWith("/");
    }

    @Deprecated
    public static boolean isWindowsAbsolutePath(String str) {
        if (str == null) {
            $$$reportNull$$$0(175);
        }
        boolean z = true;
        boolean z2 = str.length() >= 2 && Character.isLetter(str.charAt(0)) && str.charAt(1) == ':';
        if (!z2 || str.length() <= 2) {
            return z2;
        }
        char cCharAt = str.charAt(2);
        if (cCharAt != '/' && cCharAt != '\\') {
            z = false;
        }
        return z;
    }

    public static String getLocationRelativeToUserHome(String str) {
        return getLocationRelativeToUserHome(str, true);
    }

    public static String getLocationRelativeToUserHome(String str, boolean z) {
        if (str == null) {
            return null;
        }
        if (!SystemInfoRt.isUnix && z) {
            return str;
        }
        File file = new File(str);
        File file2 = new File(SystemProperties.getUserHome());
        return isAncestor(file2, file, true) ? "~" + File.separator + getRelativePath(file2, file) : str;
    }

    public static String expandUserHome(String str) {
        if (str == null) {
            $$$reportNull$$$0(176);
        }
        if (str.startsWith("~/") || str.startsWith("~\\")) {
            str = SystemProperties.getUserHome() + str.substring(1);
        }
        if (str == null) {
            $$$reportNull$$$0(177);
        }
        return str;
    }

    public static File[] notNullize(File[] fileArr) {
        return notNullize(fileArr, ArrayUtilRt.EMPTY_FILE_ARRAY);
    }

    public static File[] notNullize(File[] fileArr, File[] fileArr2) {
        if (fileArr2 == null) {
            $$$reportNull$$$0(178);
        }
        if (fileArr == null) {
            fileArr = fileArr2;
        }
        if (fileArr == null) {
            $$$reportNull$$$0(179);
        }
        return fileArr;
    }

    public static boolean isHashBangLine(CharSequence charSequence, String str) {
        int iIndexOf;
        if (str == null) {
            $$$reportNull$$$0(180);
        }
        return charSequence != null && StringUtil.startsWith(charSequence, "#!") && (iIndexOf = Strings.indexOf(charSequence, '\n', 2)) >= 0 && Strings.indexOf(charSequence, str, 2, iIndexOf) != -1;
    }

    public static File createTempDirectory(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(181);
        }
        File fileCreateTempDirectory = FileUtilRt.createTempDirectory(str, str2);
        if (fileCreateTempDirectory == null) {
            $$$reportNull$$$0(182);
        }
        return fileCreateTempDirectory;
    }

    public static File createTempDirectory(String str, String str2, boolean z) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(183);
        }
        File fileCreateTempDirectory = FileUtilRt.createTempDirectory(str, str2, z);
        if (fileCreateTempDirectory == null) {
            $$$reportNull$$$0(184);
        }
        return fileCreateTempDirectory;
    }

    public static File createTempDirectory(File file, String str, String str2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(185);
        }
        if (str == null) {
            $$$reportNull$$$0(186);
        }
        File fileCreateTempDirectory = FileUtilRt.createTempDirectory(file, str, str2);
        if (fileCreateTempDirectory == null) {
            $$$reportNull$$$0(187);
        }
        return fileCreateTempDirectory;
    }

    public static File createTempDirectory(File file, String str, String str2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(188);
        }
        if (str == null) {
            $$$reportNull$$$0(189);
        }
        File fileCreateTempDirectory = FileUtilRt.createTempDirectory(file, str, str2, z);
        if (fileCreateTempDirectory == null) {
            $$$reportNull$$$0(190);
        }
        return fileCreateTempDirectory;
    }

    public static File createTempFile(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(191);
        }
        File fileCreateTempFile = FileUtilRt.createTempFile(str, str2);
        if (fileCreateTempFile == null) {
            $$$reportNull$$$0(192);
        }
        return fileCreateTempFile;
    }

    public static File createTempFile(String str, String str2, boolean z) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(193);
        }
        File fileCreateTempFile = FileUtilRt.createTempFile(str, str2, z);
        if (fileCreateTempFile == null) {
            $$$reportNull$$$0(194);
        }
        return fileCreateTempFile;
    }

    public static File createTempFile(File file, String str, String str2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(195);
        }
        if (str == null) {
            $$$reportNull$$$0(196);
        }
        File fileCreateTempFile = FileUtilRt.createTempFile(file, str, str2);
        if (fileCreateTempFile == null) {
            $$$reportNull$$$0(197);
        }
        return fileCreateTempFile;
    }

    public static File createTempFile(File file, String str, String str2, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(198);
        }
        if (str == null) {
            $$$reportNull$$$0(199);
        }
        File fileCreateTempFile = FileUtilRt.createTempFile(file, str, str2, z);
        if (fileCreateTempFile == null) {
            $$$reportNull$$$0(200);
        }
        return fileCreateTempFile;
    }

    public static File createTempFile(File file, String str, String str2, boolean z, boolean z2) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(201);
        }
        if (str == null) {
            $$$reportNull$$$0(202);
        }
        File fileCreateTempFile = FileUtilRt.createTempFile(file, str, str2, z, z2);
        if (fileCreateTempFile == null) {
            $$$reportNull$$$0(203);
        }
        return fileCreateTempFile;
    }

    public static String getTempDirectory() {
        String tempDirectory = FileUtilRt.getTempDirectory();
        if (tempDirectory == null) {
            $$$reportNull$$$0(204);
        }
        return tempDirectory;
    }

    public static void resetCanonicalTempPathCache(String str) {
        if (str == null) {
            $$$reportNull$$$0(205);
        }
        FileUtilRt.resetCanonicalTempPathCache(str);
    }

    public static File generateRandomTemporaryPath() throws IOException {
        File fileGenerateRandomTemporaryPath = FileUtilRt.generateRandomTemporaryPath();
        if (fileGenerateRandomTemporaryPath == null) {
            $$$reportNull$$$0(206);
        }
        return fileGenerateRandomTemporaryPath;
    }

    public static void setExecutable(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(207);
        }
        NioFiles.setExecutable(file.toPath());
    }

    public static String loadFileOrNull(String str) {
        if (str == null) {
            $$$reportNull$$$0(208);
        }
        return loadFileOrNull(new File(str));
    }

    public static String loadFileOrNull(File file) {
        if (file == null) {
            $$$reportNull$$$0(209);
        }
        try {
            return loadFile(file);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String loadFile(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(210);
        }
        String strLoadFile = FileUtilRt.loadFile(file);
        if (strLoadFile == null) {
            $$$reportNull$$$0(211);
        }
        return strLoadFile;
    }

    public static String loadFile(File file, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(212);
        }
        String strLoadFile = FileUtilRt.loadFile(file, z);
        if (strLoadFile == null) {
            $$$reportNull$$$0(213);
        }
        return strLoadFile;
    }

    public static String loadFile(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(214);
        }
        String strLoadFile = FileUtilRt.loadFile(file, str);
        if (strLoadFile == null) {
            $$$reportNull$$$0(215);
        }
        return strLoadFile;
    }

    public static String loadFile(File file, Charset charset) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(216);
        }
        if (charset == null) {
            $$$reportNull$$$0(217);
        }
        String strValueOf = String.valueOf(FileUtilRt.loadFileText(file, charset));
        if (strValueOf == null) {
            $$$reportNull$$$0(218);
        }
        return strValueOf;
    }

    public static String loadFile(File file, String str, boolean z) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(219);
        }
        String strLoadFile = FileUtilRt.loadFile(file, str, z);
        if (strLoadFile == null) {
            $$$reportNull$$$0(220);
        }
        return strLoadFile;
    }

    public static char[] loadFileText(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(221);
        }
        char[] cArrLoadFileText = FileUtilRt.loadFileText(file);
        if (cArrLoadFileText == null) {
            $$$reportNull$$$0(222);
        }
        return cArrLoadFileText;
    }

    public static char[] loadFileText(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(223);
        }
        char[] cArrLoadFileText = FileUtilRt.loadFileText(file, str);
        if (cArrLoadFileText == null) {
            $$$reportNull$$$0(224);
        }
        return cArrLoadFileText;
    }

    public static char[] loadText(Reader reader, int i) throws IOException {
        if (reader == null) {
            $$$reportNull$$$0(225);
        }
        char[] cArrLoadText = FileUtilRt.loadText(reader, i);
        if (cArrLoadText == null) {
            $$$reportNull$$$0(226);
        }
        return cArrLoadText;
    }

    public static List<String> loadLines(File file) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(XKeySym.XK_atilde);
        }
        List<String> listLoadLines = FileUtilRt.loadLines(file);
        if (listLoadLines == null) {
            $$$reportNull$$$0(XKeySym.XK_adiaeresis);
        }
        return listLoadLines;
    }

    public static List<String> loadLines(File file, String str) throws IOException {
        if (file == null) {
            $$$reportNull$$$0(XKeySym.XK_aring);
        }
        List<String> listLoadLines = FileUtilRt.loadLines(file, str);
        if (listLoadLines == null) {
            $$$reportNull$$$0(XKeySym.XK_ae);
        }
        return listLoadLines;
    }

    public static List<String> loadLines(String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ccedilla);
        }
        List<String> listLoadLines = FileUtilRt.loadLines(str);
        if (listLoadLines == null) {
            $$$reportNull$$$0(XKeySym.XK_egrave);
        }
        return listLoadLines;
    }

    public static List<String> loadLines(String str, String str2) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_eacute);
        }
        List<String> listLoadLines = FileUtilRt.loadLines(str, str2);
        if (listLoadLines == null) {
            $$$reportNull$$$0(XKeySym.XK_ecircumflex);
        }
        return listLoadLines;
    }

    public static List<String> loadLines(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader == null) {
            $$$reportNull$$$0(XKeySym.XK_ediaeresis);
        }
        List<String> listLoadLines = FileUtilRt.loadLines(bufferedReader);
        if (listLoadLines == null) {
            $$$reportNull$$$0(XKeySym.XK_igrave);
        }
        return listLoadLines;
    }

    public static byte[] loadBytes(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(XKeySym.XK_iacute);
        }
        byte[] bArrLoadBytes = FileUtilRt.loadBytes(inputStream);
        if (bArrLoadBytes == null) {
            $$$reportNull$$$0(XKeySym.XK_icircumflex);
        }
        return bArrLoadBytes;
    }

    public static byte[] loadBytes(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(XKeySym.XK_idiaeresis);
        }
        byte[] bArrLoadBytes = FileUtilRt.loadBytes(inputStream, i);
        if (bArrLoadBytes == null) {
            $$$reportNull$$$0(240);
        }
        return bArrLoadBytes;
    }

    public static List<String> splitPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(XKeySym.XK_ntilde);
        }
        List<String> listSplitPath = splitPath(str, File.separatorChar);
        if (listSplitPath == null) {
            $$$reportNull$$$0(242);
        }
        return listSplitPath;
    }

    public static boolean visitFiles(File file, Processor<? super File> processor) {
        if (file == null) {
            $$$reportNull$$$0(243);
        }
        if (processor == null) {
            $$$reportNull$$$0(244);
        }
        if (!processor.process(file)) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return true;
        }
        for (File file2 : fileArrListFiles) {
            if (!visitFiles(file2, processor)) {
                return false;
            }
        }
        return true;
    }

    public static boolean deleteWithRenaming(Path path) {
        if (path == null) {
            $$$reportNull$$$0(245);
        }
        return deleteWithRenaming(path.toFile());
    }

    public static boolean deleteWithRenaming(File file) {
        if (file == null) {
            $$$reportNull$$$0(246);
        }
        File fileFindSequentNonexistentFile = findSequentNonexistentFile(file.getParentFile(), file.getName(), "");
        if (file.renameTo(fileFindSequentNonexistentFile)) {
            file = fileFindSequentNonexistentFile;
        }
        return delete(file);
    }

    public static boolean isFileSystemCaseSensitive(String str) throws FileNotFoundException {
        if (str == null) {
            $$$reportNull$$$0(247);
        }
        FileAttributes attributes = FileSystemUtil.getAttributes(str);
        if (attributes != null) {
            return (attributes.equals(FileSystemUtil.getAttributes(Strings.toUpperCase(str))) && attributes.equals(FileSystemUtil.getAttributes(Strings.toLowerCase(str)))) ? false : true;
        }
        throw new FileNotFoundException(str);
    }

    public static String getUrl(File file) {
        if (file == null) {
            $$$reportNull$$$0(248);
        }
        try {
            String externalForm = file.toURI().toURL().toExternalForm();
            if (externalForm == null) {
                $$$reportNull$$$0(249);
            }
            return externalForm;
        } catch (MalformedURLException unused) {
            String str = StandardFileSystems.FILE_PROTOCOL_PREFIX + file.getAbsolutePath();
            if (str == null) {
                $$$reportNull$$$0(250);
            }
            return str;
        }
    }

    public static URI fileToUri(File file) {
        if (file == null) {
            $$$reportNull$$$0(251);
        }
        URI uriFileToUri = FileUtilRt.fileToUri(file);
        if (uriFileToUri == null) {
            $$$reportNull$$$0(252);
        }
        return uriFileToUri;
    }
}
