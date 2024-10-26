import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // System.getProperty("user.dir");
        File file = new File("/mnt/5ABE59B542AC2370/Experimentos/hist.txt");
        if (!file.exists())
            file.createNewFile();

        while (true) {
            System.out.println(("rshell> "));
            String in = scanner.nextLine();

            switch (in) {
                case "exit":
                    break;
                case "history":
                    findHistory();
                    continue;
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write((in + "\n").getBytes());
            } catch (Exception e) {
                System.out.println("Erro ao salvar o comando: " + e.getMessage());
            }

            List<String> commandParts = Arrays.asList(in.split(" "));
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);

            try {
                Process process = processBuilder.start();

                InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
                BufferedReader stdInput = new BufferedReader(inputStreamReader);
                stdInput.lines().forEach(System.out::println);

                InputStreamReader errorStreamReader = new InputStreamReader(process.getErrorStream());
                BufferedReader stdError = new BufferedReader(errorStreamReader);
                stdError.lines().forEach(System.err::println);

            } catch (IOException e) {
                System.out.println("Erro ao executar o comando: " + e.getMessage());
            }
        }
    }

    static void findHistory() throws IOException {
        File history = new File("/mnt/5ABE59B542AC2370/Experimentos/hist.txt");
        if (!history.canWrite()) {
            System.out.println("Erro ao acessar o historico: " + history.getAbsolutePath());
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(history));
        bufferedReader.lines()
                .filter(l -> !l.equals("clear"))
                .toList()
                .stream()
                .limit(5).
                forEach(System.out::println);
    }
}
