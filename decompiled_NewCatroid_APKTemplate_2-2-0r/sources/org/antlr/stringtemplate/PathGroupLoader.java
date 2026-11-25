package org.antlr.stringtemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes12.dex */
public class PathGroupLoader implements StringTemplateGroupLoader {
    static /* synthetic */ Class class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer;
    protected List dirs;
    protected StringTemplateErrorListener errors;
    String fileCharEncoding;

    public PathGroupLoader(StringTemplateErrorListener stringTemplateErrorListener) {
        this.dirs = null;
        this.errors = null;
        this.fileCharEncoding = System.getProperty(SystemProperties.FILE_ENCODING);
        this.errors = stringTemplateErrorListener;
    }

    public PathGroupLoader(String str, StringTemplateErrorListener stringTemplateErrorListener) {
        this.dirs = null;
        this.errors = null;
        this.fileCharEncoding = System.getProperty(SystemProperties.FILE_ENCODING);
        this.errors = stringTemplateErrorListener;
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":", false);
        while (stringTokenizer.hasMoreElements()) {
            String str2 = (String) stringTokenizer.nextElement();
            if (this.dirs == null) {
                this.dirs = new ArrayList();
            }
            this.dirs.add(str2);
        }
    }

    @Override // org.antlr.stringtemplate.StringTemplateGroupLoader
    public StringTemplateGroup loadGroup(String str, Class cls, StringTemplateGroup stringTemplateGroup) throws Throwable {
        StringTemplateGroup stringTemplateGroup2;
        Class clsClass$ = class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer;
        if (clsClass$ == null) {
            clsClass$ = class$("org.antlr.stringtemplate.language.AngleBracketTemplateLexer");
            class$org$antlr$stringtemplate$language$AngleBracketTemplateLexer = clsClass$;
        }
        if (cls == null) {
            cls = clsClass$;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReaderLocate = locate(new StringBuffer().append(str).append(".stg").toString());
                try {
                    try {
                        if (bufferedReaderLocate == null) {
                            error(new StringBuffer("no such group file ").append(str).append(".stg").toString());
                            if (bufferedReaderLocate != null) {
                                try {
                                    bufferedReaderLocate.close();
                                } catch (IOException e) {
                                    error(new StringBuffer("Cannot close template group file: ").append(str).append(".stg").toString(), e);
                                }
                            }
                            return null;
                        }
                        stringTemplateGroup2 = new StringTemplateGroup(bufferedReaderLocate, cls, this.errors, stringTemplateGroup);
                        try {
                            bufferedReaderLocate.close();
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReaderLocate;
                            error(new StringBuffer("can't load group ").append(str).toString(), e);
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e3) {
                                    str = new StringBuffer("Cannot close template group file: ").append(str).append(".stg").toString();
                                    error(str, e3);
                                }
                            }
                        }
                        return stringTemplateGroup2;
                    } catch (IOException e4) {
                        e = e4;
                        stringTemplateGroup2 = null;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReaderLocate;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            error(new StringBuffer("Cannot close template group file: ").append(str).append(".stg").toString(), e5);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e = e6;
            stringTemplateGroup2 = null;
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.antlr.stringtemplate.StringTemplateGroupLoader
    public StringTemplateGroup loadGroup(String str, StringTemplateGroup stringTemplateGroup) {
        return loadGroup(str, null, stringTemplateGroup);
    }

    @Override // org.antlr.stringtemplate.StringTemplateGroupLoader
    public StringTemplateGroup loadGroup(String str) {
        return loadGroup(str, null);
    }

    @Override // org.antlr.stringtemplate.StringTemplateGroupLoader
    public StringTemplateGroupInterface loadInterface(String str) {
        try {
            BufferedReader bufferedReaderLocate = locate(new StringBuffer().append(str).append(".sti").toString());
            if (bufferedReaderLocate != null) {
                return new StringTemplateGroupInterface(bufferedReaderLocate, this.errors);
            }
            error(new StringBuffer("no such interface file ").append(str).append(".sti").toString());
            return null;
        } catch (IOException e) {
            error(new StringBuffer("can't load interface ").append(str).toString(), e);
            return null;
        }
    }

    protected BufferedReader locate(String str) throws IOException {
        for (int i = 0; i < this.dirs.size(); i++) {
            String string = new StringBuffer().append((String) this.dirs.get(i)).append("/").append(str).toString();
            if (new File(string).exists()) {
                return new BufferedReader(getInputStreamReader(new FileInputStream(string)));
            }
        }
        return null;
    }

    protected InputStreamReader getInputStreamReader(InputStream inputStream) {
        try {
            return new InputStreamReader(inputStream, this.fileCharEncoding);
        } catch (UnsupportedEncodingException unused) {
            error(new StringBuffer("Invalid file character encoding: ").append(this.fileCharEncoding).toString());
            return null;
        }
    }

    public String getFileCharEncoding() {
        return this.fileCharEncoding;
    }

    public void setFileCharEncoding(String str) {
        this.fileCharEncoding = str;
    }

    public void error(String str) {
        error(str, null);
    }

    public void error(String str, Exception exc) {
        StringTemplateErrorListener stringTemplateErrorListener = this.errors;
        if (stringTemplateErrorListener != null) {
            stringTemplateErrorListener.error(str, exc);
            return;
        }
        System.err.println(new StringBuffer("StringTemplate: ").append(str).toString());
        if (exc != null) {
            exc.printStackTrace();
        }
    }
}
