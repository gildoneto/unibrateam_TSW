package br.com.unibrateam.leilao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import br.com.unibrateam.leilao.R;
import br.com.unibrateam.leilao.api.EnviadorDeLance;
import br.com.unibrateam.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.unibrateam.leilao.database.dao.UsuarioDAO;
import br.com.unibrateam.leilao.formatter.FormatadorDeMoeda;
import br.com.unibrateam.leilao.model.Lance;
import br.com.unibrateam.leilao.model.Leilao;
import br.com.unibrateam.leilao.ui.dialog.AvisoDialogManager;
import br.com.unibrateam.leilao.ui.dialog.NovoLanceDialog;

public class LancesLeilaoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lances do leilão";

    private TextView maioresLances;
    private TextView menorLance;
    private TextView maiorLance;
    private TextView descricao;
    private FormatadorDeMoeda formatador;
    private UsuarioDAO dao;
    private Leilao leilaoRecebido;
    private final LeilaoWebClient client = new LeilaoWebClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        getSupportActionBar().setTitle(TITULO_APPBAR);
        carregaLeilaoRecebido();
    }

    private void carregaLeilaoRecebido() {
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(LeilaoConstantes.CHAVE_LEILAO)) {
            leilaoRecebido = (Leilao) dadosRecebidos.getSerializableExtra(LeilaoConstantes.CHAVE_LEILAO);
            inicializaAtributos();
            inicializaViews();
            apresentaDados();
            configuraFab();
        } else {
            finish();
        }
    }

    private void inicializaViews() {
        descricao = findViewById(R.id.lances_leilao_descricao);
        maiorLance = findViewById(R.id.lances_leilao_maior_lance);
        menorLance = findViewById(R.id.lances_leilao_menor_lance);
        maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
    }

    private void inicializaAtributos() {
        formatador = new FormatadorDeMoeda();
        dao = new UsuarioDAO(this);
    }

    private void configuraFab() {
        FloatingActionButton fabAdiciona = findViewById(R.id.lances_leilao_fab_adiciona);
        fabAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraDialogNovoLance();
            }
        });
    }

    private void mostraDialogNovoLance() {
        NovoLanceDialog dialog = new NovoLanceDialog(
                this,
                new NovoLanceDialog.LanceCriadoListener() {
                    @Override
                    public void lanceCriado(Lance lance) {
                        enviaLance(lance);
                    }
                },
                dao);
        dialog.mostra();
    }

    private void enviaLance(Lance lance) {
        EnviadorDeLance enviador = new EnviadorDeLance(
                client,
                lanceProcessadoListener(),
                new AvisoDialogManager(this));
        enviador.envia(leilaoRecebido, lance);
    }

    @NonNull
    private EnviadorDeLance.LanceProcessadoListener lanceProcessadoListener() {
        return new EnviadorDeLance.LanceProcessadoListener() {
            @Override
            public void processado(Leilao leilao) {
                leilaoRecebido = leilao;
                apresentaDados();
            }
        };
    }

    private void apresentaDados() {
        adicionaDescricao(leilaoRecebido);
        adicionaMaiorLance(leilaoRecebido);
        adicionaMenorLance(leilaoRecebido);
        adicionaMaioresLances(leilaoRecebido);
    }

    private void adicionaMaioresLances(Leilao leilao) {
        StringBuilder sb = new StringBuilder();
        for (Lance lance :
                leilao.tresMaioresLances()) {
            sb.append(lance.getValor())
                    .append(" - ")
                    .append(lance.getUsuario())
                    .append("\n");
        }
        String maioresLancesEmTexto = sb.toString();
        maioresLances.setText(maioresLancesEmTexto);
    }

    private void adicionaMenorLance(Leilao leilao) {
        menorLance.setText(formatador.formata(leilao.getMenorLance()));
    }

    private void adicionaMaiorLance(Leilao leilao) {
        maiorLance.setText(formatador.formata(leilao.getMaiorLance()));
    }

    private void adicionaDescricao(Leilao leilao) {
        descricao.setText(leilao.getDescricao());
    }


}
