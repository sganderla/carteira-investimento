package br.com.esaplicacoes.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 04/08/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "tipo_rendimento", schema = "public")
public class TipoRendimento extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

}
