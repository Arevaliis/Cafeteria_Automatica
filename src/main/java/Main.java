public class Main {

    public static void main(String[] args) {
        Cafetera cafetera = new Cafetera();

        Thread profesor1 =new Thread( new Profesor( cafetera), "Ana");
        Thread profesor2 =new Thread( new Profesor( cafetera), "Luis");
        Thread profesor3 =new Thread( new Profesor( cafetera), "Marta");
        Thread deposito =new Thread( new DepositoCafes( cafetera));

        profesor1.start();
        profesor2.start();
        profesor3.start();
        deposito.start();

        try {
            profesor1.join();
            profesor2.join();
            profesor3.join();
            deposito.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Todos los profesores están a tope de cafeína!!");

    }
}