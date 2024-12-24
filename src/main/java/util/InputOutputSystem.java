package util;

import enums.Colors;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class InputOutputSystem {

    public static void findHistory() throws IOException {
        File history = new File("hist.txt");

        if (!history.exists())
            history.createNewFile();

        if (!history.canWrite())
            log(Colors.RED, "Erro ao acessar o historico: " + history.getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(history));
        bufferedReader.lines()
                .filter(l -> !l.equals("clear"))
                .toList()
                .stream()
                .limit(5).
                forEach(System.out::println);
    }

    public static boolean saveCommand(String in, File file) {
        try {
            clearHistoryForSize(file);

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((in + "\n").getBytes());
            return true;
        } catch (Exception e) {
            log(Colors.RED, "Erro ao salvar o arquivo: " + e.getMessage());
            return false;
        }
    }


    private static void clearHistoryForSize(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.size() > 20) {
            List<String> lastLines = lines.subList(lines.size() - 15, lines.size());
            Files.write(file.toPath(), lastLines, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public static void log(Colors colorLog, String msg) {
        System.out.println(colorLog.getCodigo() + msg + Colors.RESET.getCodigo());
    }
}
