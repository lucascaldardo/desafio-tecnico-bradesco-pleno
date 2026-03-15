package database.model;

public class TBReplicaDirecao {
    private Long id;
    private String direcaoOrigem;
    private String direcaoDestino;
    private String usuarioOrigem;
    private String usuarioDestino;
    private String senhaOrigem;
    private String senhaDestino;
    private boolean habilitado;
    private Long processoId;

    public TBReplicaDirecao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }
}
