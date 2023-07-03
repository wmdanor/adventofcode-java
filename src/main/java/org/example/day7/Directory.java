package org.example.day7;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Directory {
    private final String name;
    private final Map<String, Directory> directories;
    private final Map<String, File> files;

    public Directory(String name) {
        this.name = name;
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
    }

    public Directory(String name, Collection<Directory> directories, Collection<File> files) {
        this.name = name;
        this.directories = directories
                .stream()
                .collect(Collectors.toMap(Directory::getName, Function.identity()));
        this.files = files
                .stream()
                .collect(Collectors.toMap(File::getName, Function.identity()));
    }

    public String getName() {
        return name;
    }

    public Collection<Directory> getDirectories() {
        return directories.values();
    }

    public Collection<Directory> getDirectoriesRecursive() {
        return directories
                .values()
                .stream()
                .reduce(
                        new ArrayList<>(),
                        (list, dir) -> {
                            list.add(dir);

                            list.addAll(dir.getDirectoriesRecursive());

                            return list;
                        },
                        (a, b) -> {
                            a.addAll(b);

                            return a;
                        }
                );
    }

    public Collection<Directory> getDirectoriesRecursive(long maxSize) {
        return directories
                .values()
                .stream()
                .reduce(
                        new ArrayList<>(),
                        (list, dir) -> {
                            if (dir.getSize() <= maxSize) {
                                list.add(dir);
                            }

                            list.addAll(dir.getDirectoriesRecursive(maxSize));

                            return list;
                        },
                        (a, b) -> {
                            a.addAll(b);

                            return a;
                        }
                        );
    }

    public Collection<File> getFiles() {
        return files.values();
    }

    public long getSize() {
        long filesSize = files
                .values()
                .stream()
                .map(File::getSize)
                .reduce(Long::sum)
                .orElse(0L);

        long dirsSize = directories
                .values()
                .stream()
                .map(Directory::getSize)
                .reduce(Long::sum)
                .orElse(0L);

        return filesSize + dirsSize;
    }

    public void addDirectory(Directory directory) {
        directories.put(directory.name, directory);
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
    }

    public Optional<Directory> getDirectory(String name) {
        return Optional.ofNullable(directories.get(name));
    }

    @Override
    public String toString() {
        String l = "- " + this.getName() + " (dir)\n";

        return l + toString(1);
    }

    private String toString(int level) {
        int indentSize = level * 2;
        StringBuilder builder = new StringBuilder();

        for (var file : files.values()) {
            String line = "- " + file.getName() + " (file, size=" + file.getSize() + ")\n";
            builder.append(line.indent(indentSize));
        }

        for (var dir : directories.values()) {
            String line = "- " + dir.getName() + " (dir)\n";
            builder.append(line.indent(indentSize));

            builder.append(dir.toString(level + 1));
        }

        return builder.toString();
    }
}
