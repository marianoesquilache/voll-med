package med.voll.api.domain;

public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
