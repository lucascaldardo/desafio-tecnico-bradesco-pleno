import database.model.TBReplicacaoProcesso;

public class Main {
    public static void main(String[] args) {
        System.out.println("oi");

        TBReplicacaoProcesso tbReplicacaoProcesso = new TBReplicacaoProcesso();
        tbReplicacaoProcesso.setId(1L);
        tbReplicacaoProcesso.setProcesso("completo");
        tbReplicacaoProcesso.setDescricao("Processa todas as tabelas");
        tbReplicacaoProcesso.setHabilitado(true);
        System.out.println(tbReplicacaoProcesso);


    }
}