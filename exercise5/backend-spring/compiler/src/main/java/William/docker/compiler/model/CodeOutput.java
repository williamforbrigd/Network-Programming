package William.docker.compiler.model;

public class CodeOutput {
    String code;

    public CodeOutput(String code) {
        this.code = code;
    }

    public CodeOutput() {}

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return this.code;
    }
}
