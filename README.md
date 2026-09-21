# 03-java-thread

## Compilação:
```bash
javac *.java
```

## Execução:
```bash
java Main
```

# Exercícios:

Em Java, implemente a aplicação abaixo.
Em Markdown, explique sua solução.

## 1) Gerenciador de Downloads (DownloadManager)

Você foi contratado para criar um sistema básico de downloads. O sistema deve disparar três downloads paralelos. Cada download possui tamanhos e tempos de execução diferentes e o usuário deve ser capaz de cancelar apenas os downloads que estourarem o tempo limite.

## Requisitos de Implementação:

### Modifique a classe Tarefa:

Renomeie a classe para Download.

Adicione um novo atributo privado no construtor chamado `int totalEtapas` (em vez de fixar o loop em 5, cada download terá seu próprio número de etapas/tamanho).

Atualize as mensagens na tela para fazerem sentido com um download (ex: "Download [Nome] baixando parte X de Y...").

### Crie a classe Principal (Main):

Instancie e dispare três threads de download simultaneamente com as seguintes configurações:

Download 1: Nome "Arquivo_Pequeno", com 2 etapas (duração total: 2 segundos).

Download 2: Nome "Arquivo_Medio", com 4 etapas (duração total: 4 segundos).

Download 3: Nome "Arquivo_Grande", com 8 etapas (duração total: 8 segundos).

Faça a thread main aguardar por 5000 milissegundos (5 segundos) usando Thread.sleep(5000).Após esse tempo de espera, a main deve verificar cada uma das três threads. Se a thread ainda estiver viva (.isAlive()), envie o sinal de interrupção (.interrupt()) individualmente nela.

## Resultado Esperado no Console:

Ao rodar o programa com a tolerância de 5 segundos, você deverá observar que:

1. O Arquivo_Pequeno e o Arquivo_Medio devem terminar com SUCESSO (pois precisam de 2s e 4s, respectivamente).

2. O Arquivo_Grande deve ser interrompido no meio do caminho pela main (por volta da etapa 5 ou 6), disparando o alerta CRÍTICO.




# Solução

## 1. Classe `Download`

A classe `Tarefa` foi renomeada para `Download`.

Como ela é `public`, o arquivo também precisa ser renomeado:

```text
Tarefa.java → Download.java
```

A classe continua implementando `Runnable`:

```java
public class Download implements Runnable
```

## 2. Número de etapas

Foi criado o atributo:

```java
private int totalEtapas;
```

O valor é recebido pelo construtor:

```java
public Download(String nome, int totalEtapas) {
    this.nome = nome;
    this.totalEtapas = totalEtapas;
}
```

O `for` usa esse valor:

```java
for (int i = 1; i <= totalEtapas; i++)
```

Assim, cada download pode ter uma quantidade diferente de etapas.

## 3. Simulação do download

Cada etapa dura aproximadamente 1 segundo usando um `while` com processamento:

```java
long tempoInicial = System.currentTimeMillis();

while (System.currentTimeMillis() - tempoInicial < 1000) {
    double calculoInutil =
        Math.sin(Math.random()) *
        Math.cos(Math.random());
}
```

Portanto:

```text
2 etapas ≈ 2 segundos
4 etapas ≈ 4 segundos
8 etapas ≈ 8 segundos
```

## 4. Interrupção

A `Main` pode enviar um sinal de interrupção:

```java
thread.interrupt();
```

O `interrupt()` não mata a Thread diretamente.

A própria Thread verifica o sinal:

```java
if (Thread.currentThread().isInterrupted()) {
    interrompidaNoMeio = true;
    break;
}
```

Depois encerra o `run()`:

```java
if (interrompidaNoMeio) {
    System.out.println("-> CRÍTICO: o download foi cancelado!");
    return;
}
```

### Por que não usamos `InterruptedException`?

Na atividade anterior havia:

```java
Thread.sleep(1000);
```

Nesse caso, `interrupt()` podia causar `InterruptedException`.

Nesta atividade estamos usando processamento dentro de um `while`, então verificamos manualmente:

```java
Thread.currentThread().isInterrupted()
```

## 5. Criação das Threads

Na `Main`:

```java
Download download1 = new Download("Arquivo_Pequeno", 2);
Download download2 = new Download("Arquivo_Medio", 4);
Download download3 = new Download("Arquivo_Grande", 8);
```

Depois:

```java
Thread thread1 = new Thread(download1);
Thread thread2 = new Thread(download2);
Thread thread3 = new Thread(download3);
```

E iniciamos:

```java
thread1.start();
thread2.start();
thread3.start();
```

Isso permite que os três downloads sejam executados concorrentemente.

## 6. Cancelamento após 5 segundos

A `Main` espera:

```java
Thread.sleep(5000);
```

Depois verifica cada Thread:

```java
if (thread1.isAlive()) {
    thread1.interrupt();
}

if (thread2.isAlive()) {
    thread2.interrupt();
}

if (thread3.isAlive()) {
    thread3.interrupt();
}
```

`isAlive()`:

```text
true  → Thread ainda está executando
false → Thread já terminou
```

## 7. Resultado esperado

Depois de aproximadamente 5 segundos:

```text
Arquivo_Pequeno → terminou
Arquivo_Medio   → terminou
Arquivo_Grande  → ainda está executando
```

Resultado:

```text
Arquivo_Pequeno → SUCESSO
Arquivo_Medio   → SUCESSO
Arquivo_Grande  → CRÍTICO / CANCELADO
```

A etapa exata em que o download grande é interrompido pode variar devido à execução concorrente.

## 8. Principais conceitos

- `Runnable` → define o trabalho da Thread.
- `run()` → contém o código executado.
- `Thread` → representa a execução concorrente.
- `start()` → inicia a Thread.
- `interrupt()` → envia um sinal de interrupção.
- `isInterrupted()` → verifica o sinal de interrupção.
- `isAlive()` → verifica se a Thread ainda está executando.
- `return` → encerra o `run()`.

## Ideia principal

A `Main` controla as Threads:

```java
thread.start();
thread.isAlive();
thread.interrupt();
```

O `Download` executa o trabalho e verifica se recebeu o sinal:

```java
run();
isInterrupted();
return;
```

**`interrupt()` envia o sinal; a própria Thread detecta esse sinal e coopera com o cancelamento.**
````
