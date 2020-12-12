package com.mzielinski.advent.of.code.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public abstract class StreamReader<T> implements Closeable {

    private final InputStream is;

    public StreamReader(String filePath) {
        this.is = requireNonNull(StreamReader.class.getClassLoader().getResourceAsStream(filePath));
    }

    @Override
    public void close() {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot close stream", e);
        }
    }

    public Stream<T> getStream() {
        Reader r = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(r);
        return br.lines().map(this::processLines);
    }

    protected abstract T processLines(String line);
}
