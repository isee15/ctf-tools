package ctf;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileResource {
    private ArrayList<String> wordList = new ArrayList<>();

    public FileResource(String path) {
        // It's even easier in Java 8
        try {
            System.out.println(getClass().getResource(path));
            Files.lines(Paths.get(getClass().getResource(path).getPath()), StandardCharsets.UTF_8)
                    .forEach((line) -> {
                        System.out.println(line);
                        wordList.add(line);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> words() {
        return wordList;
    }

}
