package totabraz.com.socorromovel.domain;

/**
 * Created by totabraz on 13/04/18.
 */

public class AnonymousReport {
    private String diaDaOcorrencia;
    private String horaDaOcorrencia;
    private String enderecoDoFato;
    private String estado;
    private String numero;
    private String cep;
    private String complemento;
    private String pontoDeReferencia;
    private String bairro;
    private String municipio;

    public AnonymousReport() {
    }

    public String getDiaDaOcorrencia() {
        return diaDaOcorrencia;
    }

    public void setDiaDaOcorrencia(String diaDaOcorrencia) {
        this.diaDaOcorrencia = diaDaOcorrencia;
    }

    public String getHoraDaOcorrencia() {
        return horaDaOcorrencia;
    }

    public void setHoraDaOcorrencia(String horaDaOcorrencia) {
        this.horaDaOcorrencia = horaDaOcorrencia;
    }

    public String getEnderecoDoFato() {
        return enderecoDoFato;
    }

    public void setEnderecoDoFato(String enderecoDoFato) {
        this.enderecoDoFato = enderecoDoFato;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoDeReferencia() {
        return pontoDeReferencia;
    }

    public void setPontoDeReferencia(String pontoDeReferencia) {
        this.pontoDeReferencia = pontoDeReferencia;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
