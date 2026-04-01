public class Profesor implements Runnable {
    private final Cafetera cafetera;

    public Profesor(Cafetera cafetera) { this.cafetera = cafetera; }

    @Override
    public void run() {
       boolean quedaCafe = true;

        while (quedaCafe){

           try{
               Thread.sleep((int) (Math.random() * 7000) + 100);
               quedaCafe = cafetera.cogerCafe();

           } catch (InterruptedException e) { throw new RuntimeException(e); }
       }
    }
}