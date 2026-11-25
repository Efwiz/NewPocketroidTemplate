package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/PathConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PathConverter extends AbstractSingleValueConverter {
    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls != null && Path.class.isAssignableFrom(cls);
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Path fromString(String str) {
        try {
            try {
                URI uri = new URI(str);
                if (uri.getScheme() != null && uri.getScheme().length() != 1) {
                    return Paths.get(uri);
                }
                return Paths.get(File.separatorChar != '/' ? str.replace('/', File.separatorChar) : str, new String[0]);
            } catch (URISyntaxException unused) {
                return Paths.get(str, new String[0]);
            }
        } catch (InvalidPathException e) {
            throw new ConversionException(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        Path path = (Path) obj;
        if (path.getFileSystem() == FileSystems.getDefault()) {
            String string = path.toString();
            return File.separatorChar != '/' ? string.replace(File.separatorChar, '/') : string;
        }
        return path.toUri().toString();
    }
}
