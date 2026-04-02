public class Profesor implements Runnable {
    private final Cafetera cafetera;

    public Profesor(Cafetera cafetera) { this.cafetera = cafetera; }

    @Override
    public void run() {
       boolean quedaCafe = true;

        while (quedaCafe){

           try{
               quedaCafe = cafetera.cogerCafe();
               Thread.sleep((int) (Math.random() * 3000) + 100);

           } catch (InterruptedException e) { throw new RuntimeException(e); }
       }
    }
}