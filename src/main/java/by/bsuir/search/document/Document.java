package by.bsuir.search.document;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import by.bsuir.search.document.Splitter;
import org.apache.commons.io.FileUtils;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Document {

    public Document() {

    }

    public static List<FileContainer> readsFileFromDirectory(final String directory) {
        final File folder = new File(directory);
        final List<FileContainer> files = new ArrayList<>();
        for (final File fileDirectory : Objects.requireNonNull(folder.listFiles())) {
            for (final File file : Objects.requireNonNull(fileDirectory.listFiles())) {
                try {
                    FileContainer fc = new FileContainer(
                            file.getName(), fileDirectory.getName(),
                            FileUtils.readFileToString(file)
                    );
                    files.add(fc);
                } catch (IOException e) {
                    throw new UncheckedIOException(format("Unable to read file=%s", fileDirectory.getName()), e);
                }
            }
        }
        return files;
    }
}
