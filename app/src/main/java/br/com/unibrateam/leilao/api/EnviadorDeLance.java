package br.com.unibrateam.leilao.api;

import br.com.unibrateam.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.unibrateam.leilao.api.retrofit.client.RespostaListener;
import br.com.unibrateam.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.unibrateam.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.unibrateam.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.unibrateam.leilao.model.Lance;
import br.com.unibrateam.leilao.model.Leilao;
import br.com.unibrateam.leilao.ui.dialog.AvisoDialogManager;

public class EnviadorDeLance {

    private final LeilaoWebClient client;
    private final LanceProcessadoListener listener;
    private final AvisoDialogManager manager;

    public EnviadorDeLance(LeilaoWebClient client,
                           LanceProcessadoListener listener,
                           AvisoDialogManager manager) {
        this.client = client;
        this.listener = listener;
        this.manager = manager;
    }

    public void envia(final Leilao leilao, Lance lance) {
        try {
            leilao.propoe(lance);
            client.propoe(lance, leilao.getId(), new RespostaListener<Void>() {
                @Override
                public void sucesso(Void resposta) {
                    listener.processado(leilao);
                }

                @Override
                public void falha(String mensagem) {
                    manager.mostraToastFalhaNoEnvio();
                }
            });
        } catch (LanceMenorQueUltimoLanceException exception) {
            manager.mostraAvisoLanceMenorQueUltimoLance();
        } catch (LanceSeguidoDoMesmoUsuarioException exception) {
            manager.mostraAvisoLanceSeguidoDoMesmoUsuario();
        } catch (UsuarioJaDeuCincoLancesException exception) {
            manager.mostraAvisoUsuarioJaDeuCincoLances();
        }
    }

    public interface LanceProcessadoListener {
        void processado(Leilao leilao);
    }

}
