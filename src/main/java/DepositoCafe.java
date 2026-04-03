public class DepositoCafe implements Runnable {
    private final Cafetera cafetera;

    public DepositoCafe(Cafetera cafetera) { this.cafetera = cafetera; }

    @Override
    public void run() {
        boolean hacerCafe = true;

        while (hacerCafe) {
            try {
                hacerCafe = cafetera.depositarCafe();
                Thread.sleep((int) (Math.random() * 1000) + 100);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}