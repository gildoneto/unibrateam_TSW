package br.com.unibrateam.leilao.ui;

import android.support.v7.widget.RecyclerView;

import br.com.unibrateam.leilao.database.dao.UsuarioDAO;
import br.com.unibrateam.leilao.model.Usuario;
import br.com.unibrateam.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

public class AtualizadorDeUsuario {

    private final UsuarioDAO dao;
    private final ListaUsuarioAdapter adapter;
    private final RecyclerView recyclerView;

    public AtualizadorDeUsuario(UsuarioDAO dao,
                                ListaUsuarioAdapter adapter,
                                RecyclerView recyclerView) {
        this.dao = dao;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    public void salva(Usuario usuario) {
        Usuario usuarioSalvo = dao.salva(usuario);
        atualizaNaLista(usuarioSalvo);
    }

    private void atualizaNaLista(Usuario usuario) {
        adapter.adiciona(usuario);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

}
