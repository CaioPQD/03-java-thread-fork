package tarefa;

public class Tarefa implements Runnable {
    private String nome;

    public Tarefa(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(nome + " processando passo " + i + ".");

            // --- SIMULAÇÃO DE PROCESSAMENTO PESADO SEM SLEEP ---
            // Em vez de dormir, a thread fica fazendo contas matemáticas repetitivas.
            // A cada iteração interna, nós monitoramos ativamente a flag de interrupção.
            long tempoInicial = System.currentTimeMillis();
            boolean interrompidaNoMeio = false;

            // Rodar por exatamente 1 segundo:
            while (System.currentTimeMillis() - tempoInicial < 1000) { 
                // Realiza cálculos quaisquer para ocupar a CPU:
                double calculoInutil = Math.sin(Math.random()) * Math.cos(Math.random());

                // AQUI ESTÁ A VERIFICAÇÃO ATIVA DA FLAG (sem exceções, apenas leitura 
                // booleana):
                if (Thread.currentThread().isInterrupted()) {
                    interrompidaNoMeio = true;
                    break; // Sai do loop de 1 segundo imediatamente.
                }
            }

            // Se a flag foi detectada como true, interrompe toda a tarefa:
            if (interrompidaNoMeio) {
                System.out.println(nome + ":\n-> CRÍTICO: a tarefa foi cancelada!");
                return; // Encerra o método run().
            }
        }

        // Se o loop principal terminou todas as 5 etapas sem interrupções:
        System.out.println(nome + ":\n-> SUCESSO: a tarefa foi concluída com sucesso!");
    }
} //alteração//
