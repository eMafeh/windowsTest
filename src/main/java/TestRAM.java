import java.util.Arrays;

public class TestRAM {
    public static void main(String[] args) {
        int[][] ints = new int[12][Integer.MAX_VALUE / 2];
        while (true) {
            long l = System.currentTimeMillis();
            Arrays.stream(ints)
                    .parallel()
                    .forEach(is -> {

                        for (int i = 0; i < is.length; i++) {
                            is[i] = i;
                        }

                    });
            System.out.println(System.currentTimeMillis() - l);
        }
    }

    private void m() {
        int a = 10;
        int b = 20;
        int c = 20;
        int d = 20;
        int e = 20;
        int f = 20;
    }
}
