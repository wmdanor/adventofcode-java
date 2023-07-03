package org.example.day7;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Day7 {
    public static final long totalSpace = 70000000;
    public static final long neededSpace = 30000000;

    private Directory root;
    public static void run(String path) throws IOException, RuntimeException {
        var day7 = new Day7(path);

        Directory root = day7.getRoot();

        var dirs = root.getDirectoriesRecursive(100000);

        System.out.println("Dirs under 100000");
        dirs.forEach(d -> System.out.println(d.getName()));

        System.out.println("Their size = " + dirs
                .stream()
                .map(Directory::getSize)
                .reduce(Long::sum)
                .orElse(0L));

        // Part 2
        long rootSize = root.getSize();

        long spaceToFree = neededSpace - (totalSpace - rootSize);

        if (spaceToFree < 0) {
            throw new IllegalStateException();
        }

        System.out.println(spaceToFree);

        var dirToDelete = root.getDirectoriesRecursive()
                .stream()
                .filter(d -> d.getSize() > spaceToFree)
                .min(Comparator.comparingLong(Directory::getSize));

        System.out.println("Dir to delete " +
                dirToDelete.get().getName() + " " + dirToDelete.get().getSize());
    }

    public Day7(String path) throws IOException, RuntimeException {
        parseFile(path);

        System.out.println(root);
    }

    public Directory getRoot() {
        return root;
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        var lsPattern = Pattern.compile("^\\$ ls$");
        var cdPattern = Pattern.compile("^\\$ cd (?<dir>[a-zA-Z0-9]+|\\/|\\.{2})$");
        var dirPattern = Pattern.compile("^dir (?<dir>[a-zA-Z0-9]+)$");
        var filePattern = Pattern.compile("(?<size>^[0-9]+) (?<file>[a-zA-Z0-9.]+)$");

        root = new Directory("/");
        Directory currentDir = null;

        List<Directory> currentPath = new ArrayList<>(List.of(root));

        boolean isLs = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            var lsMatcher = lsPattern.matcher(line);
            var cdMatcher = cdPattern.matcher(line);
            var dirMatcher = dirPattern.matcher(line);
            var fileMatcher = filePattern.matcher(line);

            if (cdMatcher.find()) {
                isLs = false;

                String dir = cdMatcher.group("dir");

                if (dir.equals("/")) {
                    currentPath = new ArrayList<>(List.of(root));
                    currentDir = root;

                    continue;
                }

                if (dir.equals("..")) {
                    currentPath.remove(currentPath.size() - 1);
                    currentDir = currentPath.get(currentPath.size() - 1);

                    continue;
                }

                Optional<Directory> nextDir = currentDir.getDirectory(dir);

                if (nextDir.isPresent()) {
                    currentPath.add(nextDir.get());
                    currentDir = nextDir.get();
                } else {
                    currentDir = new Directory(dir);
                    currentPath.add(currentDir);
                }

                continue;
            }

            if (lsMatcher.find()) {
                isLs = true;

                continue;
            }

            if (!isLs) {
                throw new IllegalStateException(
                        "\"" + line + "\n is not in ls mode, but did not match commands patterns");
            }

            if (dirMatcher.find()) {
                String dir = dirMatcher.group("dir");

                Optional<Directory> lsDir = currentDir.getDirectory(dir);

                if (lsDir.isEmpty()) {
                    currentDir.addDirectory(new Directory(dir));
                }

                continue;
            }

            if (fileMatcher.find()) {
                long size = Long.parseLong(fileMatcher.group("size"));
                String file = fileMatcher.group("file");

                currentDir.addFile(new File(file, size));

                continue;
            }

            throw new IllegalStateException("\"" + line + "\n line did not match any patterns");
        }
    }
}
