package util;

import java.io.*;

public class IO {

    public static void findHistory() throws IOException {
        File history = new File("/mnt/5ABE59B542AC2370/Experimentos/hist.txt");
        if (!history.canWrite()) {
            log(Colors.RED,"Erro ao acessar o historico: " + history.getAbsolutePath());
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(history));
        bufferedReader.lines()
                .filter(l -> !l.equals("clear"))
                .toList()
                .stream()
                .limit(5).
                forEach(System.out::println);
    }

    public static boolean saveCommand(String in ,File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((in + "\n").getBytes());
            return true;
        } catch (Exception e) {
            log(Colors.RED , "Erro ao salvar o arquivo: " + e.getMessage());
            return false;
        }
    }

    public static void log(Colors colorLog , String msg ) {
        System.out.println(colorLog.getCodigo() + msg + Colors.RESET.getCodigo());
    }
}
