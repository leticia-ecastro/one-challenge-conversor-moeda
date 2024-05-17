import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);

        int opcaoMenu = 0;
        List<Historico> historico = new ArrayList<>();

        do {

            try {
                System.out.println("\nSelecione uma opção de câmbio:");
                System.out.println("[1] Dólar ---> Real Brasileiro;\n" +
                        "[2] Real Brasileiro ---> Dólar;\n" +
                        "[3] Dólar ---> Euro;\n" +
                        "[4] Euro ---> Dólar;\n" +
                        "[5] Dólar ---> Iene;\n" +
                        "[6] Iene ---> Dólar;\n" +
                        "[7] Sair;\n" +
                        "[8] Histórico de Conversões.\n");
                System.out.print("Opção selecionada: ");
                opcaoMenu = leitura.nextInt();

                String moedaDePara = obterMoedaDePara(opcaoMenu);
                if (moedaDePara == null) {
                    System.out.println("Programa finalizado.\n");
                    System.exit(0);
                }

                if (moedaDePara.equals("Histórico") && !historico.isEmpty()) {
                    for (Historico item : historico) {
                        System.out.println(item.toString());
                    }
                    continue;
                } else if (moedaDePara.equals("Histórico")) {
                    System.out.println("Não há conversões para serem exibidas.\n");
                    continue;
                }

                System.out.print("Digite o valor a ser convertido: ");
                double valor = leitura.nextDouble();

                String urlBase = "https://v6.exchangerate-api.com/v6/db48b835b70c59c5d07ae35f/pair/";
                String url = urlBase + moedaDePara + valor;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                Gson gson = new Gson();
                Cambio cambio = gson.fromJson(json, Cambio.class);

                System.out.printf("%.2f[%s] correspondem a %.2f[%s].\n", valor, cambio.getBase_code(), cambio.getConversion_result(), cambio.getTarget_code());

                Historico itemHistorico = new Historico(cambio.getBase_code(), cambio.getTarget_code(), valor, cambio.getConversion_result());
                historico.add(itemHistorico);
            } catch (IllegalStateException e2) {
                System.out.println("Selecione uma das opções apresentadas no menu!");
            } catch (IOException | InterruptedException e) {
                System.out.println("Erro na requisição " + e.getMessage());
            }
        } while (opcaoMenu >= 1 && opcaoMenu <= 8);

        leitura.close();
    }

    public static String obterMoedaDePara(int opcaoMenu) {
        return switch (opcaoMenu) {
            case 1 -> "USD/BRL/";
            case 2 -> "BRL/USD/";
            case 3 -> "USD/EUR/";
            case 4 -> "EUR/USD/";
            case 5 -> "USD/JPY/";
            case 6 -> "JPY/USD/";
            case 7 -> null;
            case 8 -> "Histórico";
            default -> throw new IllegalStateException();
        };
    }
}
