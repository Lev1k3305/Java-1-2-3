import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class CyberDetectors {

    // Параметры (можно менять)
    static final int ARRAY_SIZE = 100_000;
    static final int N_SEGMENTS = 20; // количество детекторов
    static final int ANOMALY_THRESHOLD = 9000;

    // Результат сканирования сегмента
    public static class Result {
        public final int detectorId;
        public final int startIndex;
        public final int endIndex; 
        public final long sum;
        public final int anomalies;
        public final int phase; 

        public Result(int detectorId, int startIndex, int endIndex, long sum, int anomalies) {
            this(detectorId, startIndex, endIndex, sum, anomalies, 1);
        }

        public Result(int detectorId, int startIndex, int endIndex, long sum, int anomalies, int phase) {
            this.detectorId = detectorId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.sum = sum;
            this.anomalies = anomalies;
            this.phase = phase;
        }

        @Override
        public String toString() {
            return String.format("[Детектор-%d] Сектор %d–%d просканирован. Сумма = %d, Аномалий = %d",
                    detectorId, startIndex, endIndex, sum, anomalies);
        }
    }

    // Этап 1: Ручные потоки
    static class ManualDetector implements Runnable {
        private final int id;
        private final int[] array;
        private final int start;
        private final int end; 
        private final AtomicLong globalAtomic; 
        private final Object syncLock; 
        private long localSum;
        private int anomalies;

        public ManualDetector(int id, int[] array, int start, int end, AtomicLong globalAtomic, Object syncLock) {
            this.id = id;
            this.array = array;
            this.start = start;
            this.end = end;
            this.globalAtomic = globalAtomic;
            this.syncLock = syncLock;
        }

        @Override
        public void run() {
            localSum = 0;
            anomalies = 0;
            for (int i = start; i <= end; i++) {
                int v = array[i];
                localSum += v;
                if (v > ANOMALY_THRESHOLD) anomalies++;
            }
            System.out.printf("[Детектор-%d] Сектор %d–%d просканирован. Сумма = %d%n", id, start, end, localSum);

            // два показанных подхода для безопасного накопления
            if (globalAtomic != null) {
                globalAtomic.addAndGet(localSum);
            } else if (syncLock != null) {
                synchronized (syncLock) {
                    // caller should provide a mutable holder if using syncLock approach
                    // For simplicity in this demo we don't use a shared primitive here,
                    // but caller can read localSum values from threads if needed.
                }
            }
        }

        public long getLocalSum() { return localSum; }
        public int getAnomalies() { return anomalies; }
    }

    
    // Этап 2-3: ExecutorService + Callable + Future<Result>
    static class CallableDetector implements Callable<Result> {
        private final int id;
        private final int[] array;
        private final int start;
        private final int end;
        private final int phase;

        public CallableDetector(int id, int[] array, int start, int end, int phase) {
            this.id = id;
            this.array = array;
            this.start = start;
            this.end = end;
            this.phase = phase;
        }

        @Override
        public Result call() {
            long sum = 0;
            int anomalies = 0;
            for (int i = start; i <= end; i++) {
                int v = array[i];
                sum += v;
                if (v > ANOMALY_THRESHOLD) anomalies++;
            }
            // небольшой искусственный лаг для демонстрации динамики (можно убрать)
            // try { Thread.sleep((long)(Math.random() * 20)); } catch (InterruptedException ignored) {}

            return new Result(id, start, end, sum, anomalies, phase);
        }
    }

    // Вспомогательные: генерация массива
    static int[] generateArray(int size) {
        Random rnd = new Random(42); // фиксированный seed для воспроизводимости
        int[] a = new int[size];
        for (int i = 0; i < size; i++) {
            // генерируем числа в диапазоне 0..9999 (чтобы были значения >9000)
            a[i] = rnd.nextInt(10_000);
        }
        return a;
    }

    // Расчёт границ сегмента (включительно)
    static int[] segmentBounds(int segmentIndex, int totalSegments, int arraySize) {
        int base = arraySize / totalSegments;
        int remainder = arraySize % totalSegments;
        int start = segmentIndex * base + Math.min(segmentIndex, remainder);
        int len = base + (segmentIndex < remainder ? 1 : 0);
        int end = start + len - 1;
        return new int[]{start, end};
    }

    // Этап 1: Ручные потоки
    static void runManualThreads(int[] array, int nSegments) throws InterruptedException {
        System.out.println("=== Этап 1: Ручные потоки ===");
        Thread[] threads = new Thread[nSegments];
        ManualDetector[] detectors = new ManualDetector[nSegments];
        AtomicLong globalAtomic = new AtomicLong(0);
        Object syncLock = new Object(); // unused for atomic approach here

        long t0 = System.nanoTime();
        for (int i = 0; i < nSegments; i++) {
            int[] bounds = segmentBounds(i, nSegments, array.length);
            detectors[i] = new ManualDetector(i + 1, array, bounds[0], bounds[1], globalAtomic, syncLock);
            threads[i] = new Thread(detectors[i], "Detector-" + (i + 1));
            threads[i].start();
        }
        for (Thread th : threads) th.join();
        long t1 = System.nanoTime();

        // print individual sums already printed by detectors; now print global sum:
        System.out.printf("[Центральный ИИ] Общая сумма данных = %d%n", globalAtomic.get());
        System.out.printf("⏱ Время (ручные потоки): %d ms%n", (t1 - t0) / 1_000_000);
    }

    // Этап 2-3: ExecutorService + Future.get()
    static Result[] runExecutorService(int[] array, int nSegments) throws InterruptedException, ExecutionException {
        System.out.println("\n=== Этап 2-3: ExecutorService (Callable + Future.get()) ===");
        ExecutorService exec = Executors.newFixedThreadPool(Math.min(nSegments, Runtime.getRuntime().availableProcessors()));
        List<Future<Result>> futures = new ArrayList<>();
        long t0 = System.nanoTime();
        for (int i = 0; i < nSegments; i++) {
            int[] bounds = segmentBounds(i, nSegments, array.length);
            CallableDetector task = new CallableDetector(i + 1, array, bounds[0], bounds[1], 1);
            Future<Result> f = exec.submit(task);
            futures.add(f);
        }

        long totalSum = 0;
        int totalAnom = 0;
        Result[] results = new Result[nSegments];
        for (int i = 0; i < futures.size(); i++) {
            Result r = futures.get(i).get(); // блокирует по очереди
            results[i] = r;
            System.out.printf("[Детектор-%d] Сектор %d–%d завершён. Сумма = %d, Аномалий = %d%n",
                    r.detectorId, r.startIndex, r.endIndex, r.sum, r.anomalies);
            totalSum += r.sum;
            totalAnom += r.anomalies;
        }
        long t1 = System.nanoTime();
        exec.shutdown();

        System.out.printf("[Центральный ИИ] Общая сумма = %d%n", totalSum);
        System.out.printf("[Центральный ИИ] Найдено аномалий = %d%n", totalAnom);
        System.out.printf("⏱ Время (ExecutorService): %d ms%n", (t1 - t0) / 1_000_000);
        return results;
    }

    // Этап 5: CompletionService
    static void runCompletionService(int[] array, int nSegments) throws InterruptedException, ExecutionException {
        System.out.println("\n=== Этап 5: CompletionService ===");
        ExecutorService exec = Executors.newFixedThreadPool(Math.min(nSegments, Runtime.getRuntime().availableProcessors()));
        CompletionService<Result> completion = new ExecutorCompletionService<>(exec);
        for (int i = 0; i < nSegments; i++) {
            int[] bounds = segmentBounds(i, nSegments, array.length);
            completion.submit(new CallableDetector(i + 1, array, bounds[0], bounds[1], 1));
        }

        long t0 = System.nanoTime();
        long totalSum = 0;
        int totalAnom = 0;
        for (int i = 0; i < nSegments; i++) {
            Future<Result> f = completion.take(); // получает по мере готовности
            Result r = f.get();
            System.out.printf("[Центральный ИИ] Получен отчёт от Детектора-%d: сумма = %d, аномалий = %d%n",
                    r.detectorId, r.sum, r.anomalies);
            totalSum += r.sum;
            totalAnom += r.anomalies;
        }
        long t1 = System.nanoTime();
        exec.shutdown();

        System.out.printf("[Центральный ИИ] Итоговая сумма = %d%n", totalSum);
        System.out.printf("[Центральный ИИ] Общие аномалии = %d%n", totalAnom);
        System.out.printf("⏱ Время (CompletionService): %d ms%n", (t1 - t0) / 1_000_000);
    }

    
    // Этап 6: CyclicBarrier (фазовая обработка)
    static void runCyclicBarrierPhases(int[] array, int nSegments) throws InterruptedException, BrokenBarrierException {
        System.out.println("\n=== Этап 6: CyclicBarrier (фазовая обработка) ===");

        final int phaseParts = 2; // две фазы: первая половина, вторая половина
        CyclicBarrier barrier = new CyclicBarrier(nSegments, () -> {
            System.out.println("[ИИ-координатор] Все детекторы завершили фазу. Переход к следующей.");
        });

        ExecutorService exec = Executors.newFixedThreadPool(Math.min(nSegments, Runtime.getRuntime().availableProcessors()));
        CountDownLatch done = new CountDownLatch(nSegments);

        long t0 = System.nanoTime();
        for (int i = 0; i < nSegments; i++) {
            final int segIndex = i;
            exec.submit(() -> {
                try {
                    int[] bounds = segmentBounds(segIndex, nSegments, array.length);
                    int start = bounds[0], end = bounds[1];
                    int len = end - start + 1;
                    int half = len / 2;
                    int p1start = start;
                    int p1end = start + half - 1;
                    int p2start = start + half;
                    int p2end = end;

                    // Фаза 1
                    long sum1 = 0;
                    int anom1 = 0;
                    for (int k = p1start; k <= Math.max(p1start, p1end); k++) {
                        int v = array[k];
                        sum1 += v;
                        if (v > ANOMALY_THRESHOLD) anom1++;
                    }
                    System.out.printf("[Детектор-%d] Фаза 1 завершена. Сумма = %d%n", segIndex + 1, sum1);
                    barrier.await(); // ждём остальных

                    // Фаза 2
                    long sum2 = 0;
                    int anom2 = 0;
                    for (int k = p2start; k <= p2end; k++) {
                        int v = array[k];
                        sum2 += v;
                        if (v > ANOMALY_THRESHOLD) anom2++;
                    }
                    System.out.printf("[Детектор-%d] Фаза 2 завершена. Сумма = %d%n", segIndex + 1, sum2);
                    done.countDown();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println("[Детектор] Ошибка фазы: " + e.getMessage());
                }
            });
        }

        done.await();
        long t1 = System.nanoTime();
        exec.shutdown();
        System.out.printf("⏱ CyclicBarrier total time: %d ms%n", (t1 - t0) / 1_000_000);
    }

    
    //Сравнение производительности — запускает все три подхода и сравнивает времена
    static void comparePerformances(int[] array, int nSegments) throws Exception {
        // Ручные потоки
        long t0 = System.nanoTime();
        // We'll run manual threads but with reduced log noise: only accumulate global AtomicLong
        AtomicLong atomic = new AtomicLong(0);
        Thread[] threads = new Thread[nSegments];
        for (int i = 0; i < nSegments; i++) {
            int[] b = segmentBounds(i, nSegments, array.length);
            ManualDetector d = new ManualDetector(i + 1, array, b[0], b[1], atomic, null);
            threads[i] = new Thread(d);
            threads[i].start();
        }
        for (Thread th : threads) th.join();
        long t1 = System.nanoTime();
        long manualTime = (t1 - t0) / 1_000_000;

        // ExecutorService + Future.get()
        long t2 = System.nanoTime();
        Result[] res = runExecutorService(array, nSegments); // this prints its own time; we measure here too
        long t3 = System.nanoTime();
        long execTime = (t3 - t2) / 1_000_000;

        // CompletionService
        long t4 = System.nanoTime();
        runCompletionService(array, nSegments);
        long t5 = System.nanoTime();
        long compTime = (t5 - t4) / 1_000_000;

        System.out.println("\n[ИИ-Аналитик] Время выполнения:");
        System.out.printf("- Ручные потоки: %d ms%n", manualTime);
        System.out.printf("- ExecutorService: %d ms%n", execTime);
        System.out.printf("- CompletionService: %d ms%n", compTime);
    }

    
    // Main menu — запускает этапы
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Сеть кибер-детекторов — запуск задания ===");
        System.out.printf("Генерируем массив размера %d, делим на %d сегментов.%n", ARRAY_SIZE, N_SEGMENTS);
        int[] array = generateArray(ARRAY_SIZE);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1 — Этап 1+2: Ручные потоки + общий отчёт");
            System.out.println("2 — Этап 3+4: ExecutorService + Future.get() (Result с аномалиями)");
            System.out.println("3 — Этап 5: CompletionService (обработка по мере готовности)");
            System.out.println("4 — Этап 6: CyclicBarrier (координация фаз)");
            System.out.println("5 — Этап 7: Сравнение производительности (запуск 1,2,5)");
            System.out.println("6 — Запустить всё по порядку");
            System.out.println("0 — Выйти");
            System.out.print("> ");
            String choice = sc.nextLine().trim();
            try {
                if (choice.equals("0")) {
                    System.out.println("Выход.");
                    break;
                } else if (choice.equals("1")) {
                    runManualThreads(array, N_SEGMENTS);
                } else if (choice.equals("2")) {
                    runExecutorService(array, N_SEGMENTS);
                } else if (choice.equals("3")) {
                    runCompletionService(array, N_SEGMENTS);
                } else if (choice.equals("4")) {
                    runCyclicBarrierPhases(array, N_SEGMENTS);
                } else if (choice.equals("5")) {
                    comparePerformances(array, N_SEGMENTS);
                } else if (choice.equals("6")) {
                    System.out.println("Запуск всех этапов по порядку...");
                    runManualThreads(array, N_SEGMENTS);
                    runExecutorService(array, N_SEGMENTS);
                    runCompletionService(array, N_SEGMENTS);
                    runCyclicBarrierPhases(array, N_SEGMENTS);
                    comparePerformances(array, N_SEGMENTS);
                } else {
                    System.out.println("Неверный выбор.");
                }
            } catch (Exception ex) {
                System.err.println("Ошибка выполнения: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        sc.close();
    }
}
