package br.com.unibrateam.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.unibrateam.leilao.database.dao.UsuarioDAO;
import br.com.unibrateam.leilao.model.Usuario;
import br.com.unibrateam.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//Concluído, rodando 100%

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private UsuarioDAO dao;
    @Mock
    private ListaUsuarioAdapter adapter;
    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtualizarListaDeUsuario_QuandoSalvarUsuario(){
        AtualizadorDeUsuario atualizador = new AtualizadorDeUsuario(
                dao,
                adapter,
                recyclerView);
        Usuario alex = new Usuario("Alex");
        when(dao.salva(alex)).thenReturn(new Usuario(1, "Alex"));
        when(adapter.getItemCount()).thenReturn(1);

        atualizador.salva(alex);

        verify(dao).salva(new Usuario("Alex"));
        verify(adapter).adiciona(new Usuario(1, "Alex"));
        verify(recyclerView).smoothScrollToPosition(0);
    }

}