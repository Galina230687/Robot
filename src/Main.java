import java.util.*;

public class Main {

    public static String LETTERS = "RLRFR";
    public static int LENGHT = 10; // должно быть 1000
    public static char NEED_COUNT_CHAR = 'R';
    public static final Map<Integer, Integer> SIZE_TO_FREQ = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < LENGHT; i++) {

            Runnable logic = () -> {  // содержание потока

                String TMP = generateRoute(LETTERS, LENGHT);
                int frequency = countChar(TMP, NEED_COUNT_CHAR);
                System.out.println(TMP + "->" + frequency);


                synchronized (SIZE_TO_FREQ) {
                    if (SIZE_TO_FREQ.containsKey(frequency)) {
                        SIZE_TO_FREQ.put(frequency, SIZE_TO_FREQ.get(frequency) + 1);
                    } else {
                        SIZE_TO_FREQ.put(frequency, 1);
                    }
                }
            };

            logic.run();
        }


        List<Map.Entry<Integer, Integer>> result = SIZE_TO_FREQ.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .toList();

        Iterator<Map.Entry<Integer, Integer>> iterator = result.iterator();
        Map.Entry<Integer, Integer> entry;
        System.out.println("Самое частое количество повторений " + result.get(0).getKey() + " (встретилось " + result.get(0).getValue() + " раз)");

        System.out.println("Другие размеры:");
        while (iterator.hasNext()) {
            entry = iterator.next();
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }

    }

    private static int countChar(String TMP, char NEED_COUNT_CHAR) { // вычисление количества R
        int count = 0;
        for (int i = 0; i < TMP.length(); i++) {
            if (TMP.charAt(i) == NEED_COUNT_CHAR) {
                count++;
            }
        }

        return count;
    }

    public static String generateRoute(String LETTERS, int LENGHT) {  //генератор маршрутов
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < LENGHT; i++) {
            route.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return route.toString();
    }
}


