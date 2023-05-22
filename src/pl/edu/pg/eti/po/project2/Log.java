package pl.edu.pg.eti.po.project2;

public class Log {
    private static String text = "";

    public static void addToLog(String info) {
        text += info + "\n";
    }

    public static String writeLog() {
        return text;
    }

    public static void deleteLog() {
        text = "";
    }
}
