package William.docker.compiler.model;

public class CppProgram {
    String code;

    public CppProgram(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
