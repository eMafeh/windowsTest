import fun.qianrui.staticUtil.file.Logger;


public class LoggerResize {
    public static void main(String[] args) {
        Logger logger = new Logger("F:\\Downloads\\bigTs\\resource\\m3u8ToResource\\cachelogreLogger");
        System.out.println(logger.length());
        System.out.println(logger.reLogger(1000000 * 100)
                .length());
    }
}
