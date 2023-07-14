import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

class PHPFormatter {

    public static String formatPhpCode(String unformattedCode) {
        String regex = "(\"(?:\\\\.|[^\"]\\\\)*\")|('(?:\\\\.|[^'\\\\])*')|/\\*(.|\\n|\\r)*\\*/|//.*|[{]|[}]|;|#.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unformattedCode);

        StringBuffer code = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("//") || match.startsWith("#")) {
                matcher.appendReplacement(code, match);
            } else if (match.startsWith("/*") && match.endsWith("*/")) {
                if (match.indexOf("\n") == -1) {
                    matcher.appendReplacement(code, "\n/*\n" + match.substring(2, match.length() - 2) + "\n*/\n");
                } else {
                    matcher.appendReplacement(code, match);
                }
            } else if (match.equals("{")) {
                matcher.appendReplacement(code, "\n{\n");
            } else if (match.equals("}")) {
                matcher.appendReplacement(code, "\n}\n");
            } else if (match.equals(";")) {
                matcher.appendReplacement(code, ";\n");
            } else {
                matcher.appendReplacement(code, match);
            }
        }
        matcher.appendTail(code);

        String[] lines = code.toString().split("\n");
        List<String> formattedLines = new ArrayList<>();

        int indentationLevel = 0;
        int indentSize = 4;

        boolean isCommentLine = false;

        for (String line : lines) {
            Pattern pattern2 = Pattern.compile("(\"(?:\\\\.|[^\"\\\\])*\")|('(?:\\\\.|[^'\\\\])*')|/\\*|\\*/|//.*|#.*");
            Matcher matcher2 = pattern2.matcher(line);
            while (matcher2.find()) {
                if (matcher2.group().equals("/*")) {
                    isCommentLine = true;
                }
                if (matcher2.group().equals("*/")) {
                    isCommentLine = false;
                }
            }

            StringBuilder formattedLine = new StringBuilder();

            if (isCommentLine) {
                formattedLine.append(line.trim());
            } else {
                Matcher matcher3 = pattern2.matcher(line);
                List<String> segments = new ArrayList<>();
                while (matcher3.find()) {
                    segments.add(matcher3.group());
                }

                int currentIndex = 0;
                for (String segment : segments) {
                    int startIndex = line.indexOf(segment, currentIndex);
                    if (startIndex > currentIndex) {
                        String leadingSpaces = line.substring(currentIndex, startIndex);
                        formattedLine.append(leadingSpaces.replaceAll("\\s+", " "));
                    }
                    formattedLine.append(segment);
                    currentIndex = startIndex + segment.length();
                }

                if (currentIndex < line.length()) {
                    String trailingSpaces = line.substring(currentIndex);
                    formattedLine.append(trailingSpaces.replaceAll("\\s+", " "));
                }
            }

            formattedLines.add(formattedLine.toString());

            String trimmedLine = formattedLine.toString().trim();
            if (trimmedLine.isEmpty() && !isCommentLine) {
                formattedLines.remove(formattedLines.size() - 1);
            } else if (!trimmedLine.isEmpty()) {
                String indentation = repeat(" ", indentationLevel * indentSize);
                formattedLines.set(formattedLines.size() - 1, indentation + trimmedLine);

                if (trimmedLine.equals("{")) {
                    indentationLevel++;
                } else if (trimmedLine.equals("}")) {
                    if(indentationLevel > 0) {
                        indentationLevel--;
                    }
                    indentation = repeat(" ", indentationLevel * indentSize);
                    formattedLines.set(formattedLines.size() - 1, indentation + trimmedLine);
                }
            }
        }

        return String.join("\n", formattedLines);
    }

    private static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }

    public static String formatPhpCode(File file) {

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String unformattedCode =  content.toString();

        String regex = "(\"(?:\\\\.|[^\"]\\\\)*\")|('(?:\\\\.|[^'\\\\])*')|/\\*(.|\\n|\\r)*\\*/|//.*|[{]|[}]|;|#.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unformattedCode);

        StringBuffer code = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("//") || match.startsWith("#")) {
                matcher.appendReplacement(code, match);
            } else if (match.startsWith("/*") && match.endsWith("*/")) {
                if (match.indexOf("\n") == -1) {
                    matcher.appendReplacement(code, "\n/*\n" + match.substring(2, match.length() - 2) + "\n*/\n");
                } else {
                    matcher.appendReplacement(code, match);
                }
            } else if (match.equals("{")) {
                matcher.appendReplacement(code, "\n{\n");
            } else if (match.equals("}")) {
                matcher.appendReplacement(code, "\n}\n");
            } else if (match.equals(";")) {
                matcher.appendReplacement(code, ";\n");
            } else {
                matcher.appendReplacement(code, match);
            }
        }
        matcher.appendTail(code);

        String[] lines = code.toString().split("\n");
        List<String> formattedLines = new ArrayList<>();

        int indentationLevel = 0;
        int indentSize = 4;

        boolean isCommentLine = false;

        for (String line : lines) {
            Pattern pattern2 = Pattern.compile("(\"(?:\\\\.|[^\"\\\\])*\")|('(?:\\\\.|[^'\\\\])*')|/\\*|\\*/|//.*|#.*");
            Matcher matcher2 = pattern2.matcher(line);
            while (matcher2.find()) {
                if (matcher2.group().equals("/*")) {
                    isCommentLine = true;
                }
                if (matcher2.group().equals("*/")) {
                    isCommentLine = false;
                }
            }

            StringBuilder formattedLine = new StringBuilder();

            if (isCommentLine) {
                formattedLine.append(line.trim());
            } else {
                Matcher matcher3 = pattern2.matcher(line);
                List<String> segments = new ArrayList<>();
                while (matcher3.find()) {
                    segments.add(matcher3.group());
                }

                int currentIndex = 0;
                for (String segment : segments) {
                    int startIndex = line.indexOf(segment, currentIndex);
                    if (startIndex > currentIndex) {
                        String leadingSpaces = line.substring(currentIndex, startIndex);
                        formattedLine.append(leadingSpaces.replaceAll("\\s+", " "));
                    }
                    formattedLine.append(segment);
                    currentIndex = startIndex + segment.length();
                }

                if (currentIndex < line.length()) {
                    String trailingSpaces = line.substring(currentIndex);
                    formattedLine.append(trailingSpaces.replaceAll("\\s+", " "));
                }
            }

            formattedLines.add(formattedLine.toString());

            String trimmedLine = formattedLine.toString().trim();
            if (trimmedLine.isEmpty() && !isCommentLine) {
                formattedLines.remove(formattedLines.size() - 1);
            } else if (!trimmedLine.isEmpty()) {
                String indentation = repeat(" ", indentationLevel * indentSize);
                formattedLines.set(formattedLines.size() - 1, indentation + trimmedLine);

                if (trimmedLine.equals("{")) {
                    indentationLevel++;
                } else if (trimmedLine.equals("}")) {
                    if(indentationLevel > 0) {
                        indentationLevel--;
                    }
                    indentation = repeat(" ", indentationLevel * indentSize);
                    formattedLines.set(formattedLines.size() - 1, indentation + trimmedLine);
                }
            }
        }

        return String.join("\n", formattedLines);
    }

}
