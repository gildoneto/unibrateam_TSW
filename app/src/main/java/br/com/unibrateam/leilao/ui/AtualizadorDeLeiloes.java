package br.com.unibrateam.leilao.ui;

import java.util.List;

import br.com.unibrateam.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.unibrateam.leilao.api.retrofit.client.RespostaListener;
import br.com.unibrateam.leilao.model.Leilao;
import br.com.unibrateam.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

public class AtualizadorDeLeiloes {

    public void buscaLeiloes(final ListaLeilaoAdapter adapter,
                             LeilaoWebClient client,
                             final ErroCarregaLeiloesListener erroListener) {
        client.todos(new RespostaListener<List<Leilao>>() {
            @Override
            public void sucesso(List<Leilao> leiloes) {
                adapter.atualiza(leiloes);
            }

            @Override
            public void falha(String mensagem) {
                erroListener.erroAoCarregar(mensagem);
            }
        });
    }

    public interface ErroCarregaLeiloesListener {
        void erroAoCarregar(String mensagem);
    }
}
