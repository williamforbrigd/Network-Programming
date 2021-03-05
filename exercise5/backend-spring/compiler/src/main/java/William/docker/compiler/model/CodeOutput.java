package William.docker.compiler.model;

public class CodeOutput {
    StringBuilder code;

    public CodeOutput(StringBuilder code) {
        this.code = code;
    }

    public StringBuilder getCode() {
        return this.code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

}
