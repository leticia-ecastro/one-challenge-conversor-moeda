import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Historico {

    private String moedaDe;
    private String moedaPara;
    private double valorDe;
    private double valorPara;
    private LocalDate data;

    public Historico(String moedaDe, String moedaPara, double valorDe, double valorPara) {
        this.moedaDe = moedaDe;
        this.moedaPara = moedaPara;
        this.valorDe = valorDe;
        this.valorPara = valorPara;
        this.data = LocalDate.now();
    }

    public String getMoedaDe() {
        return moedaDe;
    }

    public void setMoedaDe(String moedaDe) {
        this.moedaDe = moedaDe;
    }

    public String getMoedaPara() {
        return moedaPara;
    }

    public void setMoedaPara(String moedaPara) {
        this.moedaPara = moedaPara;
    }

    public double getValorDe() {
        return valorDe;
    }

    public void setValorDe(double valorDe) {
        this.valorDe = valorDe;
    }

    public double getValorPara() {
        return valorPara;
    }

    public void setValorPara(double valorPara) {
        this.valorPara = valorPara;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return String.format("Data: " + this.data.format(formatador) + " | %.2f[%s] correspondem a %.2f[%s].", valorDe, moedaDe, valorPara, moedaPara);
    }
}
