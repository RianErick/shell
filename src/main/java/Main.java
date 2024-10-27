import util.Colors;

import java.awt.color.ColorSpace;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.IO.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("/mnt/5ABE59B542AC2370/Experimentos/hist.txt");

        if (!file.exists()) {
            boolean newFile = file.createNewFile();

            if (!newFile) {
                log(Colors.RED , "Erro ao criar o arquivo");
            }
        }

        while (true) {
            log(Colors.GREEN , "rshell>");
            String in = scanner.nextLine();

            switch (in) {
                case "exit": break;

                case "history": findHistory();
                continue;
            }

            List<String> commandParts = Arrays.asList(in.split(" "));
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);

            boolean saved = saveCommand(in, file);

            if (saved) {
                try {
                    Process process = processBuilder.start();
                    InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                    BufferedReader stdInput = new BufferedReader(inputStreamReader);
                    stdInput.lines().forEach(ln -> log(Colors.PURPLE , ln));

                    InputStreamReader errorStreamReader = new InputStreamReader(process.getErrorStream());
                    BufferedReader stdError = new BufferedReader(errorStreamReader);
                    stdError.lines().forEach(err -> log(Colors.RED , err));
                } catch (IOException e) {
                    log(Colors.RED , e.getMessage());
                }
            }
        }
    }
}
