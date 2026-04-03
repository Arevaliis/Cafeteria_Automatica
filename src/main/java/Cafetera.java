/**
 * Clase encargada de gestionar la producción de cafe y su consumo. Actúa como el monitor.
 */
public class Cafetera {
    private final int TOTAL_CAFES_DIA = 20;
    private final int CAPACIDAD_MAXIMA_DEPOSITO = 5;
    private int cafesDisponibles = 0;

    // Usamos dos variables para contar los cafes, ya que la producción de cafe no tiene por qué ir a la par del consumo de cafés
    private int contadorTotalCafes = 0;
    private int consumoProfesores = 0;

    /**
     * Este metodo permite hacer cafes siempre y cuando no se sobrepase la capacidad máxima del depósito de cafes. En caso de haber
     * 5 cafes en el depósito, parará la producción de cafes hasta que otro lo despierte y vuelva a reanudarse la producción de cafés.
     *
     * Uso de synchronized: Produce exclusion mutua entre hilos, es decir, solo un hilo podrá modificar los recursos
     * compartidos de cafe_disponible y contador_total_cafes, evitando así, acceso concurrente a estos recursos.
     *
     * @return true si a llegado a los cafes máximos por dia, o false si no.
     */
    public synchronized boolean depositarCafe() {

        while (cafesDisponibles == CAPACIDAD_MAXIMA_DEPOSITO ){ // Usamos while en vez de un if, para que al despertarse el hilo vuelva a revisar la condición

            try {
                 wait(); // PONE EN ESPERA EL HILO QUE PRODUCE CAFE AL ESTAR LLENO EL DEPÓSITO. SI NO SE HICIERA USO
                         // DE ESTE METODO LA CAFETERA NO PARARÍA DE HACER CAFES, Y EL DEPOSITO DE CAFES 'DESBORDARÍA'
                         // AL TENER UNA CAPACIDAD MAX DE 5 CAFES.
            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        cafesDisponibles++; // RECURSO COMPARTIDO
        System.out.println("Cafetera preparada y deposita Cafe-" + ++contadorTotalCafes + ". En depósito: " + cafesDisponibles);

        if (cafesDisponibles == CAPACIDAD_MAXIMA_DEPOSITO){ System.out.println("Deposito de cafes lleno"); }

        if (contadorTotalCafes == TOTAL_CAFES_DIA) {  // Usamos if para comprobar la condición de finalización
            System.out.println("Se acabo la producción de cafe por hoy !!");
            notifyAll(); // DESPIERTA A TODOS HILOS 'DORMIDOS', AVISANDO DE QUE NO SE VA HACER MAS CAFE
            return false;
        }

        notifyAll(); // DESPIERTA A TODOS HILOS 'DORMIDOS', MUY IMPORTANTE PARA DESPERTAR A LOS HILOS PROFESORES, AVISÁNDOLES DE QUE YA HAY CAFE HECHO
        return true;
    }


    /**
     * Este metodo permite a los profesores coger un cafe siempre y cuando haya cafes disponibles en el depósito, si no hubiera cafes
     * disponibles quedarán a la espera de que otro hilo los despierte y vuelvan a intentar ir a por cafe.
     *
     * Uso de synchronized: Al igual que el metodo anterior, produce exclusion mutua entre los hilos, dando lugar a que los profesores
     * vayan de uno en uno a por cafe, sin producirse ninguna race condition, ni tampoco ningún acceso concurrente a los recursos compartidos.
     *
     * @return true si hay cafes disponibles, false si no hay cafes disponibles y además, se ha llegado a la producción máxima de cafe por día.
     */
    public synchronized boolean cogerCafe(){

        while(cafesDisponibles == 0){ // Usamos while para que al despertarse el hilo vuelva a revisar la condición

            if (consumoProfesores == TOTAL_CAFES_DIA) { // Usamos if para comprobar la condición de finalización
                System.out.println("Profesor " + Thread.currentThread().getName() + " ya no quiere mas café");
                return false;
            }

            System.out.println("Profesor " + Thread.currentThread().getName()  + " esperando cafe");
            try{
                 wait(); // PONE EN ESPERA A LOS HILOS DE LOS PROFESORES AL NO HABER CAFES DISPONIBLES EN EL DEPOSITO
                         // SI NO SE HICIERA USO DEL WAIT, LOS PROFESORES ESTARÍAN CONSTANTEMENTE YENDO A POR CAFE
                         // NO ESPERARÍAN EL TIEMPO NECESARIO PARA QUE SE HAGA EL CAFE.

            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }

        cafesDisponibles--;
        System.out.println("Profesor " + Thread.currentThread().getName() + " retira cafe-" + ++consumoProfesores + ". En depósito: " + cafesDisponibles);
        notifyAll(); // DESPIERTA A LOS HILOS 'DORMIDOS', MUY IMPORTANTE PARA DESPERTAR AL HILO DEPOSITO Y QUE PRODUZCA MÁS CAFE
        return true;
    }
}