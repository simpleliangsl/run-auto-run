package org.simple.autorun.command;

import org.apache.commons.lang3.StringUtils;
import org.simple.autorun.common.LogWrapper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sliang on 10/25/17.
 */
public class FileCommand {

    public static String readDefault(String filePath) {
        return read(filePath, Charset.defaultCharset());
    }

    public static String readUTF8(String filePath) {
        return read(filePath, StandardCharsets.UTF_8);
    }

    public static String read(String filePath, String charsetName) {
        boolean isDefault = charsetName == null || charsetName.isEmpty() || charsetName.equalsIgnoreCase("default");
        return read(filePath, isDefault ? Charset.defaultCharset() : Charset.forName(charsetName));
    }

    private static String read(String filePath, Charset charset) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(filePath), charset);
        } catch (Exception e) {
            LogWrapper.current().fatal(e);
        }

        return lines != null ? StringUtils.join(lines, System.lineSeparator()) : null;
    }

    public static void writeDefault(String filePath, String line) {
        write(filePath, line, Charset.defaultCharset());
    }

    public static void writeUTF8(String filePath, String line) {
        write(filePath, line, StandardCharsets.UTF_8);
    }

    public static void write(String filePath, String line, String charsetName) {
        boolean isDefault = charsetName == null || charsetName.isEmpty() || charsetName.equalsIgnoreCase("default");
        write(filePath, line, isDefault ? Charset.defaultCharset() : Charset.forName(charsetName));
    }

    private static void write(String filePath, String line, Charset charset, OpenOption... options) {

        List<String> lines = new ArrayList<String>(){{this.add(line);}};

        try {
            Files.write(Paths.get(filePath), lines, charset, options);
        } catch (Exception e) {
            LogWrapper.current().fatal(e);
        }
    }

    public static void appendDefault(String filePath, String line) {
        append(filePath, line, Charset.defaultCharset());
    }

    public static void appendUTF8(String filePath, String line) {
        append(filePath, line, StandardCharsets.UTF_8);
    }

    public static void append(String filePath, String line, String charsetName) {
        append(filePath, line, Charset.forName(charsetName));
    }

    private static void append(String filePath, String line, Charset charset) {
        write(filePath, line, charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
    }
}
