package br.com.leroymarcel.store.domain.dto;

import br.com.leroymarcel.store.domain.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler {

    public ProdutoOutputDTO toModel(Produto produto) {
        ProdutoOutputDTO dto = ProdutoOutputDTO.entidadeParaDto(produto);

        dto.setUrlDetalhes("/ferramentas/" + produto.getId());
        dto.setUrlEditar("/ferramentas/" + produto.getId() + "/editar");
        dto.setUrlDeletar("/ferramentas/" + produto.getId() + "/deletar");

        return dto;
    }
}
