package tarefa;

public class Download implements Runnable {

    private String nome;
    private int totalEtapas;

    public Download(String nome, int totalEtapas) {
        this.nome = nome;
        this.totalEtapas = totalEtapas;
    }

    @Override
    public void run() {

        for (int i = 1; i <= totalEtapas; i++) {

            System.out.println(
                "Download " + nome +
                " baixando parte " + i +
                " de " + totalEtapas + "..."
            );

            long tempoInicial = System.currentTimeMillis();
            boolean interrompidaNoMeio = false;

            // Simula 1 segundo de download
            while (System.currentTimeMillis() - tempoInicial < 1000) {

                double calculoInutil =
                    Math.sin(Math.random()) *
                    Math.cos(Math.random());

                // Verifica se a thread recebeu um interrupt()
                if (Thread.currentThread().isInterrupted()) {
                    interrompidaNoMeio = true;
                    break;
                }
            }

            if (interrompidaNoMeio) {

                System.out.println(
                    "Download " + nome +
                    ":\n-> CRÍTICO: o download foi cancelado!"
                );

                return;
            }
        }

        System.out.println(
            "Download " + nome +
            ":\n-> SUCESSO: download concluído com sucesso!"
        );
    }
}