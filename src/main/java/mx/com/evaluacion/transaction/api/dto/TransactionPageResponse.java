package mx.com.evaluacion.transaction.api.dto;

import java.util.List;

/**
 * Respuesta paginada de transacciones.
 */
public class TransactionPageResponse {

    private List<TransactionResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    /**
     * Constructor por defecto.
     */
    public TransactionPageResponse() {
    }

    /**
     * Obtiene las transacciones de la página actual.
     *
     * @return lista de transacciones
     */
    public List<TransactionResponse> getContent() {
        return content;
    }

    /**
     * Establece las transacciones de la página actual.
     *
     * @param content lista de transacciones
     */
    public void setContent(List<TransactionResponse> content) {
        this.content = content;
    }

    /**
     * Obtiene el número de página actual.
     *
     * @return número de página
     */
    public int getPage() {
        return page;
    }

    /**
     * Establece el número de página actual.
     *
     * @param page número de página
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Obtiene el tamaño de la página.
     *
     * @return tamaño de página
     */
    public int getSize() {
        return size;
    }

    /**
     * Establece el tamaño de la página.
     *
     * @param size tamaño de página
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Obtiene el total de elementos.
     *
     * @return total de elementos
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Establece el total de elementos.
     *
     * @param totalElements total de elementos
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Obtiene el total de páginas.
     *
     * @return total de páginas
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Establece el total de páginas.
     *
     * @param totalPages total de páginas
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Indica si es la primera página.
     *
     * @return true si es la primera página
     */
    public boolean isFirst() {
        return first;
    }

    /**
     * Establece si es la primera página.
     *
     * @param first indicador de primera página
     */
    public void setFirst(boolean first) {
        this.first = first;
    }

    /**
     * Indica si es la última página.
     *
     * @return true si es la última página
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Establece si es la última página.
     *
     * @param last indicador de última página
     */
    public void setLast(boolean last) {
        this.last = last;
    }
}
