package br.com.history.domain.enumeration;

/**
 * The StatusEnum enumeration.
 */
public enum StatusEnum {
    EM_ANALISE("Em análise"),
    ACEITO("Aceito"),
    NEGADO("Negado");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
