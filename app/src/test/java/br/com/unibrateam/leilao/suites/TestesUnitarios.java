package br.com.unibrateam.leilao.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.unibrateam.leilao.formatter.FormatadorDeMoedaTest;
import br.com.unibrateam.leilao.model.LeilaoTest;
import br.com.unibrateam.leilao.ui.AtualizadorDeLeiloesTest;
import br.com.unibrateam.leilao.ui.AtualizadorDeUsuarioTest;
import br.com.unibrateam.leilao.ui.recyclerview.adapter.ListaLeilaoAdapterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FormatadorDeMoedaTest.class,
        LeilaoTest.class,
        AtualizadorDeLeiloesTest.class,
        AtualizadorDeUsuarioTest.class,
        ListaLeilaoAdapterTest.class

})

//Concluído, suite rodando 100%

public class TestesUnitarios {


}
