import tarefa.Download;

public class Main {

    public static void main(String[] args) {

        Download download1 =
            new Download("Arquivo_Pequeno", 2);

        Download download2 =
            new Download("Arquivo_Medio", 4);

        Download download3 =
            new Download("Arquivo_Grande", 8);

        Thread thread1 = new Thread(download1);
        Thread thread2 = new Thread(download2);
        Thread thread3 = new Thread(download3);

        // Inicia os três downloads
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // A main aguarda 5 segundos
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica individualmente cada download
        if (thread1.isAlive()) {
            System.out.println(
                "[Main] Cancelando Arquivo_Pequeno..."
            );
            thread1.interrupt();
        }

        if (thread2.isAlive()) {
            System.out.println(
                "[Main] Cancelando Arquivo_Medio..."
            );
            thread2.interrupt();
        }

        if (thread3.isAlive()) {
            System.out.println(
                "[Main] Cancelando Arquivo_Grande..."
            );
            thread3.interrupt();
        }
    }
}