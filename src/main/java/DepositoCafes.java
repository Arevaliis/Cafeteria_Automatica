public class DepositoCafes implements Runnable {
    private final Cafetera cafetera;

    public DepositoCafes(Cafetera cafetera) { this.cafetera = cafetera; }

    @Override
    public void run() {
        boolean quedaCafe = true;

        while (quedaCafe) {
            try {
                quedaCafe = cafetera.depositarCafe();
                Thread.sleep((int) (Math.random() * 1000) + 100);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}