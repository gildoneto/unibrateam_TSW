package br.com.unibrateam.leilao.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestesUnitarios.class,
        TestesIntegracao.class
})
public class TestesAll {
}
