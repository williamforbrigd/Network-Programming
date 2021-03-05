package William.docker.compiler.model;

public class Code {
    String code;

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "TextDTO{" +
                "code='" + code + '\'' +
                '}';
    }
}
