package database.model;

public class TBReplicacaoDirecao {
    private long id;
    private String direcaoOrigem;
    private String direcaoDestino;
    private String usuarioOrigem;
    private String usuarioDestino;
    private String senhaOrigem;
    private String senhaDestino;
    private boolean habilitado;
    private long processoId;

    public TBReplicacaoDirecao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDirecaoOrigem() {
        return direcaoOrigem;
    }

    public void setDirecaoOrigem(String direcaoOrigem) {
        this.direcaoOrigem = direcaoOrigem;
    }

    public String getDirecaoDestino() {
        return direcaoDestino;
    }

    public void setDirecaoDestino(String direcaoDestino) {
        this.direcaoDestino = direcaoDestino;
    }

    public String getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(String usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public String getSenhaOrigem() {
        return senhaOrigem;
    }

    public void setSenhaOrigem(String senhaOrigem) {
        this.senhaOrigem = senhaOrigem;
    }

    public String getSenhaDestino() {
        return senhaDestino;
    }

    public void setSenhaDestino(String senhaDestino) {
        this.senhaDestino = senhaDestino;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(long processoId) {
        this.processoId = processoId;
    }
}
