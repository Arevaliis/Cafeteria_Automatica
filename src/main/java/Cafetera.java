public class Cafetera {
    private final int TOTAL_CAFES_DIA = 20;
    private final int CAPACIDAD_MAXIMA_DEPOSITO = 5;
    private int contador_total = 0;
    private int cafes_disponibles = 0;
    private int consumo_profesores = 0;

    public synchronized boolean depositarCafe() {

        if (contador_total == TOTAL_CAFES_DIA) {
            System.out.println("Se acabo el cafe por hoy !!");
            return false;
        }

        while (cafes_disponibles == CAPACIDAD_MAXIMA_DEPOSITO ){

            System.out.println("Deposito de cafes lleno");

            try {
                 wait();

            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        cafes_disponibles++;
        contador_total++;
        System.out.println("Cafetera preparada y deposita Cafe-" + contador_total + ". En depósito: " + cafes_disponibles);
        notifyAll();

        return true;
    }

    public synchronized boolean cogerCafe(){

        if (contador_total == TOTAL_CAFES_DIA && cafes_disponibles == 0) { return false;}

        while(cafes_disponibles == 0){

            System.out.println("Profesor " + Thread.currentThread().getName()  + " esperando cafe");
            try{
                 wait();

            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        cafes_disponibles--;
        System.out.println("Profesor " + Thread.currentThread().getName() + " retira cafe-" + ++consumo_profesores + ". En depósito: " + cafes_disponibles);
        notifyAll();
        return true;
    }
}